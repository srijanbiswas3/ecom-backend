package com.example.ecom.service;

import java.util.List;

import com.example.ecom.entity.Product;

public interface ProductService {

    public List<Product> getAllProducts();

    public Product getProductById(Long id);

    public List<Product> getProductsByCategory(Long categoryId);
}
