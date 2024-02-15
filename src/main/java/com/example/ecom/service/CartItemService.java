package com.example.ecom.service;

public interface CartItemService {

    public void updateCartItemQuantity(Long cartItemId, int quantity);

    public void removeCartItem(Long cartItemId);

}
