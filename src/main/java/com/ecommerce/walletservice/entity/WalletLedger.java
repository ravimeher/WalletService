package com.ecommerce.walletservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;


@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "wallet_ledger")
public class WalletLedger extends BaseModel {

    @Column(name = "wallet_id")
    private Long walletId;

    @Column(name = "previous_balance", precision = 19, scale = 4)
    private BigDecimal previousBalance;

    @Column(name = "current_balance", precision = 19, scale = 4)
    private BigDecimal currentBalance;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "transaction_id")
    private Long transactionId;

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(BigDecimal previousBalance) {
        this.previousBalance = previousBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}

