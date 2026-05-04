package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
