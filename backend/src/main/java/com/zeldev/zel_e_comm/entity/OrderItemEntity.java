package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.AbstractLineItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Immutable
@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@AttributeOverrides({
        @AttributeOverride(
                name = "price",
                column = @Column(nullable = false, updatable = false)
        ),
        @AttributeOverride(
                name = "quantity",
                column = @Column(nullable = false, updatable = false)
        )
})
public class OrderItemEntity extends AbstractLineItem {
    @Column(name = "product_name", nullable = false, updatable = false)
    private String productName;
    @Column(name = "discount_amount", nullable = false, updatable = false)
    private BigDecimal discountAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
}
