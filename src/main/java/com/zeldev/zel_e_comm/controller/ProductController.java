package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{category_name}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO request, @PathVariable("category_name") String categoryName) {
        return ResponseEntity.status(CREATED).body(productService.create(request, categoryName));
    }
}
