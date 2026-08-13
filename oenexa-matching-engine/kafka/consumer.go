package kafka

import (
	"context"
	"encoding/json"
	"log"

	"github.com/oenexa/matching-engine/orderbook"
	"github.com/segmentio/kafka-go"
)

type EventConsumer struct {
	reader   *kafka.Reader
	producer *TradeProducer
	ob       *orderbook.OrderBook
}

func NewEventConsumer(brokers []string, topic string, groupID string, ob *orderbook.OrderBook, producer *TradeProducer) *EventConsumer {
	r := kafka.NewReader(kafka.ReaderConfig{
		Brokers:  brokers,
		GroupID:  groupID,
		Topic:    topic,
		MinBytes: 10e3,
		MaxBytes: 10e6,
	})
	return &EventConsumer{reader: r, producer: producer, ob: ob}
}

func (c *EventConsumer) Start() {
	log.Println("Starting Matching Engine Kafka consumer...")
	for {
		m, err := c.reader.ReadMessage(context.Background())
		if err != nil {
			log.Printf("Error reading message: %v\n", err)
			continue
		}

		var event map[string]interface{}
		if err := json.Unmarshal(m.Value, &event); err != nil {
			log.Printf("Failed to unmarshal event: %v\n", err)
			continue
		}

		log.Printf("Matching Engine received order: %v\n", event)

		sideStr := event["side"].(string)
		var side orderbook.Side
		if sideStr == "BUY" {
			side = orderbook.Buy
		} else {
			side = orderbook.Sell
		}

		order := &orderbook.Order{
			ID:        event["orderId"].(string),
			UserID:    int64(event["userId"].(float64)), // json unmarshals numbers to float64
			Asset:     event["asset"].(string),
			Size:      event["size"].(float64),
			Price:     event["price"].(float64),
			Side:      side,
			Timestamp: int64(event["timestamp"].(float64)),
		}

		trades := c.ob.AddOrder(order)
		for _, trade := range trades {
			if err := c.producer.PublishTrade(trade); err != nil {
				log.Printf("Failed to publish trade: %v\n", err)
			}
		}
	}
}

func (c *EventConsumer) Close() {
	if err := c.reader.Close(); err != nil {
		log.Fatal("failed to close reader:", err)
	}
}
