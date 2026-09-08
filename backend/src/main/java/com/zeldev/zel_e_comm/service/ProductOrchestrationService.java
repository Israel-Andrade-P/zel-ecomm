package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.ProductRequest;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductOrchestrationService {
    private final ProductService productService;
    private final CartItemService cartItemService;

    public ProductResponse updateProductAndSyncCarts(ProductRequest dto, String productId) {
        ProductResponse updated = productService.updateProduct(dto, productId);

        cartItemService.findActiveCartItemsByProductId(UUID.fromString(productId))
                .forEach(ci -> {
                    ci.setPrice(updated.specialPrice());
                    ci.setDiscount(updated.discount());
                });

        return updated;
    }

    public ProductResponse deleteCartItemsAfterProduct(String productId) {
        cartItemService.deleteByProductPublicId(UUID.fromString(productId));
        return productService.deleteProduct(productId);
    }
}
