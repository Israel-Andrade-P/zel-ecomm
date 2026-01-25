package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.CartItemAlreadyAddedException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
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

    @Override
    public void updateQuantity(CartEntity cart, ProductEntity product, Integer quantity) {
        var item = getCartItemByCartAndProduct(cart, product);
        var newQuantity = item.getQuantity() + quantity;
        if (newQuantity <= 0) {
            cart.removeItem(item);
            return;
        }
        item.setQuantity(newQuantity);
        item.setPrice(product.getPrice());
        item.setDiscount(product.getDiscount());
    }


    private CartItemEntity getCartItemByCartAndProduct(CartEntity cart, ProductEntity product) {
        return cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), product.getId()).orElseThrow(() -> new ResourceNotFoundException("Cart item", product.getName()));
    }


}
