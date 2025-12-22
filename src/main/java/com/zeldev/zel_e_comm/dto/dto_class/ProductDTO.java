package com.zeldev.zel_e_comm.dto.dto_class;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String publicId;
    private String name;
    private String description;
    private String image;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal specialPrice;
    private BigDecimal discount;
}
