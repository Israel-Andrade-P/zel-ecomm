package com.zeldev.zel_e_comm.dto.response;

import com.zeldev.zel_e_comm.dto.dto_class.CategoryDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponse(
        List<CategoryDTO> categories,
        Integer pageNumber,
        Integer pageSize,
        Long totalElements,
        Integer totalPages,
        boolean lastPage) {

}
