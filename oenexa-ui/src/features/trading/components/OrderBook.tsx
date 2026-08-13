import React from 'react';

// Mock data
const asks = Array.from({ length: 15 }).map((_, i) => ({
  price: 68433.00 + (i * 0.5),
  amount: (Math.random() * 2).toFixed(4),
  total: (Math.random() * 10).toFixed(4)
})).reverse();

const bids = Array.from({ length: 15 }).map((_, i) => ({
  price: 68432.50 - (i * 0.5),
  amount: (Math.random() * 2).toFixed(4),
  total: (Math.random() * 10).toFixed(4)
}));

const OrderBook: React.FC = () => {
  return (
    <div className="trading-panel h-full flex flex-col text-xs font-mono">
      <div className="trading-panel-header flex justify-between items-center">
        <span>Order Book</span>
        <div className="flex space-x-1">
          <button className="p-1 rounded bg-bg-hover hover:bg-border transition-colors text-text-primary" title="Both">〓</button>
          <button className="p-1 rounded hover:bg-border transition-colors text-trade-green" title="Bids">▼</button>
          <button className="p-1 rounded hover:bg-border transition-colors text-trade-red" title="Asks">▲</button>
        </div>
      </div>
      
      <div className="flex-1 overflow-hidden flex flex-col p-2">
        {/* Table Header */}
        <div className="flex justify-between text-text-secondary mb-2 px-1 text-[10px]">
          <span>Price(USDT)</span>
          <span>Amount(BTC)</span>
          <span>Total</span>
        </div>

        {/* Asks (Sell Orders - Red) */}
        <div className="flex-1 overflow-hidden flex flex-col justify-end">
          {asks.map((ask, i) => (
            <div key={`ask-${i}`} className="flex justify-between relative hover:bg-bg-hover cursor-pointer py-[2px] px-1 group">
              <div className="absolute right-0 top-0 bottom-0 bg-trade-red opacity-10" style={{ width: `${Math.min(100, Number(ask.total) * 10)}%`}}></div>
              <span className="text-trade-red z-10">{ask.price.toFixed(2)}</span>
              <span className="text-text-primary z-10">{ask.amount}</span>
              <span className="text-text-primary z-10">{ask.total}</span>
            </div>
          ))}
        </div>

        {/* Current Price Divider */}
        <div className="py-2 flex items-center justify-between border-y border-border my-1 px-1">
          <div className="flex items-center space-x-2">
            <span className="text-trade-green text-lg font-bold">68,432.50</span>
            <span className="text-text-secondary text-sm">~ $68,432.50</span>
          </div>
        </div>

        {/* Bids (Buy Orders - Green) */}
        <div className="flex-1 overflow-hidden flex flex-col">
          {bids.map((bid, i) => (
            <div key={`bid-${i}`} className="flex justify-between relative hover:bg-bg-hover cursor-pointer py-[2px] px-1 group">
              <div className="absolute right-0 top-0 bottom-0 bg-trade-green opacity-10" style={{ width: `${Math.min(100, Number(bid.total) * 10)}%`}}></div>
              <span className="text-trade-green z-10">{bid.price.toFixed(2)}</span>
              <span className="text-text-primary z-10">{bid.amount}</span>
              <span className="text-text-primary z-10">{bid.total}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default OrderBook;
