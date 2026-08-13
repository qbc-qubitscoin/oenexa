package orderbook

import (
	"testing"
)

func TestOrderBook_Matching(t *testing.T) {
	ob := NewOrderBook("BTC-USD")

	// Maker sell order: 1 BTC at $50,000
	makerSell := &Order{
		ID:        "maker-sell-1",
		UserID:    1,
		Asset:     "BTC-USD",
		Size:      1.0,
		Price:     50000.0,
		Side:      Sell,
		Timestamp: 1000,
	}

	trades1 := ob.AddOrder(makerSell)
	if len(trades1) != 0 {
		t.Fatalf("Expected 0 trades, got %d", len(trades1))
	}
	if len(ob.Asks) != 1 {
		t.Fatalf("Expected 1 order in Asks, got %d", len(ob.Asks))
	}

	// Taker buy order: 0.5 BTC at $51,000 (Crosses the spread)
	takerBuy := &Order{
		ID:        "taker-buy-1",
		UserID:    2,
		Asset:     "BTC-USD",
		Size:      0.5,
		Price:     51000.0,
		Side:      Buy,
		Timestamp: 2000,
	}

	trades2 := ob.AddOrder(takerBuy)
	if len(trades2) != 1 {
		t.Fatalf("Expected 1 trade, got %d", len(trades2))
	}

	trade := trades2[0]
	if trade.Price != 50000.0 {
		t.Errorf("Expected trade price 50000.0, got %f", trade.Price)
	}
	if trade.Size != 0.5 {
		t.Errorf("Expected trade size 0.5, got %f", trade.Size)
	}

	if len(ob.Asks) != 1 {
		t.Fatalf("Expected 1 order in Asks, got %d", len(ob.Asks))
	}
	if ob.Asks[0].Size != 0.5 {
		t.Errorf("Expected remaining ask size 0.5, got %f", ob.Asks[0].Size)
	}
	if len(ob.Bids) != 0 {
		t.Fatalf("Expected 0 orders in Bids, got %d", len(ob.Bids))
	}
}
