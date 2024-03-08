package com.Intent.shop.facade.impl;


import com.Intent.shop.dto.ProductDTO;
import com.Intent.shop.facade.ProductFacade;
import com.Intent.shop.mapper.ProductMapperUtil;
import com.Intent.shop.model.Product;
import com.Intent.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.Intent.shop.mapper.ProductMapperUtil.toProduct;
import static com.Intent.shop.mapper.ProductMapperUtil.toProductDto;


@Component
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;
    @Override
    public Page<ProductDTO> getAllProducts(int pageNum, int pageSize) {
        Page<Product> productPage = productService.getAllProducts(pageNum, pageSize);
        return productPage.map(ProductMapperUtil::toProductDto);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productService.getProductById(id);
        return toProductDto(product);
    }


    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(toProduct(productDTO));
        return toProductDto(createdProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO updatedProductDTO) {
        Product undatedProduct = productService.updateProduct(id, toProduct(updatedProductDTO));
        return toProductDto(undatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }



}
