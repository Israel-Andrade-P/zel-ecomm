package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEntity extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItemEntity> cartItems = new HashSet<>();

    @Transient
    public BigDecimal getTotalPrice() {
        return cartItems
                .stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
