package com.ecommerce.walletservice.controller;

import com.ecommerce.walletservice.dto.CreateTransactionDTO;
import com.ecommerce.walletservice.dto.CreateTransactionResponseDTO;
import com.ecommerce.walletservice.dto.WalletLedgerResponseDTO;
import com.ecommerce.walletservice.entity.Transaction;
import com.ecommerce.walletservice.entity.WalletLedger;
import com.ecommerce.walletservice.exception.LedgerRetrievalException;
import com.ecommerce.walletservice.exception.TransactionCreationException;
import com.ecommerce.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;


    @GetMapping("/ledger/{id}")
    public ResponseEntity<List<WalletLedgerResponseDTO>> getCustomerLedger(@PathVariable Long id) {
        try{

            List<WalletLedger> ledgerResponse = walletService.getWalletLedger(id);
            List<WalletLedgerResponseDTO> ledgerResponseDTO = new ArrayList<>();
            for (WalletLedger walletLedger : ledgerResponse) {
                WalletLedgerResponseDTO walletLedgerResponseDTO = new WalletLedgerResponseDTO();
                ledgerResponseDTO.add(from(walletLedger));
            }
            return new ResponseEntity<>(ledgerResponseDTO, HttpStatus.CREATED);

        }
        catch(Exception e){
            throw new LedgerRetrievalException(e.getMessage());
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<CreateTransactionResponseDTO> createTransaction(@RequestBody CreateTransactionDTO request) {
        try{
            Transaction response = walletService.createTransaction(request.getOrder_id());
            CreateTransactionResponseDTO responseDTO = new CreateTransactionResponseDTO();
            responseDTO.setAmount(response.getAmount());
            responseDTO.setStatus(response.getStatus());
            responseDTO.setOrder_id(responseDTO.getOrder_id());
            responseDTO.setUpdated_at(response.getUpdatedAt());
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
        }catch (Exception e){
            throw new TransactionCreationException(e.getMessage());
        }

    }
    private WalletLedgerResponseDTO from (WalletLedger ledger){
        WalletLedgerResponseDTO responseDTO = new WalletLedgerResponseDTO();
        responseDTO.setType(ledger.getType());
        responseDTO.setPreviousBalance(ledger.getPreviousBalance());
        responseDTO.setCurrentBalance(ledger.getCurrentBalance());
        responseDTO.setUpdatedAt(ledger.getUpdatedAt());
        return responseDTO;
    }
}
