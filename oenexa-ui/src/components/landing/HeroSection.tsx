import React from 'react';
import { Link } from 'react-router-dom';

const HeroSection: React.FC = () => {
  return (
    <div className="flex flex-col space-y-8 max-w-xl">
      <div className="space-y-2">
        <h1 className="text-[4rem] leading-tight font-extrabold text-brand-yellow font-sans">
          327,141,065
        </h1>
        <h1 className="text-[4rem] leading-tight font-extrabold text-text-primary uppercase">
          Users<br/>Trust Us
        </h1>
        <p className="text-xl text-text-primary mt-4">
          The World's Leading Cryptocurrency Exchange
        </p>
      </div>

      <div className="flex space-x-12 mt-8">
        <div className="flex flex-col">
          <div className="flex items-center text-brand-yellow">
            <span className="text-2xl mr-2">🌾</span> {/* Decoration */}
            <span className="font-bold text-xl">No.1</span>
            <span className="text-2xl ml-2">🌾</span>
          </div>
          <span className="text-text-secondary text-sm mt-1">Customer Assets</span>
        </div>
        <div className="flex flex-col">
          <div className="flex items-center text-brand-yellow">
            <span className="text-2xl mr-2">🌾</span>
            <span className="font-bold text-xl">No.1</span>
            <span className="text-2xl ml-2">🌾</span>
          </div>
          <span className="text-text-secondary text-sm mt-1">Trading Volume</span>
        </div>
      </div>

      <div className="flex items-center space-x-4 mt-8 bg-bg-panel border border-border p-2 rounded-xl">
        <div className="flex-1 flex items-center px-4 bg-[#1e2026] rounded-lg text-sm text-text-secondary h-12">
          <span className="mr-3 text-lg">🛡️</span>
          Industry-Leading Security
        </div>
        <Link to="/register" className="h-12 flex items-center px-8 bg-brand-yellow text-bg-panel font-bold rounded-lg hover:bg-yellow-400 transition-colors">
          Sign Up
        </Link>
      </div>

      <div className="flex items-center space-x-4 mt-6">
        <button className="w-12 h-12 bg-bg-panel border border-border rounded-xl flex items-center justify-center hover:bg-bg-hover transition-colors">
          <span className="text-xl font-bold text-blue-400">G</span>
        </button>
        <button className="w-12 h-12 bg-bg-panel border border-border rounded-xl flex items-center justify-center hover:bg-bg-hover transition-colors">
          <span className="text-xl font-bold text-white"></span>
        </button>
        <button className="w-12 h-12 bg-bg-panel border border-border rounded-xl flex items-center justify-center hover:bg-bg-hover transition-colors">
          <span className="text-xl font-bold text-text-primary">QR</span>
        </button>
      </div>
    </div>
  );
};

export default HeroSection;
