package org.oenexa.kyc.repository;

import org.oenexa.kyc.entity.KycProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KycProfileRepository extends JpaRepository<KycProfileEntity, Long> {
    Optional<KycProfileEntity> findByUserId(UUID userId);
}
