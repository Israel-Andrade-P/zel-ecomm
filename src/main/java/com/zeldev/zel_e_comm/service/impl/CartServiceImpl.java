package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.CartDTO;
import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.CartRepository;
import com.zeldev.zel_e_comm.service.CartItemService;
import com.zeldev.zel_e_comm.service.CartService;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import com.zeldev.zel_e_comm.util.CartUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.zeldev.zel_e_comm.util.CartUtils.buildCart;
import static com.zeldev.zel_e_comm.util.CartUtils.toDTO;

@Service
@RequiredArgsConstructor
//Inside a @Transactional method, you do NOT need to call save() after changing a managed entity.
//Hibernate will detect the changes and issue UPDATE/DELETE automatically at flush time.
//Each repository call runs in its own tiny transaction, those transactions start and end inside the repository
//Your service method has no unit of work, mutates detached entities, relies on save() everywhere
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final AuthUtils authUtils;

    @Override
    public CartDTO addProductToCart(String productId, Integer quantity) {
        var cart = createCart();

        var product = productService.findByPublicId(productId);

        cartItemService.checkCartItemByCartAndProduct(cart, product);

        productService.validateQuantity(quantity, product.getPublicId());

        cartRepository.save(cart);

        cartItemService.createItem(product, cart, quantity);

        return toDTO(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public @Nullable List<CartDTO> getCarts() {
        var carts = cartRepository.findAll();
        if (carts.isEmpty()) throw new APIException("No carts have been created yet :(");
        return carts.stream().map(CartUtils::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public @Nullable CartDTO getCart() {
        var cart = getCartByEmail(authUtils.getLoggedInEmail());
        return toDTO(cart);
    }

    @Override
    public @Nullable CartDTO updateQuantity(String productId, Integer quantity) {
        CartEntity cart = getCartByEmail(authUtils.getLoggedInEmail());
        //ProductEntity product = productService.findByPublicId(productId);
        cartItemService.updateQuantity(cart, productId, quantity);
        return toDTO(cart);
    }

    @Override
    public void deleteItemFromCart(String productId) {
        CartEntity cart = getCartByEmail(authUtils.getLoggedInEmail());
        cart.removeItem(cartItemService.getCartItemByCartAndProduct(cart.getId(), UUID.fromString(productId)));
    }

    @Override
    public CartEntity getCartByEmail(String email) {
        return cartRepository.findCartByUserEmail(email).orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart"));
    }

    private CartEntity createCart() {
        var user = authUtils.getLoggedInUser();
        return cartRepository.findCartByUserEmail(user.getEmail()).orElseGet(() -> buildCart(user));
    }
}
