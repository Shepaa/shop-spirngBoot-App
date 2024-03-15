package com.intent.BookStore.unit.facade;

import com.Intent.shop.dto.ProductDTO;
import com.Intent.shop.facade.impl.ProductFacadeImpl;
import com.Intent.shop.model.Product;
import com.Intent.shop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductFacadeImplTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductFacadeImpl productFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts() {
        int pageNum = 0;
        int pageSize = 10;
        Product product1 = new Product(1L, "Product 1", "Small", BigDecimal.valueOf(9.99), 10, "Description 1");
        Product product2 = new Product(2L, "Product 2", "Medium", BigDecimal.valueOf(19.99), 5, "Description 2");
        List<Product> products = Arrays.asList(product1, product2);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(pageNum, pageSize), products.size());
        when(productService.getAllProducts(pageNum, pageSize)).thenReturn(productPage);

        Page<ProductDTO> result = productFacade.getAllProducts(pageNum, pageSize);

        verify(productService, times(1)).getAllProducts(pageNum, pageSize);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void getProductById() {
        Long productId = 1L;
        Product product = new Product(productId, "Product 1", "Small", BigDecimal.valueOf(9.99), 10, "Description 1");
        when(productService.getProductById(productId)).thenReturn(product);

        ProductDTO result = productFacade.getProductById(productId);

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void createProduct() {
        ProductDTO productDTO = new ProductDTO(null, "New Product", "Large", BigDecimal.valueOf(29.99), 20, "New Description");
        Product product = new Product(1L, "New Product", "Large", BigDecimal.valueOf(29.99), 20, "New Description");
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        ProductDTO result = productFacade.createProduct(productDTO);

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;

        productFacade.deleteProduct(productId);

        verify(productService, times(1)).deleteProduct(productId);
    }
}