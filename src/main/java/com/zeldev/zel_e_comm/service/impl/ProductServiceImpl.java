package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.model.CategoryEntity;
import com.zeldev.zel_e_comm.model.ProductEntity;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        entity.setSpecialPrice(entity.calculateSpecialPrice());
        entity.setImage("default.png");
        ProductEntity saved = productRepository.save(entity);
        return mapper.map(saved, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        return ProductResponse.builder().content(productRepository.findAll().stream().map(p -> mapper.map(p, ProductDTO.class)).toList()).build();
    }

    @Override
    public ProductResponse getProductsByCategory(String id) {
        return ProductResponse.builder()
                .content(productRepository.findByCategory_IdOrderByPriceAsc(categoryService.getByName(id).getId()).stream().map(p -> mapper.map(p, ProductDTO.class)).toList())
                .build();
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        return ProductResponse.builder()
                .content(productRepository.findByNameLikeIgnoreCase("%" + keyword + "%").stream().map(p -> mapper.map(p, ProductDTO.class)).toList())
                .build();
    }
}
