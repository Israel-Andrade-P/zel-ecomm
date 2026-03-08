package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.request.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.service.ProductOrchestrationService;
import com.zeldev.zel_e_comm.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.zeldev.zel_e_comm.constants.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Product APIs", description = "APIs that manage products")
public class ProductController {
    private final ProductService productService;
    private final ProductOrchestrationService orchestrationService;

    @PostMapping("/seller/categories/{category_name}/product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductDTO> addProduct(
            @Parameter(description = "ID of category product belongs to")
            @RequestBody @Valid ProductDTO request,
            @PathVariable("category_name") String categoryName) {
        return ResponseEntity.status(CREATED).body(productService.create(request, categoryName));
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.status(OK).body(productService.getAllProducts(page, size, sortBy, sortOrder));
    }

    @GetMapping("/seller/products")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> getAllProductsBySeller(
            @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.status(OK).body(productService.getProductsBySeller(page, size, sortBy, sortOrder));
    }

    @GetMapping("/categories/{category_id}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable("category_id") String id,
            @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.status(OK).body(productService.getProductsByCategory(id, page, size, sortBy, sortOrder));
    }

    @GetMapping("/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable("keyword") String keyword,
            @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
            @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder

    ) {
        return ResponseEntity.status(OK).body(productService.getProductsByKeyword(keyword, page, size, sortBy, sortOrder));
    }


    @PutMapping("/manage/products/update/{product_id}")
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isOwner(#productId)")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO request, @PathVariable("product_id") String productId) {
        return ResponseEntity.status(OK).body(orchestrationService.updateProductAndSyncCarts(request, productId));
    }

    @DeleteMapping("/manage/products/delete/{product_id}")
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isOwner(#productId)")
    public ResponseEntity<ProductDTO> deleteProduct(
            @Parameter(description = "ID of product to be deleted")
            @PathVariable("product_id") String productId) {
        return ResponseEntity.status(OK).body(orchestrationService.deleteCartItemsAfterProduct(productId));
    }

    @PutMapping("/manage/products/{product_id}/image")
    @PreAuthorize("hasRole('ADMIN') or @productSecurity.isOwner(#productId)")
    public ResponseEntity<ProductDTO> updateImage(
            @Parameter(description = "Image file to be uploaded")
            @PathVariable("product_id") String productId,
            @RequestParam(name = "image") MultipartFile image) throws IOException {
        return ResponseEntity.status(OK).body(productService.updateImage(productId, image));
    }
}
