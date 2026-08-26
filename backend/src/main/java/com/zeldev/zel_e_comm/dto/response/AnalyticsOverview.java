package com.zeldev.zel_e_comm.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AnalyticsOverview(
        long totalProducts,
        long totalOrders,
        BigDecimal totalRevenue,
        long totalCustomers
) {}
