package com.zeldev.zel_e_comm.dto.response;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponse(List<CategoryDTO> categories) {

}
