package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.model.CategoryEntity;
import com.zeldev.zel_e_comm.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory_IdOrderByPriceAsc(Long id);

    List<ProductEntity> findByNameLikeIgnoreCase(String keyword);
}
