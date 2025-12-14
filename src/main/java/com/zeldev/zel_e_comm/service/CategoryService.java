package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);

    CategoryResponse getAll(Integer page, Integer size, String sortBy, String sortOrder);

    CategoryDTO deleteById(Long id);

    CategoryDTO updateById(CategoryDTO request, Long id);
}
