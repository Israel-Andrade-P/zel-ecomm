package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    @Query(
            """
                   SELECT ci FROM CartItemEntity ci WHERE ci.cart.id=?1 AND ci.product.publicId=?2\s
                   \s"""
    )
    Optional<CartItemEntity> findCartItemEntityByProductIdAndCartId(Long cartId, UUID productId);

    //when cart states are added, join carts as well: JOIN ci.cart c WHERE c.state = :ACTIVE
    @Query("""
        SELECT ci
        FROM CartItemEntity ci
        JOIN ci.product p
        WHERE p.publicId=?1
    """)
    List<CartItemEntity> findActiveCartItemsByProductId(UUID productId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        DELETE FROM CartItemEntity ci
        WHERE ci.product.publicId=?1
    """)
    void deleteByProductPublicId(UUID productId);
}
