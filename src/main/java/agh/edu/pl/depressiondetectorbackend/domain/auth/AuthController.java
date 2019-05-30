package agh.edu.pl.depressiondetectorbackend.domain.auth;

import agh.edu.pl.depressiondetectorbackend.domain.user.User;
import agh.edu.pl.depressiondetectorbackend.domain.user.UserRepository;
import agh.edu.pl.depressiondetectorbackend.util.RandomService;
import agh.edu.pl.depressiondetectorbackend.util.TimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/authentication")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TimeService timeService;
    private final RandomService randomService;


    public AuthController(SessionRepository sessionRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TimeService timeService, RandomService randomService) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.timeService = timeService;
        this.randomService = randomService;
    }

    @PostMapping
    @Transactional
    public SessionInfo logIn(@Valid @RequestBody UserAuthenticationData userAuthenticationData) throws AuthException {
        User user = userRepository.findByUsername(userAuthenticationData.username).orElseThrow(AuthException::new);
        if (!bCryptPasswordEncoder.matches(userAuthenticationData.password, user.getPassword())) {
            throw new AuthException();
        }
        Session session = new Session();
        session.setExpDate(timeService.getOffsetDate(1, new Date()));
        session.setToken(session.getExpDate() + randomService.getRandomString(10));
        session.setUser(user);
        return sessionRepository.save(session).toSessionInfo();
    }

    @DeleteMapping
    @Transactional
    public void logOut(@RequestBody String token) {
        Optional<Session> session = sessionRepository.findByToken(token);
        session.ifPresent(sessionRepository::delete);
    }

    @Scheduled(fixedRate = 500000)
    @Transactional
    public void deleteOldSessions() {
        logger.info("Deleting old sessions");
        sessionRepository.deleteByExpDateBefore(new Date());
    }
}
