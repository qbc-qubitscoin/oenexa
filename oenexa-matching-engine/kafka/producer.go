package kafka

import (
	"context"
	"encoding/json"
	"fmt"
	"log"

	"github.com/oenexa/matching-engine/orderbook"
	"github.com/segmentio/kafka-go"
)

type TradeProducer struct {
	writer *kafka.Writer
}

func NewTradeProducer(brokers []string, topic string) *TradeProducer {
	w := &kafka.Writer{
		Addr:     kafka.TCP(brokers...),
		Topic:    topic,
		Balancer: &kafka.LeastBytes{},
	}
	return &TradeProducer{writer: w}
}

func (p *TradeProducer) PublishTrade(trade orderbook.Trade) error {
	tradeMap := map[string]interface{}{
		"makerOrderId": trade.MakerOrderID,
		"takerOrderId": trade.TakerOrderID,
		"makerUserId":  trade.MakerUserID,
		"takerUserId":  trade.TakerUserID,
		"price":        trade.Price,
		"size":         trade.Size,
		"timestamp":    trade.Timestamp,
	}

	payloadBytes, err := json.Marshal(tradeMap)
	if err != nil {
		return fmt.Errorf("failed to marshal trade: %w", err)
	}

	msg := kafka.Message{
		Key:   []byte(trade.MakerOrderID), // Or taker
		Value: payloadBytes,
	}

	err = p.writer.WriteMessages(context.Background(), msg)
	if err != nil {
		log.Printf("Failed to produce trade: %v\n", err)
		return err
	}

	log.Printf("Published trade execution to Kafka: %s\n", string(payloadBytes))
	return nil
}

func (p *TradeProducer) Close() {
	if err := p.writer.Close(); err != nil {
		log.Fatal("failed to close writer:", err)
	}
}
