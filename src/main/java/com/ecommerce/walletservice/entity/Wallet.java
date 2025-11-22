package com.ecommerce.walletservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet extends BaseModel {

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "currency", length = 30)
    private String currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

