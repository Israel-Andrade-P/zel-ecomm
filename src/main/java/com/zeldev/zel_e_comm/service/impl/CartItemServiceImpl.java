package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.CartItemAlreadyAddedException;
import com.zeldev.zel_e_comm.exception.InsufficientStockException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.CartItemRepository;
import com.zeldev.zel_e_comm.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        cartItemRepository.findCartItemEntityByProductIdAndCartId(cart.getId(), product.getPublicId())
                .ifPresent(cartItem -> {
                    throw new CartItemAlreadyAddedException(String.format("%s is already in the shopping cart", cartItem.getProduct().getName()));
                });
    }

    @Override
    public void updateQuantity(CartEntity cart, ProductEntity product, Integer quantity) {
        var item = getCartItemByCartAndProduct(cart.getId(), product.getPublicId());
        var newQuantity = item.getQuantity() + quantity;
        validateQuantity(newQuantity, product.getQuantity());
        if (newQuantity <= 0) {
            cart.removeItem(item);
            return;
        }
        item.setQuantity(newQuantity);
        item.setPrice(product.getPrice());
        item.setDiscount(product.getDiscount());
    }

    @Override
    public CartItemEntity getCartItemByCartAndProduct(Long cartId, UUID productId) {
        return cartItemRepository.findCartItemEntityByProductIdAndCartId(cartId, productId).orElseThrow(() -> new ResourceNotFoundException("Cart item", productId.toString()));
    }

    @Override
    public void validateQuantity(Integer requestedQuantity, Integer inStock) {
        if (inStock == 0) throw new InsufficientStockException("The requested quantity is not available");
        if (requestedQuantity > inStock) throw new InsufficientStockException("The requested quantity is not available");
    }

    @Override
    public List<CartItemEntity> findActiveCartItemsByProductId(UUID productId) {
        return cartItemRepository.findActiveCartItemsByProductId(productId);
    }

    @Override
    public void deleteByProductPublicId(UUID productId) {
        cartItemRepository.deleteByProductPublicId(productId);
    }
}
