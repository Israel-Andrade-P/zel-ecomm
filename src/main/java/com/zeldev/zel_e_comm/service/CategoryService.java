package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.CategoryRequest;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryRequest category);

    List<CategoryResponse> getAll();

    void deleteById(Long id);
}
