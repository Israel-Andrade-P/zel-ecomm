package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.publicId=?1")
    Optional<ProductEntity> findByPublicId(UUID publicId);

    @Query(
            """
                    SELECT p FROM ProductEntity p WHERE p.category.id=?1 ORDER BY p.price ASC\s
                   \s"""
    )
    Page<ProductEntity> findByCategory_IdOrderByPriceAsc(Long id, Pageable pageDetails);

    @Query(
            """
                    SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))
                    """
    )
    Page<ProductEntity> findByNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    @Query("SELECT p FROM ProductEntity p WHERE p.seller.email=?1")
    Page<ProductEntity> findBySellerEmail(String email, Pageable pageDetails);

    @Query(
            """
                     SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END\s
                     FROM ProductEntity p WHERE p.publicId = ?1 AND p.seller.email = ?2
                    \s"""
    )
    boolean existsByIdAndSellerEmail(UUID productId, String sellerEmail);
}
