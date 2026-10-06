package com.cristinni.productmanagement.controller;

import com.cristinni.productmanagement.dto.ProductResponseDTO;
import com.cristinni.productmanagement.exception.GlobalExceptionHandler;
import com.cristinni.productmanagement.exception.ResourceNotFoundException;
import com.cristinni.productmanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {

        ProductResponseDTO response =
                new ProductResponseDTO(
                        1L,
                        "Notebook Lenovo",
                        "Notebook profissional",
                        new BigDecimal("3499.90"),
                        10
                );

        when(productService.create(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "Notebook Lenovo",
                                    "description": "Notebook profissional",
                                    "price": 3499.90,
                                    "stock": 10
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Notebook Lenovo"))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void shouldReturnAllProducts() throws Exception {

        ProductResponseDTO product =
                new ProductResponseDTO(
                        1L,
                        "Notebook Lenovo",
                        "Notebook profissional",
                        new BigDecimal("3499.90"),
                        10
                );

        when(productService.findAll())
                .thenReturn(List.of(product));

        mockMvc.perform(
                        get("/products")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Notebook Lenovo"))
                .andExpect(jsonPath("$[0].stock")
                        .value(10));
    }

    @Test
    void shouldReturnProductById() throws Exception {

        ProductResponseDTO product =
                new ProductResponseDTO(
                        1L,
                        "Notebook Lenovo",
                        "Notebook profissional",
                        new BigDecimal("3499.90"),
                        10
                );

        when(productService.findById(1L))
                .thenReturn(product);

        mockMvc.perform(
                        get("/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Notebook Lenovo"));
    }

    @Test
    void shouldReturnBadRequestWhenProductIsInvalid()
            throws Exception {

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "",
                                    "description": "",
                                    "price": -100,
                                    "stock": -5
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist()
            throws Exception {

        when(productService.findById(999L))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Product not found"
                        )
                );

        mockMvc.perform(
                        get("/products/999")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Product not found"));
    }
}