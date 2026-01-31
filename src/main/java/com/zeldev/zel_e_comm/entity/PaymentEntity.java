package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEntity extends BaseEntity {
}
