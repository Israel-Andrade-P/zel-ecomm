package com.zeldev.zel_e_comm.entity;


import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    //everytime you have a ManyToOne or ManyToMany change the fetch type to LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    public BigDecimal calculateSpecialPrice() {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100))
        ).setScale(2, RoundingMode.HALF_UP);
    }

    @PrePersist
    private void initPublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
