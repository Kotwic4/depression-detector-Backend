package agh.edu.pl.depressiondetectorbackend.domain.user;

import javax.validation.constraints.NotBlank;

public class UserRegistrationData {
    @NotBlank
    public String username;

    @NotBlank
    public String password;
}
