package com.zeldev.zel_e_comm.dto.response;

import java.util.Set;

public record LoginResponse (String email, Set<String> roles) {}
