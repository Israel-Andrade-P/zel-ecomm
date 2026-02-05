package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
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

    public ProductDTO updateProductAndSyncCarts(ProductDTO dto, String productId) {
        ProductDTO updated = productService.updateProduct(dto, productId);

        cartItemService.findActiveCartItemsByProductId(UUID.fromString(productId))
                .forEach(ci -> {
                    ci.setPrice(updated.specialPrice());
                    ci.setDiscount(updated.discount());
                });

        return updated;
    }

    public ProductDTO deleteCartItemsAfterProduct(String productId) {
        cartItemService.deleteByProductPublicId(UUID.fromString(productId));
        return productService.deleteProduct(productId);
    }
}
