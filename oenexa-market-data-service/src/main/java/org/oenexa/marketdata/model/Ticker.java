package org.oenexa.marketdata.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Ticker {
    private String symbol;
    private BigDecimal price;
}
