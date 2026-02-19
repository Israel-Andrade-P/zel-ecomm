package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByPublicId(UUID publicId);

    Page<ProductEntity> findByCategory_IdOrderByPriceAsc(Long id, Pageable pageDetails);

    Page<ProductEntity> findByNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    @Query("SELECT p FROM ProductEntity p WHERE p.seller.username=?1")
    Page<ProductEntity> findBySellerId(String username, Pageable pageDetails);

    @Query(
            """
                    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END\s
                    FROM ProductEntity p WHERE p.publicId = ?1 AND p.seller.email = ?2
                   \s"""
    )
    boolean existsByIdAndSellerEmail(UUID productId, String sellerEmail);
}
