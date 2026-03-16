package com.zeldev.zel_e_comm.unittests.categoryservice;

import com.zeldev.zel_e_comm.repository.CategoryRepository;
import com.zeldev.zel_e_comm.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceBaseTest {
    protected static final String CATEGORY_NAME = "Pet Food";

    @Mock protected CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    protected void assertPageable(
            Pageable pageable,
            int page,
            int size,
            String sort,
            Sort.Direction direction
    ) {
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());

        Sort.Order order = pageable.getSort().getOrderFor(sort);
        assertEquals(direction, order.getDirection());
    }
}
