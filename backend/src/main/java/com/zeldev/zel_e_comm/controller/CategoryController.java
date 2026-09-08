package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.request.CategoryRequest;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zeldev.zel_e_comm.constants.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Category APIs", description = "APIs that manage categories")
public class CategoryController {
    private final CategoryService service;

    @PostMapping("/admin/categories/create")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.status(CREATED).body(service.createCategory(request));
    }

    @GetMapping("/categories")
    public ResponseEntity<PageResponse<CategoryResponse>> getAll(
                                               @RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
                                               @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
                                               @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
                                               @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder) {
        return ResponseEntity.status(OK).body(service.getAll(page, size, sortBy, sortOrder));
    }

    @DeleteMapping("/admin/categories/delete/{name}")
    public ResponseEntity<CategoryResponse> delete(@PathVariable("name") String name) {
        return ResponseEntity.status(OK).body(service.deleteById(name));
    }

    @PutMapping("/admin/categories/update/{name}")
    public ResponseEntity<CategoryResponse> update(@RequestBody CategoryRequest request, @PathVariable("name") String name) {
        return ResponseEntity.status(OK).body(service.updateById(request, name));
    }
}