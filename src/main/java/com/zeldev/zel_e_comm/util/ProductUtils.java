package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProductUtils {
    public static ProductEntity buildProductEntity(ProductDTO request) {
        return ProductEntity.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .discount(request.discount())
                .build();
    }

    public static ProductDTO toDTO(ProductEntity entity) {
        return ProductDTO.builder()
                .publicId(entity.getPublicId().toString())
                .name(entity.getName())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .specialPrice(entity.getSpecialPrice())
                .image(entity.getImage())
                .build();
    }

    public static ProductResponse buildProductResponse(Page<ProductEntity> productPage, List<ProductEntity> products) {
        return ProductResponse.builder()
                .content(products.stream().map(ProductUtils::toDTO).toList())
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .lastPage(productPage.isLast())
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .build();
    }
}
