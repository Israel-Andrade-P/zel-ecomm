package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.model.CategoryEntity;
import com.zeldev.zel_e_comm.model.ProductEntity;
import com.zeldev.zel_e_comm.repository.CategoryRepository;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper mapper;

    @Override
    public ProductDTO create(ProductDTO request, String categoryName) {
        ProductEntity entity = mapper.map(request, ProductEntity.class);
        CategoryEntity category = categoryService.getByName(categoryName);
        entity.setCategory(category);
        ProductEntity saved = productRepository.save(entity);
        ProductDTO returnDTO = mapper.map(saved, ProductDTO.class);
        returnDTO.setCategory(saved.getCategory().getName());
        return returnDTO;
    }
}
