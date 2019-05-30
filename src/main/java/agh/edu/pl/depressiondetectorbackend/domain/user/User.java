package agh.edu.pl.depressiondetectorbackend.domain.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Set<UserPrivilege> userPrivileges = new CopyOnWriteArraySet<>();
}
