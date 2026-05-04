package com.zeldev.zel_e_comm.unittests.categoryservice;

import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class GetAllTest extends CategoryServiceBaseTest {

    @ParameterizedTest
    @ValueSource(strings = {"asc", "desc"})
    @DisplayName(
            """
                    GIVEN: some pagination related request params 
                    WHEN: getAll is called
                    THEN: return a CategoryResponse
                    """
    )
    void greenPath(String sortOrder) {
        Integer page = 0;
        Integer size = 5;
        String sortBy = "name";

        Page<CategoryEntity> categotyPage = new PageImpl<>(List.of(new CategoryEntity("Furniture"), new CategoryEntity("Computers")));

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categotyPage);

        var response = categoryService.getAll(page, size, sortBy, sortOrder);

        Pageable pageable = capturePageable();

        assertPageable(pageable, page, size, sortBy, sortOrder.equals("asc") ? ASC : DESC);

        assertEquals(2, response.categories().size());
        assertEquals(page, response.pageNumber());
        assertEquals(2, response.pageSize());
        assertTrue(response.lastPage());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: some pagination related request params 
                    WHEN: getAll is called
                    THEN: return a empty list
                    AND: throws exception
                    """
    )
    void throwsException() {
        int page = 0;
        int size = 10;

        Page<CategoryEntity> categoryPage = new PageImpl<>(List.of());

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);

        assertThrows(APIException.class, () -> categoryService.getAll(page, size, "name", "asc"));
    }

    private Pageable capturePageable() {
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(categoryRepository).findAll(captor.capture());
        return captor.getValue();
    }
}
