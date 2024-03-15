package com.intent.BookStore.unit.service;

import com.Intent.shop.exception.ProductNotFoundException;
import com.Intent.shop.model.Product;
import com.Intent.shop.repository.ProductRepository;
import com.Intent.shop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProductsTest() {
        Product product1 = new Product().setId(1L).setName("Product 1").setPrice(BigDecimal.valueOf(10.00)).setQuantity(5).setDescription("Description 1");
        Product product2 = new Product().setId(2L).setName("Product 2").setPrice(BigDecimal.valueOf(20.00)).setQuantity(10).setDescription("Description 2");
        Product product3 = new Product().setId(3L).setName("Product 3").setPrice(BigDecimal.valueOf(30.00)).setQuantity(15).setDescription("Description 3");
        Pageable pageable = PageRequest.of(0, 3, Sort.by("id").ascending());
        Page<Product> productsPage = new PageImpl<>(List.of(product1, product2, product3), pageable, 3);
        when(productRepository.findAll(pageable)).thenReturn(productsPage);
        Page<Product> result = productService.getAllProducts(1, 3);
        assertThat(result.getTotalElements(), is((long) productsPage.getNumberOfElements()));
        assertThat(result, contains(product1, product2, product3));
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void getProductByIdTest() {
        Long productId = 1L;
        Product product = new Product().setId(productId).setName("Product").setPrice(BigDecimal.TEN).setQuantity(5).setDescription("Description");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Product result = productService.getProductById(productId);
        assertThat(result, equalTo(product));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductByIdWithNotFoundExceptionTest() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void createProductTest() {
        Product newProduct = new Product().setName("New Product").setPrice(BigDecimal.valueOf(25.00)).setQuantity(10).setDescription("Description");
        when(productRepository.save(newProduct)).thenReturn(newProduct);
        Product result = productService.createProduct(newProduct);
        assertThat(result, equalTo(newProduct));
        verify(productRepository, times(1)).save(newProduct);
    }


    @Test
    void updateProductTest() {
        Long productId = 1L;
        Product updatedProduct = new Product().setId(productId).setName("Updated Product").setPrice(BigDecimal.valueOf(30.00)).setQuantity(15).setDescription("Updated Description");
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
        Product result = productService.updateProduct(productId, updatedProduct);
        assertThat(result, equalTo(updatedProduct));
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void deleteProductTest() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteProductWithNotFoundExceptionTest() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).deleteById(productId);
    }
}
