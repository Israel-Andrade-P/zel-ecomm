package com.zeldev.zel_e_comm.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static Pageable getPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equals("asc")
                ? Sort.by(Sort.Order.asc(sortBy))
                : Sort.by(Sort.Order.desc(sortBy));
        return PageRequest.of(page, size, sortByAndOrder);
    }
}
