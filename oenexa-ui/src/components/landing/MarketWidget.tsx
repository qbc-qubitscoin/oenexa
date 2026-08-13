import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import { useMarketData } from '../../hooks/useMarketData';

const initialCoins = [
  { symbol: 'BTC', name: 'Bitcoin', price: 63163.11, change: '-0.52%' },
  { symbol: 'ETH', name: 'Ethereum', price: 1877.86, change: '-0.61%' },
  { symbol: 'BNB', name: 'BNB', price: 608.29, change: '-0.47%' },
  { symbol: 'XRP', name: 'XRP', price: 1.01, change: '-0.72%' },
  { symbol: 'ASTER', name: 'Aster', price: 0.601, change: '-0.17%' },
];

const MarketWidget: React.FC = () => {
  const [activeTab, setActiveTab] = useState('Popular');
  const { data: btcData } = useMarketData('BTC');

  // Merge live data with initial state
  const coins = initialCoins.map(c => {
    if (c.symbol === 'BTC' && btcData) {
      return { ...c, price: btcData.price, change: btcData.change };
    }
    return c;
  });

  const formatPrice = (price: number) => {
    return '$' + price.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  };

  return (
    <div className="bg-bg-panel border border-border rounded-2xl p-6 mb-6">
      <div className="flex items-center justify-between mb-6 border-b border-border/50 pb-2">
        <div className="flex space-x-6">
          {['Popular', 'Stocks', 'Commodities'].map(tab => (
            <button
              key={tab}
              onClick={() => setActiveTab(tab)}
              className={`text-sm font-semibold pb-2 border-b-2 transition-colors ${
                activeTab === tab 
                  ? 'text-text-primary border-brand-yellow' 
                  : 'text-text-secondary border-transparent hover:text-text-primary'
              }`}
            >
              {tab}
            </button>
          ))}
        </div>
        <Link to="/markets" className="text-sm text-text-secondary hover:text-brand-yellow flex items-center">
          View All <span className="ml-1">›</span>
        </Link>
      </div>

      <div className="flex flex-col space-y-5">
        {coins.map(coin => (
          <div key={coin.symbol} className="flex items-center justify-between hover:bg-bg-hover rounded-lg p-2 transition-colors cursor-pointer -mx-2">
            <div className="flex items-center space-x-3 w-1/3">
              <div className="w-6 h-6 rounded-full bg-brand-yellow flex items-center justify-center font-bold text-[10px] text-bg-panel">
                {coin.symbol[0]}
              </div>
              <div className="flex items-center space-x-2">
                <span className="font-bold text-text-primary">{coin.symbol}</span>
                <span className="text-xs text-text-secondary">{coin.name}</span>
              </div>
            </div>
            
            <div className="w-1/3 text-right font-medium text-text-primary">
              {formatPrice(coin.price)}
            </div>

            <div className={`w-1/3 text-right font-medium ${coin.change.startsWith('-') ? 'text-trade-red' : 'text-trade-green'}`}>
              {coin.change}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MarketWidget;
