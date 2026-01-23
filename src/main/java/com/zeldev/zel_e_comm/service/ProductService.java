package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO create(ProductDTO request, String category, Authentication loggedUser);
    ProductResponse getAllProducts(Integer page, Integer size, String sortBy, String sortOrder);

    ProductResponse getProductsByCategory(String id, Integer page, Integer size, String sortBy, String sortOrder);

    ProductResponse getProductsByKeyword(String keyword, Integer page, Integer size, String sortBy, String sortOrder);

    ProductDTO updateProduct(ProductDTO productDTO, String productId);

    ProductDTO deleteProduct(String productId);

    ProductDTO updateImage(String productId, MultipartFile image) throws IOException;

    ProductEntity findByPublicId(String publicId);
}
