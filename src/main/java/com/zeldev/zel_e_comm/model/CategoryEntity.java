package com.zeldev.zel_e_comm.model;

import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryEntity extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
