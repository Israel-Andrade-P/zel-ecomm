package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.OrderItemEntity;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query(
            """
                    SELECT COALESCE(SUM(oi.price * oi.quantity), 0)\s
                    FROM OrderItemEntity oi WHERE oi.order.status = ?1
                   \s"""
    )
    BigDecimal calculateRevenue(OrderStatus status);
}
