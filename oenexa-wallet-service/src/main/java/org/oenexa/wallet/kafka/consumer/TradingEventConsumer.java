package org.oenexa.wallet.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TradingEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TradingEventConsumer.class);

    @KafkaListener(topics = "trading.order.matched", groupId = "oenexa-wallet-service-group")
    public void handleOrderMatched(String payload) {
        log.info("Received order matched event to settle balances: {}", payload);
        // Implement balance settlement based on matched trades from the Trading Engine
    }
}
