package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zeldev.zel_e_comm.util.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Cart APIs", description = "APIs that manage carts")
public class CartController {
    private final CartService cartService;

    @PostMapping("/carts/products/{product_id}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("product_id") String productId,
                                                    @PathVariable("quantity") Integer quantity) {
        return ResponseEntity.status(CREATED).body(cartService.addProductToCart(productId, quantity));
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        return ResponseEntity.status(OK).body(cartService.getCarts());
    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCart() {
        return ResponseEntity.status(OK).body(cartService.getCart());
    }

    @PutMapping("/cart/products/{product_id}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable("product_id") String productId, @PathVariable("operation") String operation) {
        return ResponseEntity.status(OK).body(cartService.updateQuantity(productId, operation.equalsIgnoreCase("delete") ? -1 : 1));
    }

    @DeleteMapping("/carts/product/{product_id}")
    public ResponseEntity<Response> deleteItem(@PathVariable("product_id") String productId, HttpServletRequest request) {
        cartService.deleteItemFromCart(productId);
        return ResponseEntity.status(OK).body(getResponse(request, emptyMap(), "Item removed", OK));
    }
}
