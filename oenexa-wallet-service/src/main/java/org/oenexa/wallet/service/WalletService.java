package org.oenexa.wallet.service;

import org.oenexa.wallet.entity.WalletEntity;
import org.oenexa.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<WalletEntity> getWallets(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    @Transactional
    public void addBalance(Long userId, String currency, BigDecimal amount) {
        WalletEntity wallet = walletRepository.findByUserIdAndCurrency(userId, currency)
            .orElseGet(() -> {
                WalletEntity w = new WalletEntity();
                w.setUserId(userId);
                w.setCurrency(currency);
                w.setWalletType(WalletEntity.WalletType.SPOT);
                w.setStatus(WalletEntity.WalletStatus.ACTIVE);
                w.setIsDefault(true);
                return w;
            });
        
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }
}
