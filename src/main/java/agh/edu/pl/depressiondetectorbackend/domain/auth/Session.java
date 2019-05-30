package agh.edu.pl.depressiondetectorbackend.domain.auth;

import agh.edu.pl.depressiondetectorbackend.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    @NotNull
    private Date expDate;

    public SessionInfo toSessionInfo() {
        SessionInfo info = new SessionInfo();
        info.token = token;
        info.userID = user.getId();
        info.userPrivileges = user.getUserPrivileges();
        info.expDate = expDate;
        return info;
    }
}
