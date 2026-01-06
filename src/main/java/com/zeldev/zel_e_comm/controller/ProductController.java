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

import static com.zeldev.zel_e_comm.constants.Constants.*;
import static com.zeldev.zel_e_comm.constants.Constants.SORT_DIR;
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
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.status(OK).body(productService.getAllProducts(page, size, sortBy, sortOrder));
    }

    @GetMapping("/public/categories/{category_id}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable("category_id") String id,
            @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.status(OK).body(productService.getProductsByCategory(id, page, size, sortBy, sortOrder));
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable("keyword") String keyword,
            @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder

    ) {
        return ResponseEntity.status(OK).body(productService.getProductsByKeyword(keyword, page, size, sortBy, sortOrder));
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
