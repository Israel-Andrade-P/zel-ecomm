package com.zeldev.zel_e_comm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zeldev.zel_e_comm.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  @Query("SELECT o FROM OrderEntity o WHERE o.publicId=?1")
  Optional<OrderEntity> findByPublicId(String orderId);

  @Query("""
       SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END\s
       FROM OrderEntity o WHERE o.publicId = ?1 AND o.user.email = ?2
      \s""")
  boolean existsByIdAndUserEmail(String orderId, String loggedInEmail);

  @Query(value = """
      SELECT DISTINCT o.*
      FROM orders o
      JOIN order_items oi ON oi.order_id = o.id
      JOIN products p ON p.id = oi.product_id
      WHERE p.seller_id = :sellerId
      """, countQuery = """
      SELECT COUNT(DISTINCT o.id)
      FROM orders o
      JOIN order_items oi ON oi.order_id = o.id
      JOIN products p ON p.id = oi.product_id
      WHERE p.seller_id = :sellerId
      """, nativeQuery = true)
  Page<OrderEntity> findSellerOrders(@Param ("sellerId") Long sellerId, Pageable pageDetails);
}
