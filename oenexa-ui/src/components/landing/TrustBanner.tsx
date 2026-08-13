import React from 'react';

const TrustBanner: React.FC = () => {
  return (
    <div className="w-full mt-24">
      {/* Logos Row */}
      <div className="flex items-center justify-between opacity-50 mb-24 max-w-5xl mx-auto px-4 border-t border-border/30 pt-8">
        <div className="flex items-center space-x-4">
          <div className="w-8 h-8 border border-white/20 rounded-sm"></div>
          <span className="text-sm text-text-secondary w-48">Recognized as Forbes' Most Trusted Crypto Exchanges 2025</span>
        </div>
        <div className="flex items-center space-x-4">
          <div className="w-8 h-8 border border-white/20 rounded-sm"></div>
          <span className="text-sm text-text-secondary w-48">Named CNBC's World's Top Fintech Companies 2025 in Digital Assets</span>
        </div>
        <div className="flex items-center space-x-4">
          <div className="font-bold text-xl tracking-tighter">FORTUNE<br/><span className="text-sm">CRYPTO 100</span></div>
          <span className="text-sm text-text-secondary w-48">Named a CeFi Leader in Fortune Crypto 100's most influential companies in blockchain</span>
        </div>
      </div>

      {/* FUNDS ARE SAFU Section */}
      <div className="mt-24">
        <div className="max-w-7xl mx-auto flex items-end justify-between px-4">
          <h2 className="text-[5rem] leading-none font-bold text-text-primary tracking-tight">
            FUNDS ARE<br/>SAFU
          </h2>
          
          <div className="flex flex-col items-end pb-4">
            <p className="text-text-secondary text-sm mb-2 max-w-sm text-right">
              As of February 2026, the SAFU fund wallet comprises a reserve of
            </p>
            <span className="text-3xl font-bold text-brand-yellow">15,000 BTC</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TrustBanner;
