package agh.edu.pl.depressiondetectorbackend.domain.auth;

import agh.edu.pl.depressiondetectorbackend.domain.user.UserPrivilege;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

public class SessionInfo {

    public String token;

    public Long userID;

    public @NotNull Set<UserPrivilege> userPrivileges;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    public Date expDate;
}