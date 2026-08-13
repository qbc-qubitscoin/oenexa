import React from 'react';

const trades = Array.from({ length: 30 }).map((_, i) => {
  const isBuy = Math.random() > 0.5;
  return {
    price: (68430 + (Math.random() * 10 - 5)).toFixed(2),
    amount: (Math.random() * 2).toFixed(4),
    time: new Date(Date.now() - i * 5000).toLocaleTimeString([], { hour12: false }),
    isBuy
  };
});

const RecentTrades: React.FC = () => {
  return (
    <div className="trading-panel h-full flex flex-col text-xs font-mono">
      <div className="trading-panel-header">
        Market Trades
      </div>
      <div className="flex-1 overflow-hidden flex flex-col p-2">
        <div className="flex justify-between text-text-secondary mb-2 px-1 text-[10px]">
          <span>Price(USDT)</span>
          <span>Amount(BTC)</span>
          <span>Time</span>
        </div>
        <div className="flex-1 overflow-y-auto">
          {trades.map((trade, i) => (
            <div key={i} className="flex justify-between hover:bg-bg-hover cursor-pointer py-[2px] px-1">
              <span className={trade.isBuy ? "text-trade-green" : "text-trade-red"}>
                {trade.price}
              </span>
              <span className="text-text-primary">{trade.amount}</span>
              <span className="text-text-secondary">{trade.time}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default RecentTrades;
