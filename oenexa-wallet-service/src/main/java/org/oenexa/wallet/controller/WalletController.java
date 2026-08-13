package org.oenexa.wallet.controller;

import org.oenexa.wallet.entity.WalletEntity;
import org.oenexa.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WalletEntity>> getWallets(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWallets(userId));
    }
}
