package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.CategoryRequest;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest category);

    PageResponse<CategoryResponse> getAll(Integer page, Integer size, String sortBy, String sortOrder);

    CategoryEntity getByName(String name);

    CategoryResponse deleteById(String name);

    CategoryResponse updateById(CategoryRequest request, String name);
}
