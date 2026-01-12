package com.zeldev.zel_e_comm.dto.response;

import java.util.List;

public record LoginResponse (String email, List<String> roles) {}
