import React from 'react';
import PriceTicker from '../features/trading/components/PriceTicker';
import OrderBook from '../features/trading/components/OrderBook';
import ChartPlaceholder from '../features/trading/components/ChartPlaceholder';
import RecentTrades from '../features/trading/components/RecentTrades';
import OrderEntry from '../features/trading/components/OrderEntry';

const TradingDashboard: React.FC = () => {
  return (
    <div className="flex-1 flex flex-col p-1 bg-[#0b0e11] overflow-hidden">
      <PriceTicker />
      
      {/* 
        Responsive CSS Grid for Trading Layout
        Desktop: 4 columns
        - Col 1 (1fr): Chart + OrderEntry
        - Col 2 (300px): OrderBook
        - Col 3 (300px): RecentTrades
      */}
      <div className="flex-1 grid grid-cols-1 lg:grid-cols-[1fr_320px_320px] gap-1 overflow-hidden">
        
        {/* Left Column: Chart (top) & Order Entry (bottom) */}
        <div className="flex flex-col gap-1 overflow-hidden">
          <div className="flex-1 min-h-[400px]">
            <ChartPlaceholder />
          </div>
          <div className="h-[280px] shrink-0">
            <OrderEntry />
          </div>
        </div>

        {/* Middle Column: Order Book */}
        <div className="hidden lg:block overflow-hidden">
          <OrderBook />
        </div>

        {/* Right Column: Recent Trades */}
        <div className="hidden lg:block overflow-hidden">
          <RecentTrades />
        </div>
      </div>
    </div>
  );
};

export default TradingDashboard;
