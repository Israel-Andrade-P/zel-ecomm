package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.CategoryRequest;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.mapper.CategoryMapper;
import com.zeldev.zel_e_comm.model.CategoryEntity;
import com.zeldev.zel_e_comm.repository.CategoryRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public void createCategory(CategoryRequest category) {
        categoryRepository.save(CategoryEntity.builder().name(category.name()).build());
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(mapper::toCategoryResponse).toList();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
