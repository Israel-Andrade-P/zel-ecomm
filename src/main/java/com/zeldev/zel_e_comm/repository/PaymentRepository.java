package com.zeldev.zel_e_comm.repository;

import com.zeldev.zel_e_comm.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
