package com.example.ecom.service;

import java.util.List;

import com.example.ecom.entity.Brand;

public interface BrandService {

    public List<Brand> getAllBrands();

    public Brand createBrand(Brand brand);
}
