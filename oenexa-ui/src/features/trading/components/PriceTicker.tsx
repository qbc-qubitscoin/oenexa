import React from 'react';
import { useMarketData } from '../../../hooks/useMarketData';

const PriceTicker: React.FC = () => {
  const { data: btcData, isConnected } = useMarketData('BTC');
  
  const price = btcData ? btcData.price : 68432.50;
  const change = btcData ? btcData.change : "+1.79%";
  const isUp = change.startsWith('+');

  const formatPrice = (p: number) => p.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 });

  return (
    <div className="trading-panel flex-row items-center justify-between px-4 py-2 mb-1 shrink-0 h-16 overflow-x-auto">
      <div className="flex items-center space-x-6 min-w-max">
        {/* Pair & Current Price */}
        <div className="flex items-center space-x-4">
          <h1 className="text-xl font-bold text-text-primary">
            BTC/USDT 
            <span className={`ml-2 w-2 h-2 rounded-full inline-block ${isConnected ? 'bg-trade-green' : 'bg-trade-red'}`}></span>
          </h1>
          <div className="flex flex-col">
            <span className={`font-semibold text-lg ${isUp ? 'text-trade-green' : 'text-trade-red'}`}>
              {formatPrice(price)}
            </span>
            <span className="text-text-secondary text-xs">${formatPrice(price)}</span>
          </div>
        </div>

        {/* 24h Change */}
        <div className="flex flex-col">
          <span className="text-text-secondary text-xs">24h Change</span>
          <span className={`text-sm ${isUp ? 'text-trade-green' : 'text-trade-red'}`}>{change}</span>
        </div>

        {/* 24h High */}
        <div className="flex flex-col">
          <span className="text-text-secondary text-xs">24h High</span>
          <span className="text-text-primary text-sm">69,120.00</span>
        </div>

        {/* 24h Low */}
        <div className="flex flex-col">
          <span className="text-text-secondary text-xs">24h Low</span>
          <span className="text-text-primary text-sm">67,105.20</span>
        </div>

        {/* 24h Volume */}
        <div className="flex flex-col">
          <span className="text-text-secondary text-xs">24h Vol(BTC)</span>
          <span className="text-text-primary text-sm">45,123.45</span>
        </div>
      </div>
    </div>
  );
};

export default PriceTicker;
