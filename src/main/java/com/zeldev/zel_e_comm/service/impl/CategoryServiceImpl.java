package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.repository.CategoryRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public CategoryResponse getAll(Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(page, size, sortByAndOrder);
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageDetails);

        List<CategoryEntity> categories = categoryPage.getContent();
        if (categories.isEmpty()) throw new APIException("No categories have been added yet :(");
        return CategoryResponse.builder()
                .categories(categories.stream().map(c -> modelMapper.map(c, CategoryDTO.class)).toList())
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .lastPage(categoryPage.isLast())
                .build();
    }

    @Override
    public CategoryDTO deleteById(String name) {
        CategoryEntity entity = getByName(name);
        CategoryDTO categoryDto = modelMapper.map(entity, CategoryDTO.class);
        categoryRepository.delete(entity);
        return categoryDto;
    }

    @Override
    public CategoryDTO updateById(CategoryDTO request, String name) {
        var categoryDB = getByName(name);
        if (!request.getName().isBlank() && !request.getName().equals(categoryDB.getName())) {
            categoryDB.setName(request.getName());
        }
        CategoryEntity savedCategory = categoryRepository.save(categoryDB);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    public CategoryEntity getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(name, "Category"));
    }
}
