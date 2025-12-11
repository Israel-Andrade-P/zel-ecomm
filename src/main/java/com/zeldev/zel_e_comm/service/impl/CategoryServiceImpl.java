package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.CategoryRequest;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
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
        categoryRepository.save(mapper.toCategoryEntity(category));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(mapper::toCategoryResponse).toList();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.delete(getById(id));
    }

    @Override
    public void updateById(CategoryRequest request, Long id) {
        var categoryDB = getById(id);
        if (!request.name().isBlank() && !request.name().equals(categoryDB.getName())) {
            categoryDB.setName(request.name());
        }
        categoryRepository.save(categoryDB);
    }

    private CategoryEntity getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Category"));
    }
}
