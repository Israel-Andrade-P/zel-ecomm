package com.zeldev.zel_e_comm.dto.dto_class;

import java.util.Set;

public record OrderRequest(
        Set<OrderItemRequest> orderItems,
        String locationPublicId
) {}
