package com.cristinni.productmanagement.service;

import com.cristinni.productmanagement.dto.ProductRequestDTO;
import com.cristinni.productmanagement.dto.ProductResponseDTO;
import com.cristinni.productmanagement.entity.Product;
import com.cristinni.productmanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.cristinni.productmanagement.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO create(ProductRequestDTO request) {

        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );

        Product savedProduct = productRepository.save(product);

        return toResponseDTO(savedProduct);
    }

    public List<ProductResponseDTO> findAll() {

        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return toResponseDTO(product);
    }

    public ProductResponseDTO update(
            Long id,
            ProductRequestDTO request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStock(request.getStock());

        Product updatedProduct =
                productRepository.save(existingProduct);

        return toResponseDTO(updatedProduct);
    }

    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    private ProductResponseDTO toResponseDTO(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}