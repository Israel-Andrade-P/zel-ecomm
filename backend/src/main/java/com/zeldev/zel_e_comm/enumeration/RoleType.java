package com.zeldev.zel_e_comm.enumeration;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("Default user role"),
    SELLER("Seller permissions"),
    ADMIN("System administrator");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }
}
