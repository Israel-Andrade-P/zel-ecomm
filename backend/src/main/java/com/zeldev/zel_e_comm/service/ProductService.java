package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.request.ProductRequest;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ProductService {
    ProductResponse create(ProductRequest request, String category);
    PageResponse<ProductResponse> getAllProducts(Integer page, Integer size, String sortBy, String sortOrder, String category, String keyword);

    PageResponse<ProductResponse> getProductsByCategory(String id, Integer page, Integer size, String sortBy, String sortOrder);

    PageResponse<ProductResponse> getProductsByKeyword(String keyword, Integer page, Integer size, String sortBy, String sortOrder);

    PageResponse<ProductResponse> getProductsBySeller(Integer page, Integer size, String sortBy, String sortOrder);

    ProductResponse updateProduct(ProductRequest productDTO, String productId);

    ProductResponse deleteProduct(String productId);

    void validateQuantity(Integer newQuantity, UUID productId);

    ProductResponse updateImage(String productId, MultipartFile image) throws IOException;

    ProductEntity findByPublicId(String publicId);

    void decreaseStock(UUID productId, Integer quantity);
}
