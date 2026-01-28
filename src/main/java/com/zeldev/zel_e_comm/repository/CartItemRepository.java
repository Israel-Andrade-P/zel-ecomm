package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    @Query(
            """
                   SELECT i FROM CartItemEntity i WHERE i.cart.id=?1 AND i.product.publicId=?2\s
                   \s"""
    )
    Optional<CartItemEntity> findCartItemEntityByProductIdAndCartId(Long cartId, UUID productId);
}
