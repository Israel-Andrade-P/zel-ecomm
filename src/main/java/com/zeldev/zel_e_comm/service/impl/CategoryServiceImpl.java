package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.model.CategoryEntity;
import com.zeldev.zel_e_comm.repository.CategoryRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        CategoryEntity savedCategory = categoryRepository.save(modelMapper.map(category, CategoryEntity.class));
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryResponse getAll() {
        var categories = categoryRepository.findAll();
        if (categories.isEmpty()) throw new APIException("No categories have been added yet :(");
        return CategoryResponse.builder().categories(categories.stream().map(c -> modelMapper.map(c, CategoryDTO.class)).toList()).build();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.delete(getById(id));
    }

    @Override
    public void updateById(CategoryDTO request, Long id) {
        var categoryDB = getById(id);
        if (!request.getName().isBlank() && !request.getName().equals(categoryDB.getName())) {
            categoryDB.setName(request.getName());
        }
        categoryRepository.save(categoryDB);
    }

    private CategoryEntity getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Category"));
    }
}
