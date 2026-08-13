package org.oenexa.wallet.repository;

import org.oenexa.wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    List<WalletEntity> findByUserId(Long userId);
    Optional<WalletEntity> findByUserIdAndCurrency(Long userId, String currency);
}
