package com.zeldev.zel_e_comm.webtests.categorycontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UpdateTest extends CategoryControllerBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a CategoryDTO and a categoryName
                    WHEN: it hits /admin/categories/update/{name}
                    THEN: returns a 201 with an updated CategoryDTO
                    """
    )
    void greenPath() throws JsonProcessingException {
        String categoryName = "Gaming Stuff";
        CategoryDTO categoryDTO = new CategoryDTO("Furniture");

        when(categoryService.updateById(categoryDTO, categoryName)).thenReturn(categoryDTO);

        var result = mockMvc.put()
                .uri(BASE_URI.concat("/admin/categories/update/" + categoryName))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
        assertThat(result).bodyJson().extractingPath("$.name").isEqualTo(categoryDTO.name());
    }

}
