package com.zeldev.zel_e_comm.unittests.categoryservice;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UpdateByIdTest extends CategoryServiceBaseTest{

    @Test
    @DisplayName(
            """
            GIVEN: dto request, string category name 
            WHEN: updateById is called
            THEN: update category 
            AND:  return updated dto 
                    """
    )
    void greenPath() {
        CategoryDTO categoryDTO = new CategoryDTO("Cuisine");
        CategoryEntity category = new CategoryEntity(CATEGORY_NAME);

        when(categoryRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(category));

        var response = categoryService.updateById(categoryDTO, CATEGORY_NAME);

        assertEquals(categoryDTO.name(), response.name());
    }

    @Test
    @DisplayName(
            """
            GIVEN: dto request name field empty, string category name 
            WHEN: updateById is called
            THEN: don't update category 
            AND:  return dto 
                    """
    )
    void greenPath2() {
        CategoryDTO categoryDTO = new CategoryDTO("");
        CategoryEntity category = new CategoryEntity(CATEGORY_NAME);

        when(categoryRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(category));

        var response = categoryService.updateById(categoryDTO, CATEGORY_NAME);

        assertNotEquals(categoryDTO.name(), response.name());
    }
}
