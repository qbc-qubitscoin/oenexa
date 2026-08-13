import React from 'react';

const ChartPlaceholder: React.FC = () => {
  return (
    <div className="trading-panel h-full flex flex-col">
      <div className="trading-panel-header flex items-center space-x-4">
        <span className="text-brand-yellow border-b-2 border-brand-yellow pb-1">Chart</span>
        <span className="text-text-secondary pb-1 cursor-pointer hover:text-text-primary">Info</span>
      </div>
      <div className="flex-1 bg-bg-base m-2 rounded flex flex-col items-center justify-center border border-border/50 text-text-secondary">
        {/* Placeholder for TradingView Lightweight Charts or similar */}
        <div className="w-16 h-16 mb-4 opacity-20">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <path strokeLinecap="round" strokeLinejoin="round" d="M7 12l3-3 3 3 4-4M8 21l4-4 4 4M3 4h18M4 4h16v12a1 1 0 01-1 1H5a1 1 0 01-1-1V4z" />
          </svg>
        </div>
        <p className="text-sm">Interactive Chart Component</p>
        <p className="text-xs text-text-muted mt-2">TradingView integration will be placed here.</p>
      </div>
    </div>
  );
};

export default ChartPlaceholder;
