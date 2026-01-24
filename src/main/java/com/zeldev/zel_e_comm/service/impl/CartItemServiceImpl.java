package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.CartItemAlreadyAddedException;
import com.zeldev.zel_e_comm.repository.CartItemRepository;
import com.zeldev.zel_e_comm.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zeldev.zel_e_comm.util.CartItemUtils.buildCartItem;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemEntity createItem(ProductEntity product, CartEntity cart, Integer quantity) {
        return cartItemRepository.save(buildCartItem(product, cart, quantity));
    }

    @Override
    public void checkCartItemByCartAndProduct(CartEntity cart, ProductEntity product) {
        cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), product.getId())
                .ifPresent(cartItem -> {
                    throw new CartItemAlreadyAddedException(String.format("%s is already in the shopping cart", cartItem.getProduct().getName()));
                });
    }
}
