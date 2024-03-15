package com.intent.BookStore.unit.controller;

import com.Intent.shop.controller.ProductController;
import com.Intent.shop.dto.ProductDTO;
import com.Intent.shop.facade.ProductFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductFacade productFacade;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProducts() throws Exception {
        int pageNum = 1;
        int pageSize = 10;
        ProductDTO product1 = new ProductDTO(1L, "Product 1", "Small", BigDecimal.valueOf(9.99), 10, "Description 1");
        ProductDTO product2 = new ProductDTO(2L, "Product 2", "Medium", BigDecimal.valueOf(19.99), 5, "Description 2");
        List<ProductDTO> products = Arrays.asList(product1, product2);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize); // Создаем объект PageRequest
        Page<ProductDTO> productPage = new PageImpl<>(products, pageable, products.size());
        when(productFacade.getAllProducts(pageNum, pageSize)).thenReturn(productPage);

        mockMvc.perform(get("/product")
                        .param("pageNum", String.valueOf(pageNum))
                        .param("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Product 1")))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].name", is("Product 2")));
    }

    @Test
    void getProductById() throws Exception {
        Long productId = 1L;
        ProductDTO product = new ProductDTO(productId, "Product 1", "Small", BigDecimal.valueOf(9.99), 10, "Description 1");
        when(productFacade.getProductById(productId)).thenReturn(product);
        mockMvc.perform(get("/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productId.intValue())))
                .andExpect(jsonPath("$.name", is("Product 1")));
    }

    @Test
    void deleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/product/{id}", productId))
                .andExpect(status().isNoContent());
    }
}