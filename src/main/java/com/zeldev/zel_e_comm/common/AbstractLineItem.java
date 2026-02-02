package com.zeldev.zel_e_comm.common;

import com.zeldev.zel_e_comm.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractLineItem extends BaseEntity {
    @Column(nullable = false)
    protected BigDecimal price;
    @Column(nullable = false)
    protected Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    protected ProductEntity product;

    @PrePersist
    private void validate() {
        if (quantity <= 0) {
            throw new IllegalStateException("Quantity must be positive");
        }
    }

}
