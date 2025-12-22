package com.zeldev.zel_e_comm.model;


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
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal specialPrice;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @PrePersist
    private void initPublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
