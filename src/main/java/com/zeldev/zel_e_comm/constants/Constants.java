package com.zeldev.zel_e_comm.constants;

public class Constants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "50";
    public static final String SORT_ENTITY_BY = "name";
    public static final String SORT_DIR = "asc";
    public static final String ZEL_DEV_INC = "ZelDev.Inc";
    public static final String ROLES = "Roles";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String LOGIN_PATH = "/api/v1/auth/login";
    public static final String PASSWORD_PROTECTED = "PASSWORD_PROTECTED";
    public static final String[] WHITE_LIST =
            {"/api/v1/auth/**", "/v3/api-docs/**","/swagger-ui/**",
                    "swagger-ui.html", "/api-docs/**", "/swagger-resources/**", "/aggregate/**", "/actuator/**"};
}
