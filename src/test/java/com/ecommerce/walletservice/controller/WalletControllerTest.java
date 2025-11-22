package com.ecommerce.walletservice.controller;

import com.ecommerce.walletservice.dto.CreateTransactionDTO;
import com.ecommerce.walletservice.dto.CreateTransactionResponseDTO;
import com.ecommerce.walletservice.dto.WalletLedgerResponseDTO;
import com.ecommerce.walletservice.entity.Transaction;
import com.ecommerce.walletservice.entity.WalletLedger;
import com.ecommerce.walletservice.exception.LedgerRetrievalException;
import com.ecommerce.walletservice.exception.TransactionCreationException;
import com.ecommerce.walletservice.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletService walletService;

    @MockitoBean
    private ObjectMapper objectMapper;

    private WalletLedger walletLedger;

    @BeforeEach
    void setup() {
        walletLedger = new WalletLedger();
        walletLedger.setType("credit");
        walletLedger.setPreviousBalance(BigDecimal.valueOf(100));
        walletLedger.setCurrentBalance(BigDecimal.valueOf(200));
        walletLedger.setUpdatedAt(LocalDateTime.now());
    }

    // TEST 1: GET /wallet/ledger/{id} -> SUCCESS
    @Test
    void testGetCustomerLedger_Success() throws Exception {

        List<WalletLedger> list = Arrays.asList(walletLedger);

        when(walletService.getWalletLedger(1L)).thenReturn(list);

        mockMvc.perform(get("/wallet/ledger/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].type").value("credit"))
                .andExpect(jsonPath("$[0].previousBalance").value(100))
                .andExpect(jsonPath("$[0].currentBalance").value(200));
    }

    // TEST 2: GET /wallet/ledger/{id} -> EXCEPTION
    @Test
    void testGetCustomerLedger_Exception() throws Exception {

        when(walletService.getWalletLedger(1L)).thenThrow(new RuntimeException("DB Error"));

        mockMvc.perform(get("/wallet/ledger/1"))
                .andExpect(status().isInternalServerError());
        // Your global exception handler should map to proper status code
    }

    // TEST 3: POST /wallet/transaction -> SUCCESS
    @Test
    void testCreateTransaction_Success() throws Exception {

        CreateTransactionDTO request = new CreateTransactionDTO();
        request.setOrder_id(10L);

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(500));
        transaction.setStatus("SUCCESS");
        transaction.setUpdatedAt(LocalDateTime.now());

        when(walletService.createTransaction(10L)).thenReturn(transaction);

        mockMvc.perform(post("/wallet/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(500))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    // TEST 4: POST /wallet/transaction -> EXCEPTION
    @Test
    void testCreateTransaction_Exception() throws Exception {
        CreateTransactionDTO request = new CreateTransactionDTO();
        request.setOrder_id(10L);

        when(walletService.createTransaction(10L))
                .thenThrow(new RuntimeException("Processing Failed"));

        mockMvc.perform(post("/wallet/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
        // If your exception handler maps differently, update expected status
    }
}
