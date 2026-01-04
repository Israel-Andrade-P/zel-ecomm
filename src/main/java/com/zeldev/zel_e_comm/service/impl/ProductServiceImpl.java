package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.model.CategoryEntity;
import com.zeldev.zel_e_comm.model.ProductEntity;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.FileService;
import com.zeldev.zel_e_comm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final ModelMapper mapper;
    @Value("${project.path.images}")
    private String path;

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
        List<ProductEntity> products = productRepository.findAll();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return ProductResponse.builder().content(products.stream().map(p -> mapper.map(p, ProductDTO.class)).toList()).build();
    }

    @Override
    public ProductResponse getProductsByCategory(String id) {
        List<ProductEntity> products = productRepository.findByCategory_IdOrderByPriceAsc(categoryService.getByName(id).getId());
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return ProductResponse.builder()
                .content(products.stream().map(p -> mapper.map(p, ProductDTO.class)).toList())
                .build();
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<ProductEntity> products = productRepository.findByNameLikeIgnoreCase("%" + keyword + "%");
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return ProductResponse.builder()
                .content(products.stream().map(p -> mapper.map(p, ProductDTO.class)).toList())
                .build();
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, String productId) {
        ProductEntity productDB = findByPublicId(productId);
        if (!productDTO.getName().isBlank()) {
            productDB.setName(productDTO.getName());
        }
        if (!productDTO.getDescription().isBlank()) {
            productDB.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null) {
            productDB.setPrice(productDTO.getPrice());
            productDB.setSpecialPrice(productDB.calculateSpecialPrice());
        }
        if (productDTO.getDiscount() != null) {
            productDB.setDiscount(productDTO.getDiscount());
            productDB.setSpecialPrice(productDB.calculateSpecialPrice());
        }
        if (productDTO.getQuantity() != null) {
            productDB.setQuantity(productDTO.getQuantity());
        }


        productRepository.save(productDB);
        return mapper.map(productDB, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(String productId) {
        ProductEntity productDB = findByPublicId(productId);
        productRepository.delete(productDB);
        return mapper.map(productDB, ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(String productId, MultipartFile image) throws IOException {
        ProductEntity productDB = findByPublicId(productId);
        String filename = fileService.uploadImage(path, image);
        productDB.setImage(filename);
        return mapper.map(productRepository.save(productDB), ProductDTO.class);
    }

    private ProductEntity findByPublicId(String publicId) {
        return productRepository.findByPublicId(UUID.fromString(publicId)).orElseThrow(() -> new ResourceNotFoundException(publicId, "Product"));
    }
}
