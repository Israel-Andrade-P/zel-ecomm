package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByPublicId(UUID publicId);

    Page<ProductEntity> findByCategory_IdOrderByPriceAsc(Long id, Pageable pageDetails);

    Page<ProductEntity> findByNameLikeIgnoreCase(String keyword, Pageable pageDetails);
}
