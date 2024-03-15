package com.intent.BookStore.integration.controller;

import com.Intent.shop.ShopApplication;
import com.Intent.shop.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Intent.shop.controller.ProductController;
import com.Intent.shop.dto.ProductDTO;
import com.Intent.shop.facade.ProductFacade;
import com.intent.BookStore.integration.AbstractTestContainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ShopApplication.class)
class ProductControllerIntegrationTest extends AbstractTestContainer {

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        setUp();
    }

    @BeforeEach
    public void setupMVC() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productFacade)).build();
    }

    @AfterAll
    public static void tearDownEach() {
        tearDown();
    }

    @Test
    void getAllProductsTest() throws Exception {
        ProductDTO product1 = new ProductDTO().setName("Product 1").setSize("Small").setPrice(BigDecimal.valueOf(10.0)).setQuantity(5).setDescription("Description 1");
        ProductDTO product2 = new ProductDTO().setName("Product 2").setSize("Medium").setPrice(BigDecimal.valueOf(15.0)).setQuantity(10).setDescription("Description 2");
        ProductDTO product3 = new ProductDTO().setName("Product 3").setSize("Large").setPrice(BigDecimal.valueOf(20.0)).setQuantity(8).setDescription("Description 3");
        ProductDTO createdProduct1 = productFacade.createProduct(product1);
        ProductDTO createdProduct2 = productFacade.createProduct(product2);
        ProductDTO createdProduct3 = productFacade.createProduct(product3);

        mockMvc.perform(MockMvcRequestBuilders.get("/product")
                        .queryParam("pageNum", "1")
                        .queryParam("pageSize", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id", is(createdProduct1.getId().intValue())))
                .andExpect(jsonPath("$.content[1].id", is(createdProduct2.getId().intValue())))
                .andExpect(jsonPath("$.content[2].id", is(createdProduct3.getId().intValue())))
                .andExpect(jsonPath("$.content[0].name", is(product1.getName())))
                .andExpect(jsonPath("$.content[1].name", is(product2.getName())))
                .andExpect(jsonPath("$.content[2].name", is(product3.getName())));

        productRepository.deleteAll();
    }

    @Test
    void getProductByIdTest() throws Exception {
        ProductDTO product = new ProductDTO().setName("Product 1").setSize("Small").setPrice(BigDecimal.valueOf(10.0)).setQuantity(5).setDescription("Description 1");
        ProductDTO createdProduct = productFacade.createProduct(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", createdProduct.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdProduct.getId().intValue())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.size", is(product.getSize())))
                .andExpect(jsonPath("$.price", is(product.getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(product.getQuantity())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));

        productRepository.deleteAll();
    }

    @Test
    void createProductTest() throws Exception {
        ProductDTO product = new ProductDTO().setName("Product 1").setSize("Small").setPrice(BigDecimal.valueOf(10.0)).setQuantity(5).setDescription("It's my custom own Description and I think it's cool ");
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.size", is(product.getSize())))
                .andExpect(jsonPath("$.price", is(product.getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(product.getQuantity())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));

        productRepository.deleteAll();
    }

    @Test
    void updateProductTest() throws Exception {
        ProductDTO product = new ProductDTO().setName("Product 1").setSize("Small").setPrice(BigDecimal.valueOf(10.0)).setQuantity(5).setDescription("It's my custom own Description and I think it's cool ");
        ProductDTO createdProduct = productFacade.createProduct(product);
        ProductDTO updatedProductDTO = new ProductDTO().setName("Updated Product").setSize("Medium").setPrice(BigDecimal.valueOf(20.0)).setQuantity(3).setDescription("It's my custom own Description and I think it's cool ");

        mockMvc.perform(put("/product/{id}", createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProductDTO))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdProduct.getId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedProductDTO.getName())))
                .andExpect(jsonPath("$.size", is(updatedProductDTO.getSize())))
                .andExpect(jsonPath("$.price", is(updatedProductDTO.getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(updatedProductDTO.getQuantity())))
                .andExpect(jsonPath("$.description", is(updatedProductDTO.getDescription())));
    }

    @Test
    void deleteProductTest() throws Exception {
        ProductDTO product = new ProductDTO().setName("Product 1").setSize("Small").setPrice(BigDecimal.valueOf(10.0)).setQuantity(5).setDescription("Description 1");
        ProductDTO createdProduct = productFacade.createProduct(product);
        mockMvc.perform(delete("/product/{id}", createdProduct.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

    }
}
