package com.zeldev.zel_e_comm.unittests.categoryservice;

import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteByIdTest extends CategoryServiceBaseTest{

    @Test
    @DisplayName(
            """
            GIVEN: string category name 
            WHEN: deleteById is called
            THEN: delete category from db
            AND:  return dto 
                    """
    )
    void greenPath() {
        CategoryEntity category = new CategoryEntity(CATEGORY_NAME);

        when(categoryRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(category));

        var response = categoryService.deleteById(CATEGORY_NAME);

        assertEquals(CATEGORY_NAME, response.name());
        verify(categoryRepository).delete(category);
    }

    @Test
    @DisplayName(
            """
            GIVEN: string category name 
            WHEN: deleteById is called
            THEN: no category found
            AND:  throws exception
                    """
    )
    void redPath() {
        when(categoryRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteById(CATEGORY_NAME));
    }
}
