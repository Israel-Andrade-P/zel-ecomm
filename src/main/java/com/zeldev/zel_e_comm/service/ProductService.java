package com.zeldev.zel_e_comm.service;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;

public interface ProductService {
    ProductDTO create(ProductDTO request, String category);
}
