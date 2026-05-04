package com.zeldev.zel_e_comm.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceName;
    private final String resourceId;
    public ResourceNotFoundException(String resourceId, String resourceName) {
        super(String.format("%s with ID %s not found", resourceName, resourceId));
        this.resourceId = resourceId;
        this.resourceName = resourceName;
    }

}
