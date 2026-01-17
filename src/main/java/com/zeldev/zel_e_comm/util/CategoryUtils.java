package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.dto.dto_class.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public class CategoryUtils {
    public static CategoryEntity buildCategoryEntity(CategoryDTO category) {
        return CategoryEntity.builder()
                .name(category.name())
                .build();
    }

    public static CategoryDTO toDTO(CategoryEntity entity) {
        return CategoryDTO.builder()
                .name(entity.getName())
                .build();
    }

    public static CategoryResponse categoryResponseBuilder(Page<CategoryEntity> pageInfo, List<CategoryEntity> categories) {
        return CategoryResponse.builder()
                .categories(categories.stream().map(CategoryUtils::toDTO).toList())
                .pageNumber(pageInfo.getNumber())
                .pageSize(pageInfo.getSize())
                .totalElements(pageInfo.getTotalElements())
                .totalPages(pageInfo.getTotalPages())
                .lastPage(pageInfo.isLast())
                .build();
    }
}
