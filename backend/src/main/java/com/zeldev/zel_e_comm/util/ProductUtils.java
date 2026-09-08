package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.dto.request.ProductRequest;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductUtils {
    private final AppConfig appConfig;

    public ProductEntity buildProductEntity(ProductRequest request) {
        return ProductEntity.builder()
                .name(request.productName())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .discount(request.discount())
                .build();
    }

    public ProductResponse toProductResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .productId(entity.getPublicId().toString())
                .productName(entity.getName())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .specialPrice(entity.getSpecialPrice())
                .image(constructImageUrl(entity.getImage()))
                .build();
    }

    public PageResponse<ProductResponse> buildProductPageResponse(Page<ProductEntity> productPage, List<ProductEntity> products) {
        return PageResponse.<ProductResponse>builder()
                .content(products.stream().map(this::toProductResponse).toList())
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .lastPage(productPage.isLast())
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .build();
    }

    private String constructImageUrl(String imageName) {
        String imageBaseUrl = appConfig.getUrl();
        return imageBaseUrl.endsWith("/") ? imageBaseUrl + imageName : imageBaseUrl + "/" + imageName;
    }
}
