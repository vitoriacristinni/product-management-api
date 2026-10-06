package com.cristinni.productmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.cristinni.productmanagement.dto.ProductRequestDTO;
import com.cristinni.productmanagement.dto.ProductResponseDTO;
import com.cristinni.productmanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(
        name = "Products",
        description = "Operations for product management"
)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Create a product",
            description = "Creates a new product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            )
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @RequestBody ProductRequestDTO request) {

        ProductResponseDTO response =
                productService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "List all products",
            description = "Returns all registered products"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Products retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {

        return ResponseEntity.ok(
                productService.findAll()
        );
    }

    @Operation(
            summary = "Find a product by ID",
            description = "Returns a product using its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.findById(id)
        );
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request) {

        return ResponseEntity.ok(
                productService.update(id, request)
        );
    }

    @Operation(
            summary = "Delete a product",
            description = "Deletes an existing product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}