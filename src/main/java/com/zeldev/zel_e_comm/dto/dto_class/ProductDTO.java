package com.zeldev.zel_e_comm.dto.dto_class;

import com.zeldev.zel_e_comm.model.CategoryEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String publicId;
    @NotNull(message = "Name field is mandatory")
    @NotEmpty(message = "Name field is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 - 50 characters")
    private String name;
    private String description;
    private String image;
    private String category;
    @NotNull(message = "Quantity must be at least 1")
    @PositiveOrZero(message = "Quantity must be at least 1")
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal specialPrice;
    private BigDecimal discount;
}
