import React from 'react';
import { Outlet, Link } from 'react-router-dom';

const TradingLayout: React.FC = () => {
  return (
    <div className="min-h-screen flex flex-col bg-bg-base text-text-primary">
      {/* Top Navigation Bar */}
      <header className="h-16 flex items-center px-4 bg-bg-panel border-b border-border justify-between shrink-0">
        <div className="flex items-center space-x-6">
          <Link to="/" className="flex items-center space-x-2">
            <div className="w-8 h-8 bg-brand-yellow rounded-sm flex items-center justify-center font-bold text-bg-panel">
              O
            </div>
            <span className="text-xl font-bold tracking-wider text-brand-yellow">OENEXA</span>
          </Link>
          
          <nav className="hidden md:flex space-x-4">
            <Link to="/trade" className="text-sm font-medium hover:text-brand-yellow transition-colors">Trade</Link>
            <Link to="/markets" className="text-sm font-medium text-text-secondary hover:text-brand-yellow transition-colors">Markets</Link>
            <Link to="/wallet" className="text-sm font-medium text-text-secondary hover:text-brand-yellow transition-colors">Wallet</Link>
          </nav>
        </div>

        <div className="flex items-center space-x-4">
          <Link to="/login" className="text-sm font-medium text-text-primary hover:text-brand-yellow transition-colors">
            Log In
          </Link>
          <Link to="/register" className="text-sm font-medium bg-brand-yellow text-bg-panel px-4 py-1.5 rounded hover:bg-yellow-400 transition-colors">
            Register
          </Link>
        </div>
      </header>

      {/* Main Content Area */}
      <main className="flex-1 overflow-hidden flex flex-col">
        <Outlet />
      </main>
    </div>
  );
};

export default TradingLayout;
