package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{category_name}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO request, @PathVariable("category_name") String categoryName) {
        return ResponseEntity.status(CREATED).body(productService.create(request, categoryName));
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        return ResponseEntity.status(OK).body(productService.getAllProducts());
    }

    @GetMapping("/public/categories/{category_id}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable("category_id") String id) {
        return ResponseEntity.status(OK).body(productService.getProductsByCategory(id));
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable("keyword") String keyword) {
        return ResponseEntity.status(OK).body(productService.getProductsByKeyword(keyword));
    }

    @PutMapping("/admin/products/{product_id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO request, @PathVariable("product_id") String product_id) {
        return ResponseEntity.status(OK).body(productService.updateProduct(request,product_id));
    }

    @DeleteMapping("/admin/products/{product_id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("product_id") String product_id) {
        return ResponseEntity.status(OK).body(productService.deleteProduct(product_id));
    }

    @PutMapping("/admin/products/{product_id}/image")
    public ResponseEntity<ProductDTO> updateImage(@PathVariable("product_id") String product_id, @RequestParam(name = "image") MultipartFile image) throws IOException {
        return ResponseEntity.status(OK).body(productService.updateImage(product_id, image));
    }
}
