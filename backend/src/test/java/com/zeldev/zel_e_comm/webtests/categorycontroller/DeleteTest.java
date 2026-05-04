package com.zeldev.zel_e_comm.webtests.categorycontroller;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DeleteTest extends CategoryControllerBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: it hits /admin/categories/delete/{name}
                    THEN: returns a 200 with a CategoryDTO representing the deleted Category
                    """
    )
    void greenPath() {
        var categoryId = "categoryId";
        var deletedCategory = new CategoryDTO("Pet Food");

        when(categoryService.deleteById(categoryId))
                .thenReturn(deletedCategory);

        var result = mockMvc.delete()
                .uri(BASE_URI.concat("/admin/categories/delete/" + categoryId))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);
        assertThat(result).bodyJson().extractingPath("$.name").isEqualTo(deletedCategory.name());
    }
}
