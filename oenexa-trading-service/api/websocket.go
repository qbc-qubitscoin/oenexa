package api

import (
	"encoding/json"
	"log"
	"net/http"
	"sync"

	"github.com/gin-gonic/gin"
	"github.com/gorilla/websocket"
)

var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true // Allow all origins for development
	},
}

// MarketData represents the payload sent to the UI
type MarketData struct {
	Type  string      `json:"type"` // "ticker", "orderbook", "trade"
	Asset string      `json:"asset"`
	Data  interface{} `json:"data"`
}

type WSHub struct {
	clients    map[*websocket.Conn]bool
	broadcast  chan MarketData
	register   chan *websocket.Conn
	unregister chan *websocket.Conn
	mu         sync.Mutex
}

func NewWSHub() *WSHub {
	return &WSHub{
		clients:    make(map[*websocket.Conn]bool),
		broadcast:  make(chan MarketData),
		register:   make(chan *websocket.Conn),
		unregister: make(chan *websocket.Conn),
	}
}

func (h *WSHub) Run() {
	for {
		select {
		case client := <-h.register:
			h.mu.Lock()
			h.clients[client] = true
			h.mu.Unlock()
			log.Println("New WebSocket client connected")
		case client := <-h.unregister:
			h.mu.Lock()
			if _, ok := h.clients[client]; ok {
				delete(h.clients, client)
				client.Close()
				log.Println("WebSocket client disconnected")
			}
			h.mu.Unlock()
		case message := <-h.broadcast:
			h.mu.Lock()
			msgBytes, err := json.Marshal(message)
			if err != nil {
				h.mu.Unlock()
				continue
			}
			for client := range h.clients {
				err := client.WriteMessage(websocket.TextMessage, msgBytes)
				if err != nil {
					client.Close()
					delete(h.clients, client)
				}
			}
			h.mu.Unlock()
		}
	}
}

func (h *WSHub) BroadcastData(msgType, asset string, data interface{}) {
	h.broadcast <- MarketData{
		Type:  msgType,
		Asset: asset,
		Data:  data,
	}
}

func (h *WSHub) HandleWS(c *gin.Context) {
	conn, err := upgrader.Upgrade(c.Writer, c.Request, nil)
	if err != nil {
		log.Println("WS Upgrade Error:", err)
		return
	}
	h.register <- conn

	// Keep the connection open and read messages (if client sends any)
	go func() {
		defer func() {
			h.unregister <- conn
		}()
		for {
			_, _, err := conn.ReadMessage()
			if err != nil {
				break
			}
		}
	}()
}
