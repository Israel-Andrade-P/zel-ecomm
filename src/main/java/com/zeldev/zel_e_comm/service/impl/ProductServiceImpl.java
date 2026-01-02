package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.model.CategoryEntity;
import com.zeldev.zel_e_comm.model.ProductEntity;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
        String path = "/images";
        String filename = uploadImage(path, image);
        productDB.setImage(filename);
        return mapper.map(productRepository.save(productDB), ProductDTO.class);
    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        //Generate a unique file name
        String newFileName = UUID.randomUUID().toString().concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + newFileName;
        //Check if path exists else create
        File folder = new File(path);
        if (!folder.exists()) folder.mkdir();
        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(newFileName));
        return newFileName;
    }

    private ProductEntity findByPublicId(String publicId) {
        return productRepository.findByPublicId(UUID.fromString(publicId)).orElseThrow(() -> new ResourceNotFoundException(publicId, "Product"));
    }
}
