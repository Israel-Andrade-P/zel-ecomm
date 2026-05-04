package com.zeldev.zel_e_comm.webtests.categorycontroller;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetAllTest extends CategoryControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits categories/all
                    THEN: returns a 200 with a CategoryResponse
                    """
    )
    void greenPath() {
        var categories = List.of(
                new CategoryDTO("Pet Food"),
                new CategoryDTO("Electronics")
        );

        var response = CategoryResponse.builder()
                .categories(categories)
                .pageNumber(0)
                .pageSize(50)
                .totalElements(2L)
                .totalPages(1)
                .lastPage(true)
                .build();

        when(categoryService.getAll(0, 50, "name", "asc"))
                .thenReturn(response);

        var result = mockMvc.get()
                .uri(BASE_URI.concat("/categories/all"))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
        assertThat(result).bodyJson().extractingPath("$.pageNumber").isEqualTo(0);
        assertThat(result).bodyJson().extractingPath("$.totalElements").isEqualTo(2);
        assertThat(result).bodyJson().extractingPath("$.categories[*].name").isEqualTo(List.of("Pet Food", "Electronics"));
    }
}
