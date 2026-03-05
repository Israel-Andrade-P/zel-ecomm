package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.FileService;
import com.zeldev.zel_e_comm.service.impl.ProductServiceImpl;
import com.zeldev.zel_e_comm.util.AuthUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceBaseTest {
    @Mock protected ProductRepository productRepository;
    @Mock protected CategoryService categoryService;
    @Mock protected UserRepository userRepository;
    @Mock protected FileService fileService;
    @Mock protected AuthUtils authUtils;

    @InjectMocks protected ProductServiceImpl productService;
}
