package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.response.AnalyticsOverview;
import com.zeldev.zel_e_comm.repository.OrderItemRepository;
import com.zeldev.zel_e_comm.repository.OrderRepository;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.AdminAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zeldev.zel_e_comm.enumeration.OrderStatus.PAID;
import static com.zeldev.zel_e_comm.enumeration.RoleType.USER;

@Service
@RequiredArgsConstructor
public class AdminAnalyticsServiceImpl implements AdminAnalyticsService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public AnalyticsOverview getOverview() {
        return AnalyticsOverview.builder()
                .totalProducts(productRepository.count())
                .totalOrders(orderRepository.count())
                .totalCustomers(userRepository.countByRole(USER))
                .totalRevenue(orderItemRepository.calculateRevenue(PAID))
                .build();
    }
}
