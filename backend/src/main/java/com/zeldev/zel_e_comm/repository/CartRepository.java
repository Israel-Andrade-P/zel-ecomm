package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Query(
            """
                    SELECT c FROM CartEntity c WHERE c.user.email=?1
                    """
    )
    Optional<CartEntity> findCartByUserEmail(String email);

    @Query(
            """
                    SELECT c FROM CartEntity c WHERE c.user.email=?1 AND c.id=?2
                    """
    )
    Optional<CartEntity> findCartByEmailAndCartId(String email, Long cartId);

//    @Query(
//            """
//                    SELECT c FROM CartEntity c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.publicId=?1
//                    """
//    )
//    List<CartEntity> findCartsByProductId(UUID productId);
}
