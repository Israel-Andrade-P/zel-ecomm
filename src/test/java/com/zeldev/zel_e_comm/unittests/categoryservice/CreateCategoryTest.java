package com.zeldev.zel_e_comm.unittests.categoryservice;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateCategoryTest extends CategoryServiceBaseTest{

    @Test
    @DisplayName(
            """
            GIVEN: CategoryDTO 
            WHEN: createCategory is called
            THEN: create a new category
            AND:  return dto 
                    """
    )
    void greenPath(){
        CategoryDTO categoryDTO = new CategoryDTO("Accessories");

        when(categoryRepository.save(any(CategoryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = categoryService.createCategory(categoryDTO);

        ArgumentCaptor<CategoryEntity> captor = ArgumentCaptor.forClass(CategoryEntity.class);

        verify(categoryRepository).save(captor.capture());

        CategoryEntity category = captor.getValue();

        assertEquals(categoryDTO.name(), category.getName());
        assertEquals(categoryDTO.name(), response.name());
    }
}
