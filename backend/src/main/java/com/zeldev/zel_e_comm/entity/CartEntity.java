package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEntity extends BaseEntity {
    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Builder.Default
    //orphanRemoval = true -> Only deletes the CartItem entity if the relationship in memory is broken, by calling the removeItem method
    //it makes the item orphan and therefore Hibernate automatically generates a DELETE query.
    //Orphan removal is triggered by relationship state changes, not by repository calls. Even calling this cartItemRepository.delete(item)
    //wouldn't tigger a deletion in DB, cause in memory relationship still exists.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<CartItemEntity> cartItems = new HashSet<>();

    @Transient
    public BigDecimal getTotalPrice() {
        return cartItems
                .stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(CartItemEntity item) {
        cartItems.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItemEntity item) {
        cartItems.remove(item);
        item.setCart(null);
    }

    @PrePersist
    private void initPublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
