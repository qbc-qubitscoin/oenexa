package orderbook

import (
	"fmt"
	"sort"
	"time"
)

type Side int

const (
	Buy Side = iota
	Sell
)

type Order struct {
	ID        string
	UserID    int64
	Asset     string
	Size      float64
	Price     float64
	Side      Side
	Timestamp int64
}

type Trade struct {
	MakerOrderID string
	TakerOrderID string
	MakerUserID  int64
	TakerUserID  int64
	Price        float64
	Size         float64
	Timestamp    int64
}

type OrderBook struct {
	Pair string
	Bids []*Order // Sorted descending by price, then ascending by time
	Asks []*Order // Sorted ascending by price, then ascending by time
}

func NewOrderBook(pair string) *OrderBook {
	return &OrderBook{
		Pair: pair,
		Bids: make([]*Order, 0),
		Asks: make([]*Order, 0),
	}
}

func (ob *OrderBook) AddOrder(order *Order) []Trade {
	var trades []Trade

	if order.Side == Buy {
		trades = ob.match(order, &ob.Asks, true)
		if order.Size > 0 {
			ob.Bids = append(ob.Bids, order)
			sort.Slice(ob.Bids, func(i, j int) bool {
				if ob.Bids[i].Price == ob.Bids[j].Price {
					return ob.Bids[i].Timestamp < ob.Bids[j].Timestamp
				}
				return ob.Bids[i].Price > ob.Bids[j].Price // Descending
			})
		}
	} else {
		trades = ob.match(order, &ob.Bids, false)
		if order.Size > 0 {
			ob.Asks = append(ob.Asks, order)
			sort.Slice(ob.Asks, func(i, j int) bool {
				if ob.Asks[i].Price == ob.Asks[j].Price {
					return ob.Asks[i].Timestamp < ob.Asks[j].Timestamp
				}
				return ob.Asks[i].Price < ob.Asks[j].Price // Ascending
			})
		}
	}

	for _, t := range trades {
		fmt.Printf("Trade Executed: Maker %s vs Taker %s at price %f for size %f\n", t.MakerOrderID, t.TakerOrderID, t.Price, t.Size)
	}

	return trades
}

func (ob *OrderBook) match(taker *Order, book *[]*Order, isBuy bool) []Trade {
	var trades []Trade
	b := *book

	for i := 0; i < len(b) && taker.Size > 0; i++ {
		maker := b[i]

		// Check if prices cross
		if isBuy && taker.Price < maker.Price {
			break
		}
		if !isBuy && taker.Price > maker.Price {
			break
		}

		// Calculate trade size
		tradeSize := taker.Size
		if maker.Size < taker.Size {
			tradeSize = maker.Size
		}

		// Execute trade
		trade := Trade{
			MakerOrderID: maker.ID,
			TakerOrderID: taker.ID,
			MakerUserID:  maker.UserID,
			TakerUserID:  taker.UserID,
			Price:        maker.Price, // Maker price always wins
			Size:         tradeSize,
			Timestamp:    time.Now().UnixMilli(),
		}
		trades = append(trades, trade)

		// Update sizes
		taker.Size -= tradeSize
		maker.Size -= tradeSize
	}

	// Remove fully filled orders from the book
	// In-place filtering
	n := 0
	for _, order := range b {
		if order.Size > 0 {
			b[n] = order
			n++
		}
	}
	*book = b[:n]

	return trades
}
