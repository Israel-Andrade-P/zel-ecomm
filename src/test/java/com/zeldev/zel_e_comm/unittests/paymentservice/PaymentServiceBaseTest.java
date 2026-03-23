package com.zeldev.zel_e_comm.unittests.paymentservice;

import com.zeldev.zel_e_comm.repository.PaymentRepository;
import com.zeldev.zel_e_comm.service.OrderService;
import com.zeldev.zel_e_comm.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceBaseTest {
    @Mock protected PaymentRepository paymentRepository;
    @Mock protected OrderService orderService;

    @InjectMocks protected PaymentServiceImpl paymentService;
}
