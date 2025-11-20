package com.zeldev.zel_e_comm.mapper;

import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.model.CategoryEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public CategoryResponse toCategoryResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder().name(categoryEntity.getName()).build();
    }
}
