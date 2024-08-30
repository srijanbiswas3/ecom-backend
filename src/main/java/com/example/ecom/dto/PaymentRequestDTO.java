package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private ClientDetails clientDetails;
    private OrderDetails orderDetails;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientDetails {
        private String name;
        private String phone;
        private String email;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetails {
        private String cartId;
        private String amount;
        private String currency;
        private String description;
        private String transactionType;
        private String transactionClass;
    }
}
