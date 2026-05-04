package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.AbstractLineItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CartItemEntity extends AbstractLineItem {
    private Integer discount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;
}
