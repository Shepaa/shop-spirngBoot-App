package com.Intent.shop.service;


import com.Intent.shop.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getAllProducts(int pageNum, int pageSize);

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product updatedProduct);

    void deleteProduct(Long id);

}
