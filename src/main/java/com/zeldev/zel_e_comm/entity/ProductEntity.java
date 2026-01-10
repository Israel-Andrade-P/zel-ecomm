package com.zeldev.zel_e_comm.entity;


import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {
    @Column(name = "public_id")
    private UUID publicId;
    private String name;
    private String description;
    private String image;
    private Integer quantity;
    private BigDecimal price;
    private Integer discount;
    private BigDecimal specialPrice;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity user;

    public BigDecimal calculateSpecialPrice() {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100))
        );
    }

    @PrePersist
    private void initPublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
