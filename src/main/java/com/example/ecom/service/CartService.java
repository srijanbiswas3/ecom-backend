package com.example.ecom.service;

import com.example.ecom.dto.CartDTO;

public interface CartService {

    
public void addItemToCart(Long userId,Long productId,int quantity);

public void updateCartItemQuantity(Long cartItemId,int quantity);

public void removeItemFromCart(Long cartItemId);

public CartDTO getUserCart(Long userId);



}
