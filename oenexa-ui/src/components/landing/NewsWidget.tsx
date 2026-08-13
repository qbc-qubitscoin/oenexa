import React from 'react';
import { Link } from 'react-router-dom';

const newsItems = [
  "Iranian Foreign Ministry Again Condemns U.S. and Israel for Violating International Humanitarian Law",
  "STOCKS | Second Supertanker Appears at Saudi Oil Export Terminal as Loading Rebounds",
  "Hyperliquid Burns 19,750 HYPE Tokens in 24 Hours, Generates $1.6 Million in Fees",
  "Bank of America Raises ERock Price Target to $19 After Anthropic Order",
];

const NewsWidget: React.FC = () => {
  return (
    <div className="bg-bg-panel border border-border rounded-2xl p-6">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-sm font-semibold text-text-primary">News</h2>
        <Link to="/news" className="text-sm text-text-secondary hover:text-brand-yellow flex items-center">
          View All News <span className="ml-1">›</span>
        </Link>
      </div>

      <div className="flex flex-col space-y-4">
        {newsItems.map((news, idx) => (
          <div key={idx} className="group cursor-pointer">
            <p className="text-sm text-text-secondary group-hover:text-brand-yellow transition-colors leading-relaxed line-clamp-2">
              {news}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default NewsWidget;
