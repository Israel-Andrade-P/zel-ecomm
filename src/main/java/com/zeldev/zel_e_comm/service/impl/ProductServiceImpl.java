package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.FileService;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.util.ProductUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.zeldev.zel_e_comm.util.ProductUtils.*;
import static com.zeldev.zel_e_comm.util.ProductUtils.buildProductEntity;
import static com.zeldev.zel_e_comm.util.ProductUtils.toDTO;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final FileService fileService;
    @Value("${project.path.images}")
    private String path;

    @Override
    public ProductDTO create(ProductDTO request, String categoryName) {
        ProductEntity entity = buildProductEntity(request);
        CategoryEntity category = categoryService.getByName(categoryName);
        entity.setCategory(category);
        entity.setSpecialPrice(entity.calculateSpecialPrice());
        entity.setImage("default.png");
        ProductEntity saved = productRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public ProductResponse getAllProducts(Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(Sort.Order.asc(sortBy).ignoreCase())
                : Sort.by(Sort.Order.desc(sortBy).ignoreCase());
        Pageable pageDetails = PageRequest.of(page, size, sortByAndOrder);
        Page<ProductEntity> productPage = productRepository.findAll(pageDetails);

        List<ProductEntity> products = productPage.getContent();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return buildProductResponse(productPage, products);
    }

    @Override
    public ProductResponse getProductsByCategory(String id, Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(Sort.Order.asc(sortBy).ignoreCase())
                : Sort.by(Sort.Order.desc(sortBy).ignoreCase());
        Pageable pageDetails = PageRequest.of(page, size, sortByAndOrder);
        Page<ProductEntity> productPage = productRepository.findByCategory_IdOrderByPriceAsc(categoryService.getByName(id).getId(), pageDetails);
        List<ProductEntity> products = productPage.getContent();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return buildProductResponse(productPage, products);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(Sort.Order.asc(sortBy).ignoreCase())
                : Sort.by(Sort.Order.desc(sortBy).ignoreCase());
        Pageable pageDetails = PageRequest.of(page, size, sortByAndOrder);
        Page<ProductEntity> productPage = productRepository.findByNameLikeIgnoreCase("%" + keyword + "%", pageDetails);
        List<ProductEntity> products = productPage.getContent();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return buildProductResponse(productPage, products);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, String productId) {
        ProductEntity productDB = findByPublicId(productId);
        if (!productDTO.name().isBlank()) {
            productDB.setName(productDTO.name());
        }
        if (!productDTO.description().isBlank()) {
            productDB.setDescription(productDTO.description());
        }
        if (productDTO.price() != null) {
            productDB.setPrice(productDTO.price());
            productDB.setSpecialPrice(productDB.calculateSpecialPrice());
        }
        if (productDTO.discount() != null) {
            productDB.setDiscount(productDTO.discount());
            productDB.setSpecialPrice(productDB.calculateSpecialPrice());
        }
        if (productDTO.quantity() != null) {
            productDB.setQuantity(productDTO.quantity());
        }


        productRepository.save(productDB);
        return toDTO(productDB);
    }

    @Override
    public ProductDTO deleteProduct(String productId) {
        ProductEntity productDB = findByPublicId(productId);
        productRepository.delete(productDB);
        return toDTO(productDB);
    }

    @Override
    public ProductDTO updateImage(String productId, MultipartFile image) throws IOException {
        ProductEntity productDB = findByPublicId(productId);
        String filename = fileService.uploadImage(path, image);
        productDB.setImage(filename);
        return toDTO(productRepository.save(productDB));
    }

    private ProductEntity findByPublicId(String publicId) {
        return productRepository.findByPublicId(UUID.fromString(publicId)).orElseThrow(() -> new ResourceNotFoundException(publicId, "Product"));
    }
}
