package com.zeldev.zel_e_comm.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static Pageable getPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(Sort.Order.asc(sortBy).ignoreCase())
                : Sort.by(Sort.Order.desc(sortBy).ignoreCase());
        return PageRequest.of(page, size, sortByAndOrder);
    }
}
