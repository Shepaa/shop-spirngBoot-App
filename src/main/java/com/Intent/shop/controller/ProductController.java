package com.Intent.shop.controller;

import com.Intent.shop.dto.ProductDTO;
import com.Intent.shop.facade.ProductFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade productFacade;

    @GetMapping("/product")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page should be greater or equals one")
            int pageNum,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page size should be greater or equals one")
            int pageSize) {
        Page<ProductDTO> products = productFacade.getAllProducts(pageNum, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productFacade.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productFacade.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO updatedProductDTO) {
        ProductDTO updatedProduct = productFacade.updateProduct(id, updatedProductDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productFacade.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}