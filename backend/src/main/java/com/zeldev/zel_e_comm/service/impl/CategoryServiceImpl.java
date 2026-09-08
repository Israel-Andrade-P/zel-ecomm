package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.request.CategoryRequest;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.CategoryRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zeldev.zel_e_comm.util.CategoryUtils.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest category) {
        CategoryEntity savedCategory = categoryRepository.save(buildCategoryEntity(category));
        return toResponse(savedCategory);
    }

    @Override
    public PageResponse<CategoryResponse> getAll(Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(page, size, sortByAndOrder);
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageDetails);

        List<CategoryEntity> categories = categoryPage.getContent();
        if (categories.isEmpty()) throw new APIException("No categories have been added yet :(");
        return buildCategoryPageResponse(categoryPage, categories);
    }

    @Override
    public CategoryResponse deleteById(String name) {
        CategoryEntity entity = getByName(name);
        CategoryResponse categoryDto = toResponse(entity);
        categoryRepository.delete(entity);
        return categoryDto;
    }

    @Override
    public CategoryResponse updateById(CategoryRequest request, String name) {
        var categoryDB = getByName(name);
        if (!request.name().isBlank() && !request.name().equals(categoryDB.getName())) {
            categoryDB.setName(request.name());
        }

        return toResponse(categoryDB);
    }

    public CategoryEntity getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(name, "Category"));
    }
}
