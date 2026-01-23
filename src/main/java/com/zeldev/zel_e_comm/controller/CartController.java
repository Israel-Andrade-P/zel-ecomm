package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/carts/products/{product_id}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("product_id") String productId,
                                                    @PathVariable("quantity") Integer quantity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addProductToCart(productId, quantity));
    }
}
