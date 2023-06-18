package se.ifmo.lab08.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ifmo.lab08.common.dto.Credentials;
import se.ifmo.lab08.common.entity.User;
import se.ifmo.lab08.common.exception.AuthorizationException;
import se.ifmo.lab08.server.persistance.repository.UserRepository;
import se.ifmo.lab08.common.util.SecurityManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;

public class AuthManager {

    private static final Logger logger = LoggerFactory.getLogger(AuthManager.class);

    private final UserRepository userRepository = new UserRepository();

    public User register(String username, String password) {
        var salt = UUID.randomUUID().toString();
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                throw new AuthorizationException("User already exists");
            }
            var hash = SecurityManager.generateHash(password, salt);
            var user = new User(username, hash, salt);
            return userRepository.save(user);
        } catch (NoSuchAlgorithmException | SQLException e) {
            logger.error(e.toString());
            throw new AuthorizationException("Something went wrong while registration");
        }
    }

    public User authorize(String username, String password) {
        try {
            var user = userRepository.findByUsername(username).orElseThrow(() -> new AuthorizationException("Username not found"));
            if (!SecurityManager.checkPasswordHash(userRepository.findByUsername(username).orElseThrow(() -> new AuthorizationException("Username not found")).getPassword(), password, userRepository.findByUsername(username).orElseThrow(() -> new AuthorizationException("Username not found")).getSalt())) {
                throw new AuthorizationException("Invalid password");
            }
            return user;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.toString());
            throw new AuthorizationException("Something went wrong while authorization");
        }
    }

    public User authorize(Credentials credentials) {
        if (credentials == null) {
            throw new AuthorizationException();
        }
        return authorize(credentials.getUsername(), credentials.getPassword());
    }
}
