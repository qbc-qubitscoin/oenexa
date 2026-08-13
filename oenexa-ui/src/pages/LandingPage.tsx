import React from 'react';
import { Link } from 'react-router-dom';
import HeroSection from '../components/landing/HeroSection';
import MarketWidget from '../components/landing/MarketWidget';
import NewsWidget from '../components/landing/NewsWidget';
import TrustBanner from '../components/landing/TrustBanner';

const LandingPage: React.FC = () => {
  return (
    <div className="min-h-screen bg-bg-base text-text-primary flex flex-col font-sans overflow-x-hidden">
      
      {/* Navbar (Landing Page specific) */}
      <header className="h-16 flex items-center px-6 justify-between shrink-0">
        <div className="flex items-center space-x-8">
          <Link to="/" className="flex items-center space-x-2">
            <div className="w-8 h-8 bg-brand-yellow rounded-sm flex items-center justify-center font-bold text-bg-panel text-xl pb-1">
              ⬡
            </div>
            <span className="text-xl font-bold tracking-wider text-brand-yellow">OENEXA</span>
          </Link>
          
          <nav className="hidden lg:flex space-x-6">
            <Link to="/buy" className="text-sm font-semibold hover:text-brand-yellow transition-colors">Buy Crypto</Link>
            <Link to="/markets" className="text-sm font-semibold hover:text-brand-yellow transition-colors">Markets</Link>
            <Link to="/trade" className="text-sm font-semibold hover:text-brand-yellow transition-colors">Trade ⌄</Link>
            <Link to="/futures" className="text-sm font-semibold hover:text-brand-yellow transition-colors">Futures ⌄</Link>
            <Link to="/earn" className="text-sm font-semibold hover:text-brand-yellow transition-colors">Earn ⌄</Link>
            <Link to="/square" className="text-sm font-semibold hover:text-brand-yellow transition-colors">Square ⌄</Link>
            <Link to="/more" className="text-sm font-semibold hover:text-brand-yellow transition-colors">More ⌄</Link>
          </nav>
        </div>

        <div className="flex items-center space-x-4">
          <button className="text-xl text-text-primary hover:text-brand-yellow transition-colors">
            <span role="img" aria-label="search">🔍</span>
          </button>
          <Link to="/login" className="text-sm font-semibold bg-bg-hover px-4 py-2 rounded-lg hover:bg-border transition-colors">
            Log In
          </Link>
          <Link to="/register" className="text-sm font-semibold bg-brand-yellow text-bg-panel px-4 py-2 rounded-lg hover:bg-yellow-400 transition-colors">
            Sign Up
          </Link>
          <div className="flex items-center space-x-3 ml-2 text-text-secondary">
            <span className="cursor-pointer hover:text-text-primary">📥</span>
            <span className="cursor-pointer hover:text-text-primary">🌐</span>
            <span className="cursor-pointer hover:text-text-primary">🌙</span>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 flex flex-col pt-16">
        <div className="max-w-7xl w-full mx-auto px-4 flex flex-col lg:flex-row justify-between gap-12">
          
          {/* Left Side: Hero */}
          <div className="flex-1 lg:pr-12 pt-12">
            <HeroSection />
          </div>

          {/* Right Side: Widgets */}
          <div className="w-full lg:w-[400px] xl:w-[450px] shrink-0">
            <MarketWidget />
            <NewsWidget />
          </div>

        </div>

        {/* Bottom Banners */}
        <div className="mt-auto">
          <TrustBanner />
        </div>
      </main>

    </div>
  );
};

export default LandingPage;
