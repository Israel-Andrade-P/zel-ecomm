package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.dto_class.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category APIs", description = "APIs that manage categories")
public class CategoryController {
    private final CategoryService service;

    @PostMapping("/admin/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO request) {
        return ResponseEntity.status(CREATED).body(service.createCategory(request));
    }

    @GetMapping("/all")
    public ResponseEntity<CategoryResponse> getAll(@RequestParam(name = "page", defaultValue = PAGE_NUMBER, required = false) Integer page,
                                                   @RequestParam(name = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
                                                   @RequestParam(name = "sortBy", defaultValue = SORT_ENTITY_BY, required = false) String sortBy,
                                                   @RequestParam(name = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder) {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, sortOrder));
    }

    @DeleteMapping("/admin/delete/{name}")
    public ResponseEntity<CategoryDTO> delete(@PathVariable("name") String name) {
        return ResponseEntity.status(OK).body(service.deleteById(name));
    }

    @PutMapping("/admin/update/{name}")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO request, @PathVariable("name") String name) {
        return ResponseEntity.status(OK).body(service.updateById(request, name));
    }
}