import React, { useState } from 'react';

const OrderEntry: React.FC = () => {
  const [orderType, setOrderType] = useState<'limit' | 'market'>('limit');

  return (
    <div className="trading-panel h-full flex flex-col text-sm">
      <div className="trading-panel-header flex space-x-6">
        <button 
          className={`font-semibold pb-1 ${orderType === 'limit' ? 'text-brand-yellow border-b-2 border-brand-yellow' : 'text-text-secondary hover:text-text-primary'}`}
          onClick={() => setOrderType('limit')}
        >
          Limit
        </button>
        <button 
          className={`font-semibold pb-1 ${orderType === 'market' ? 'text-brand-yellow border-b-2 border-brand-yellow' : 'text-text-secondary hover:text-text-primary'}`}
          onClick={() => setOrderType('market')}
        >
          Market
        </button>
      </div>

      <div className="flex-1 p-4 flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
        {/* Buy Side */}
        <div className="flex-1 flex flex-col space-y-4">
          <div className="flex justify-between text-xs text-text-secondary">
            <span>Avail:</span>
            <span className="text-text-primary">15,420.00 USDT</span>
          </div>

          {orderType === 'limit' && (
            <div className="bg-bg-base border border-border rounded flex items-center px-3 py-2">
              <span className="text-text-secondary w-16">Price</span>
              <input type="text" className="bg-transparent flex-1 text-right outline-none text-text-primary" defaultValue="68432.50" />
              <span className="text-text-secondary ml-2">USDT</span>
            </div>
          )}

          <div className="bg-bg-base border border-border rounded flex items-center px-3 py-2">
            <span className="text-text-secondary w-16">Amount</span>
            <input type="text" className="bg-transparent flex-1 text-right outline-none text-text-primary" placeholder="" />
            <span className="text-text-secondary ml-2">BTC</span>
          </div>

          {orderType === 'limit' && (
            <div className="bg-bg-base border border-border/50 rounded flex items-center px-3 py-2 opacity-50 cursor-not-allowed">
              <span className="text-text-secondary w-16">Total</span>
              <input type="text" className="bg-transparent flex-1 text-right outline-none text-text-primary" disabled />
              <span className="text-text-secondary ml-2">USDT</span>
            </div>
          )}

          <button className="w-full bg-trade-green hover:bg-trade-greenHover text-white font-bold py-3 rounded transition-colors mt-auto">
            Buy BTC
          </button>
        </div>

        {/* Sell Side */}
        <div className="flex-1 flex flex-col space-y-4">
          <div className="flex justify-between text-xs text-text-secondary">
            <span>Avail:</span>
            <span className="text-text-primary">0.5432 BTC</span>
          </div>

          {orderType === 'limit' && (
            <div className="bg-bg-base border border-border rounded flex items-center px-3 py-2">
              <span className="text-text-secondary w-16">Price</span>
              <input type="text" className="bg-transparent flex-1 text-right outline-none text-text-primary" defaultValue="68432.50" />
              <span className="text-text-secondary ml-2">USDT</span>
            </div>
          )}

          <div className="bg-bg-base border border-border rounded flex items-center px-3 py-2">
            <span className="text-text-secondary w-16">Amount</span>
            <input type="text" className="bg-transparent flex-1 text-right outline-none text-text-primary" placeholder="" />
            <span className="text-text-secondary ml-2">BTC</span>
          </div>

          {orderType === 'limit' && (
            <div className="bg-bg-base border border-border/50 rounded flex items-center px-3 py-2 opacity-50 cursor-not-allowed">
              <span className="text-text-secondary w-16">Total</span>
              <input type="text" className="bg-transparent flex-1 text-right outline-none text-text-primary" disabled />
              <span className="text-text-secondary ml-2">USDT</span>
            </div>
          )}

          <button className="w-full bg-trade-red hover:bg-trade-redHover text-white font-bold py-3 rounded transition-colors mt-auto">
            Sell BTC
          </button>
        </div>
      </div>
    </div>
  );
};

export default OrderEntry;
