package com.ecommerce.walletservice.service;

import com.ecommerce.walletservice.entity.Transaction;
import com.ecommerce.walletservice.entity.WalletLedger;

import java.util.List;


public interface WalletService {
    public List<WalletLedger> getWalletLedger(Long uuid);
    public Transaction createTransaction(Long orderid);
}
