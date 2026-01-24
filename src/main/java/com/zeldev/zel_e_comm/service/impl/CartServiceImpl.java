package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.exception.InsufficientStockException;
import com.zeldev.zel_e_comm.repository.CartRepository;
import com.zeldev.zel_e_comm.service.CartItemService;
import com.zeldev.zel_e_comm.service.CartService;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private void validateQuantity(Integer requestedQuantity, Integer inStock) {
        if (inStock == 0) throw new InsufficientStockException("The requested quantity is not available");
        if (requestedQuantity > inStock) throw new InsufficientStockException("The requested quantity is not available");
    }

    private CartEntity createCart() {
        var user = authUtils.getLoggedInUser();
        return cartRepository.findCartByUserEmail(authUtils.getLoggedInEmail()).orElseGet(() -> buildCart(user));
    }
}
