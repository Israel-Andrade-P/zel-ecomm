package com.zeldev.zel_e_comm.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private Integer code;
    private HttpStatus status;
    private String reason;
    private Map<String, String> data;

    public ErrorResponse(Integer code, HttpStatus status, String reason) {
        this.code = code;
        this.status = status;
        this.reason = reason;
    }

    public ErrorResponse(Map<String, String> data) {
        this.data = data;
    }
}
