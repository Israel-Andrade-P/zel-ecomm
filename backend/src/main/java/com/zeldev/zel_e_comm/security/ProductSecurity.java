package com.zeldev.zel_e_comm.security;

import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductSecurity {
    private final ProductRepository productRepository;
    private final AuthUtils authUtils;

    public boolean isOwner(String productId) {
        if (productId == null) return false;
        return productRepository.existsByIdAndSellerEmail(UUID.fromString(productId), authUtils.getLoggedInEmail());
    }
}
