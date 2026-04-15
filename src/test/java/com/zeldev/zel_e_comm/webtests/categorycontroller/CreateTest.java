package com.zeldev.zel_e_comm.webtests.categorycontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CreateTest extends CategoryControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a CategoryDTO
                    WHEN: it hits /admin/categories/create
                    THEN: returns a 201 CategoryDTO
                    """
    )
    void greenPath() throws JsonProcessingException {
        CategoryDTO categoryDTO = new CategoryDTO("Pet Food");

        when(categoryService.createCategory(categoryDTO)).thenReturn(categoryDTO);

        var result = mockMvc.post()
                .uri(BASE_URI.concat("/admin/categories/create"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.name").isEqualTo(categoryDTO.name());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a CategoryDTO with invalid field
                    WHEN: it hits /admin/categories/create
                    THEN: returns a 400 
                    """
    )
    void redPath() throws JsonProcessingException {
        CategoryDTO categoryDTO = new CategoryDTO("P");

        var result = mockMvc.post()
                .uri(BASE_URI.concat("/admin/categories/create"))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
        assertThat(result).bodyJson().extractingPath("$.code").isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result).bodyJson().extractingPath("$.reason").isEqualTo("Invalid fields");
        assertThat(result).bodyJson().extractingPath("$.data.name").isEqualTo("Category name must be between 2 - 20 characters");
    }
}
