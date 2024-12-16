package com.example.ecom.util;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExternalRestUtils {
    
    private final WebClient.Builder webClientBuilder;

    public String getCall(final String url) {
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String postCall(String payload, final String url) {
        return webClientBuilder.build()
                .post()
                .uri(url)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
