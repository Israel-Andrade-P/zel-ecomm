package com.zeldev.zel_e_comm.dto.response;

import com.zeldev.zel_e_comm.dto.dto_class.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private List<ProductDTO> content;
}
