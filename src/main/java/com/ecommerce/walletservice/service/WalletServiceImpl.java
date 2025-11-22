package com.ecommerce.walletservice.service;

import com.ecommerce.walletservice.entity.*;
import com.ecommerce.walletservice.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletLedgerRepository walletLedgerRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<WalletLedger> getWalletLedger(Long uuid) {
        return walletLedgerRepository.findAllByUserId(uuid) ;
    }

    @Override
    @Transactional
    public Transaction createTransaction(Long orderid) {
        Transaction tx = new Transaction();
        //From orderid get user and wallet details
        Order order = orderRepository.getReferenceById(orderid);
        Long userId = order.getUserId();
        User user = userRepository.getReferenceById(userId);
        Wallet userWallet = walletRepository.getByUserId(userId);
        // validate if wallet has amount to create transaction
        //if wallet updated then upodate wallet repository
        //and then createTransaction() and return it using dto
        WalletLedger walletLedger = new WalletLedger();
        if(userWallet.getAmount().compareTo(order.getAmount()) >= 0){
            walletLedger.setWalletId(userWallet.getId());
            walletLedger.setTransactionId(tx.getId());
            walletLedger.setPreviousBalance(userWallet.getAmount());
            walletLedger.setUpdatedAt(LocalDateTime.now());

            userWallet.setAmount(userWallet.getAmount().subtract(order.getAmount()));
            userWallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.save(userWallet);

            walletLedger.setCurrentBalance(userWallet.getAmount());
            walletLedger.setType("debit");
            walletLedgerRepository.save(walletLedger);

            tx.setAmount(order.getAmount());
            tx.setUserId(userWallet.getUserId());
            tx.setCreatedAt(LocalDateTime.now());
            transactionRepository.save(tx);
        }
        return tx;
    }
}
