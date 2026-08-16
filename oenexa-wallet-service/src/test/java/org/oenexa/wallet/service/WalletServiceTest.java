package org.oenexa.wallet.service;

import org.junit.jupiter.api.Test;
import org.oenexa.wallet.entity.WalletEntity;
import org.oenexa.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(WalletService.class)
class WalletServiceTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @Test
    void testGetWallets() {
        // Given
        WalletEntity wallet = new WalletEntity();
        wallet.setUserId(1L);
        wallet.setCurrency("USD");
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setWalletType(WalletEntity.WalletType.SPOT);
        wallet.setStatus(WalletEntity.WalletStatus.ACTIVE);
        wallet.setIsDefault(true);
        walletRepository.save(wallet);

        // When
        List<WalletEntity> wallets = walletService.getWallets(1L);

        // Then
        assertFalse(wallets.isEmpty());
        assertEquals(1, wallets.size());
        assertEquals("USD", wallets.get(0).getCurrency());
    }

    @Test
    void testAddBalance_ExistingWallet() {
        // Given
        WalletEntity wallet = new WalletEntity();
        wallet.setUserId(1L);
        wallet.setCurrency("USD");
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setWalletType(WalletEntity.WalletType.SPOT);
        wallet.setStatus(WalletEntity.WalletStatus.ACTIVE);
        wallet.setIsDefault(true);
        walletRepository.save(wallet);

        // When
        walletService.addBalance(1L, "USD", BigDecimal.valueOf(50));

        // Then
        WalletEntity updatedWallet = walletRepository.findByUserIdAndCurrency(1L, "USD").orElseThrow();
        assertEquals(0, BigDecimal.valueOf(150).compareTo(updatedWallet.getBalance()));
    }

    @Test
    void testAddBalance_NewWallet() {
        // Given
        // No existing wallet

        // When
        walletService.addBalance(1L, "EUR", BigDecimal.valueOf(50));

        // Then
        WalletEntity newWallet = walletRepository.findByUserIdAndCurrency(1L, "EUR").orElseThrow();
        assertEquals(0, BigDecimal.valueOf(50).compareTo(newWallet.getBalance()));
        assertEquals(WalletEntity.WalletType.SPOT, newWallet.getWalletType());
    }
}
