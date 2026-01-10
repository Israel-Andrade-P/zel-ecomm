package com.zeldev.zel_e_comm.enumeration;

import lombok.Getter;

@Getter
public enum RoleType {
    ROLE_USER("Regular user"),
    ROLE_SELLER("Seller"),
    ROLE_ADMIN("Has admin privileges");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }
}
