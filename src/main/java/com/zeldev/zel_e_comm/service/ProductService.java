package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO request, String category);
    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(String id);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, String productId);

    ProductDTO deleteProduct(String productId);

    ProductDTO updateImage(String productId, MultipartFile image) throws IOException;
}
