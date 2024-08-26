package com.example.ecom.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments_v2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentV2 {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "status")
    private String transactionStatus;

    @Column(name = "amount")
    private String transactionAmount;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "url")
    private String paymentUrl;

    @Column(name = "date")
    private LocalDateTime transactionDate;
}
