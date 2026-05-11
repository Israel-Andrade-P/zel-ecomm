package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.dto.request.ProductDTO;
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

    public ProductEntity buildProductEntity(ProductDTO request) {
        return ProductEntity.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .discount(request.discount())
                .build();
    }

    public ProductDTO toDTO(ProductEntity entity) {
        return ProductDTO.builder()
                .publicId(entity.getPublicId().toString())
                .name(entity.getName())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .specialPrice(entity.getSpecialPrice())
                .image(constructImageUrl(entity.getImage()))
                .build();
    }

    public ProductResponse buildProductResponse(Page<ProductEntity> productPage, List<ProductEntity> products) {
        return ProductResponse.builder()
                .content(products.stream().map(this::toDTO).toList())
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
