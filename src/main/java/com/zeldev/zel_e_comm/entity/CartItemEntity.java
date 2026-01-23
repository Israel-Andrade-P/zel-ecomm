package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemEntity extends BaseEntity {
    private BigDecimal price;
    private Integer discount;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
