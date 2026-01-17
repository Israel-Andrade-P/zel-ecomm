package com.zeldev.zel_e_comm.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    private String access;
    private String refresh;
}
