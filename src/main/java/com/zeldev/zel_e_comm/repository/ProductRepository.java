package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
