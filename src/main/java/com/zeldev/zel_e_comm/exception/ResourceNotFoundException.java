package com.zeldev.zel_e_comm.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceName;
    private final Long resourceId;
    public ResourceNotFoundException(Long resourceId, String resourceName) {
        super(String.format("%s with ID %d not found", resourceName, resourceId));
        this.resourceId = resourceId;
        this.resourceName = resourceName;
    }

}
