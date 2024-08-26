package com.example.ecom.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInitiateResponse {

    @JsonProperty(value = "transaction_id", required = true)
    private String transactionId;

    @JsonProperty(value = "payment_url", required = true)
    private String url;

    @JsonProperty(value = "transaction_amount", required = true)
    private String amount;

    @JsonProperty(value = "identifier", required = true)
    private String orderId;

    @JsonProperty(value = "transaction_status", required = true)
    private String status;

    @JsonProperty(value = "transaction_date", required = true)
    private LocalDateTime transactionDate;
}
