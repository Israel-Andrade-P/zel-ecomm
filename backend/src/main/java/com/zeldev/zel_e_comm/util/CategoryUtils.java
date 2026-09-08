package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.request.CategoryRequest;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public class CategoryUtils {
    public static CategoryEntity buildCategoryEntity(CategoryRequest category) {
        return CategoryEntity.builder()
                .name(category.name())
                .build();
    }

    public static CategoryResponse toResponse(CategoryEntity entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static PageResponse<CategoryResponse> buildCategoryPageResponse(Page<CategoryEntity> pageInfo, List<CategoryEntity> categories) {
        return PageResponse.<CategoryResponse>builder()
                .content(categories.stream().map(CategoryUtils::toResponse).toList())
                .pageNumber(pageInfo.getNumber())
                .pageSize(pageInfo.getSize())
                .totalElements(pageInfo.getTotalElements())
                .totalPages(pageInfo.getTotalPages())
                .lastPage(pageInfo.isLast())
                .build();
    }
}
