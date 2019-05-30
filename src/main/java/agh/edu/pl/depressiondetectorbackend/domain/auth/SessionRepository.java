package agh.edu.pl.depressiondetectorbackend.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    void deleteByExpDateBefore(Date date);

    Optional<Session> findByToken(String token);
}
