package com.example.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.dto.AddCartItemRequest;
import com.example.ecom.dto.CartDTO;
import com.example.ecom.dto.UpdateCartItemRequest;
import com.example.ecom.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<String> addItemToCart(@RequestBody AddCartItemRequest request) {
        cartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable Long cartItemId,
            @RequestBody UpdateCartItemRequest request) {
        cartService.updateCartItemQuantity(cartItemId, request.getQuantity());
        return ResponseEntity.ok("Cart item quantity updated successfully");
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getUserCart(@PathVariable Long userId) {
        CartDTO cartDTO = cartService.getUserCart(userId);
        return ResponseEntity.ok(cartDTO);
    }
}
