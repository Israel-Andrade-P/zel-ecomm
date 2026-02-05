package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.publicId=?1")
    Optional<OrderEntity> findByPublicId(String orderId);
}
