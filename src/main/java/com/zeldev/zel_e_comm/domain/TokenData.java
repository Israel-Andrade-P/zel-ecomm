package com.zeldev.zel_e_comm.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenData {
    private boolean valid;
    private String subject;
    private String username;
    private Long userId;
    private Integer tokenVersion;
}
