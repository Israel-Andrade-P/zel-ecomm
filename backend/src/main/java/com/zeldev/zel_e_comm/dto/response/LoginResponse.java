package com.zeldev.zel_e_comm.dto.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public record LoginResponse (String email, String username, List<String> roles) {}
