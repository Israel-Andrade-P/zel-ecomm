package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);

    CategoryResponse getAll();

    void deleteById(Long id);

    void updateById(CategoryDTO request, Long id);
}
