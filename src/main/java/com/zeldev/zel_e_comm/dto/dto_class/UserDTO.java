package com.zeldev.zel_e_comm.dto.dto_class;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull(message = "Username field is mandatory")
    @NotEmpty(message = "Username field is mandatory")
    @Size(min = 2, max = 50, message = "Username must be between 2 - 50 characters")
    private String username;
    @Email(message = "Invalid email")
    @NotNull(message = "Email field is mandatory")
    @NotEmpty(message = "Email field is mandatory")
    private String email;
    @NotNull(message = "Password field is mandatory")
    @NotEmpty(message = "Password field is mandatory")
    @Size(min = 6, max = 50, message = "Password must be between 6 - 50 characters")
    private String password;

    //Address
    @NotNull(message = "Street field is mandatory")
    @NotEmpty(message = "Street field is mandatory")
    private String street;
    @NotNull(message = "City field is mandatory")
    @NotEmpty(message = "City field is mandatory")
    private String city;
    @NotNull(message = "Country field is mandatory")
    @NotEmpty(message = "Country field is mandatory")
    private String country;
    @NotNull(message = "Zip code field is mandatory")
    @NotEmpty(message = "Zip code field is mandatory")
    private String zipCode;
}
