package com.java.product.product;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Integer> createProduct
            (@RequestBody @Valid ProductRequest request)
    {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById
            (@PathVariable("product-id") Integer productId)
    {
        return ResponseEntity.ok(productService.findById(productId));

    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts
            (@RequestBody List<ProductPurchaseRequest> request)
    {
        return  ResponseEntity.ok(productService.purchaseProducts(request));
    }

}
