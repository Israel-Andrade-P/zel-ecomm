package com.zeldev.zel_e_comm.dto.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record LoginResponse (String email, Collection<? extends GrantedAuthority> roles) {}
