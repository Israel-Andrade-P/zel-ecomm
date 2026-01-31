package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.BaseEntity;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity extends BaseEntity {
    private String orderId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemEntity> orderItems = new HashSet<>();

    @Transient
    public BigDecimal getTotalPrice() {
        return orderItems
                .stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(OrderItemEntity item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItemEntity item) {
        orderItems.remove(item);
        item.setOrder(null);
    }
}
