package com.zeldev.zel_e_comm.entity;

import com.zeldev.zel_e_comm.common.BaseEntity;
import com.zeldev.zel_e_comm.enumeration.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEntity extends BaseEntity {
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToOne(mappedBy = "payment")
    private OrderEntity order;
    @Column(name = "pg_id", nullable = false)
    private String pgId;
    @Column(name = "pg_status", nullable = false)
    private String pgStatus;
    @Column(name = "pg_response_message", nullable = false)
    private String pgResponseMessage;
    @Column(name = "pg_name", nullable = false)
    private String pgName;

    public PaymentEntity(String pgId, String pgStatus, String pgResponseMessage, String pgName) {
        this.pgId = pgId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }
}
