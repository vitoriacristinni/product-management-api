package com.cristinni.productmanagement.service;

import com.cristinni.productmanagement.dto.ProductRequestDTO;
import com.cristinni.productmanagement.dto.ProductResponseDTO;
import com.cristinni.productmanagement.entity.Product;
import com.cristinni.productmanagement.exception.ResourceNotFoundException;
import com.cristinni.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    private ProductRequestDTO request;

    @BeforeEach
    void setUp() {

        product = new Product(
                "Notebook Lenovo",
                "Notebook para uso profissional",
                new BigDecimal("3499.90"),
                10
        );

        request = new ProductRequestDTO();

        request.setName("Notebook Lenovo");
        request.setDescription("Notebook para uso profissional");
        request.setPrice(new BigDecimal("3499.90"));
        request.setStock(10);
    }

    @Test
    void shouldCreateProduct() {

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponseDTO response =
                productService.create(request);

        assertNotNull(response);
        assertEquals("Notebook Lenovo", response.getName());
      //  assertEquals("Produto Diferente", response.getName());
        assertEquals(
                new BigDecimal("3499.90"),
                response.getPrice()
        );
        assertEquals(10, response.getStock());

        verify(productRepository, times(1))
                .save(any(Product.class));
    }

    @Test
    void shouldFindAllProducts() {

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        List<ProductResponseDTO> response =
                productService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(
                "Notebook Lenovo",
                response.get(0).getName()
        );

        verify(productRepository, times(1))
                .findAll();
    }

    @Test
    void shouldFindProductById() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponseDTO response =
                productService.findById(1L);

        assertNotNull(response);
        assertEquals(
                "Notebook Lenovo",
                response.getName()
        );

        verify(productRepository, times(1))
                .findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.findById(999L)
        );

        verify(productRepository, times(1))
                .findById(999L);
    }

    @Test
    void shouldUpdateProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductRequestDTO updateRequest =
                new ProductRequestDTO();

        updateRequest.setName("Notebook Lenovo ThinkPad");
        updateRequest.setDescription(
                "Notebook profissional atualizado"
        );
        updateRequest.setPrice(
                new BigDecimal("3899.90")
        );
        updateRequest.setStock(8);

        ProductResponseDTO response =
                productService.update(1L, updateRequest);

        assertNotNull(response);
        assertEquals(
                "Notebook Lenovo ThinkPad",
                response.getName()
        );
        assertEquals(
                new BigDecimal("3899.90"),
                response.getPrice()
        );
        assertEquals(8, response.getStock());

        verify(productRepository, times(1))
                .findById(1L);

        verify(productRepository, times(1))
                .save(product);
    }

    @Test
    void shouldDeleteProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository, times(1))
                .findById(1L);

        verify(productRepository, times(1))
                .delete(product);
    }
}