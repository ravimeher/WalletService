package com.ecommerce.walletservice.repository;

import com.ecommerce.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet getByUserId(Long userId);
}
