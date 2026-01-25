package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.InsufficientStockException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.CartRepository;
import com.zeldev.zel_e_comm.service.CartItemService;
import com.zeldev.zel_e_comm.service.CartService;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import com.zeldev.zel_e_comm.util.CartUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zeldev.zel_e_comm.util.CartUtils.buildCart;
import static com.zeldev.zel_e_comm.util.CartUtils.toDTO;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public CartDTO addProductToCart(String productId, Integer quantity) {
        var cart = createCart();

        var product = productService.findByPublicId(productId);

        cartItemService.checkCartItemByCartAndProduct(cart, product);

        validateQuantity(quantity, product.getQuantity());

        cartRepository.save(cart);

        cartItemService.createItem(product, cart, quantity);

        return toDTO(cart);
    }

    @Override
    public @Nullable List<CartDTO> getCarts() {
        var carts = cartRepository.findAll();
        if (carts.isEmpty()) throw new APIException("No carts have been created yet :(");
        return carts.stream().map(CartUtils::toDTO).toList();
    }

    @Override
    public @Nullable CartDTO getCart() {
        var cart = getCartByEmail(authUtils.getLoggedInEmail());
        //var cart = cartRepository.findCartByEmailAndCartId(email, cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart"));
        return toDTO(cart);
    }
    //TEST THIS METHOD
    @Transactional
    @Override
    public @Nullable CartDTO updateQuantity(String productId, Integer quantity) {
        CartEntity cart = getCartByEmail(authUtils.getLoggedInEmail());
        ProductEntity product = productService.findByPublicId(productId);
        validateQuantity(quantity, product.getQuantity());
        cartItemService.updateQuantity(cart, product, quantity);
        return toDTO(cart);
    }

    @Override
    public void deleteItemFromCart(String productId) {

    }

    private void validateQuantity(Integer requestedQuantity, Integer inStock) {
        if (inStock == 0) throw new InsufficientStockException("The requested quantity is not available");
        if (requestedQuantity > inStock) throw new InsufficientStockException("The requested quantity is not available");
    }

    private CartEntity createCart() {
        var user = authUtils.getLoggedInUser();
        return cartRepository.findCartByUserEmail(user.getEmail()).orElseGet(() -> buildCart(user));
    }

    private CartEntity getCartByEmail(String email) {
        return cartRepository.findCartByUserEmail(email).orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart"));
    }
}
