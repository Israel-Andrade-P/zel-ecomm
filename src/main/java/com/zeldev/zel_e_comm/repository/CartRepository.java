package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Query(
            """
                    SELECT c FROM CartEntity c WHERE c.user.email=?1
                    """
    )
    Optional<CartEntity> findCartByUserEmail(String email);
}
