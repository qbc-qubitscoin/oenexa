package org.oenexa.marketdata.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/market")
public class MarketDataController {
    
    @GetMapping("/ticker")
    public Object getTicker() { return null; }
    
    @GetMapping("/orderbook")
    public Object getOrderbook() { return null; }
    
    @GetMapping("/trades")
    public Object getTrades() { return null; }
    
    @GetMapping("/klines")
    public Object getKlines() { return null; }
    
    @GetMapping("/24hr")
    public Object get24hrStats() { return null; }
}
