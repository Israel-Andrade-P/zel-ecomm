package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationEntity extends BaseEntity {
    @Column(name = "street", nullable = false, length = 150)
    private String street;
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    @Column(name = "zip_code", nullable = false, length = 50)
    private String zipCode;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
