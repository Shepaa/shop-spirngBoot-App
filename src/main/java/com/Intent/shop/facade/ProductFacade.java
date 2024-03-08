package com.Intent.shop.facade;


import com.Intent.shop.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductFacade {
    Page<ProductDTO> getAllProducts(int pageNum, int pageSize);

    ProductDTO getProductById(Long id);


    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO updatedProductDTO);

    void deleteProduct(Long id);


}
