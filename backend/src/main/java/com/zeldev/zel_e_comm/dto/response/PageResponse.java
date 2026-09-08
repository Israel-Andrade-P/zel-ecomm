package com.zeldev.zel_e_comm.dto.response;

import lombok.Builder;

import java.util.List;


@Builder
public record PageResponse<T>(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Long totalElements,
        Integer totalPages,
        boolean lastPage
){}
