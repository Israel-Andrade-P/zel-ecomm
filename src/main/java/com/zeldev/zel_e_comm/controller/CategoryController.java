package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.request.CategoryDTO;
import com.zeldev.zel_e_comm.dto.response.CategoryResponse;
import com.zeldev.zel_e_comm.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping("/admin/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(request));
    }

    @GetMapping("/all")
    public ResponseEntity<CategoryResponse> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/admin/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @PutMapping("/admin/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody CategoryDTO request, @PathVariable("id") Long id) {
        service.updateById(request, id);
    }
}