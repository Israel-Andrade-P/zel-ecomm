package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
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

import java.util.List;

import static com.zeldev.zel_e_comm.util.CategoryUtils.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        CategoryEntity savedCategory = categoryRepository.save(buildCategoryEntity(category));
        return toDTO(savedCategory);
    }

    @Override
    public CategoryResponse getAll(Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(page, size, sortByAndOrder);
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageDetails);

        List<CategoryEntity> categories = categoryPage.getContent();
        if (categories.isEmpty()) throw new APIException("No categories have been added yet :(");
        return categoryResponseBuilder(categoryPage, categories);
    }

    @Override
    public CategoryDTO deleteById(String name) {
        CategoryEntity entity = getByName(name);
        CategoryDTO categoryDto = toDTO(entity);
        categoryRepository.delete(entity);
        return categoryDto;
    }

    @Override
    public CategoryDTO updateById(CategoryDTO request, String name) {
        var categoryDB = getByName(name);
        if (!request.name().isBlank() && !request.name().equals(categoryDB.getName())) {
            categoryDB.setName(request.name());
        }
        CategoryEntity savedCategory = categoryRepository.save(categoryDB);
        return toDTO(savedCategory);
    }

    public CategoryEntity getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(name, "Category"));
    }
}
