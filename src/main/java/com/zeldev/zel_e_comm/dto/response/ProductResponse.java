package com.zeldev.zel_e_comm.dto.response;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import lombok.*;

import java.util.List;


@Builder
public record ProductResponse (
        List<ProductDTO> content,
        Integer pageNumber,
        Integer pageSize,
        Long totalElements,
        Integer totalPages,
        boolean lastPage
){}
