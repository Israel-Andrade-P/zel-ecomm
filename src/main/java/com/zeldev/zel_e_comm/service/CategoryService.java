package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);

    CategoryResponse getAll(Integer page, Integer size, String sortBy, String sortOrder);

    CategoryEntity getByName(String name);

    CategoryDTO deleteById(String name);

    CategoryDTO updateById(CategoryDTO request, String name);
}
