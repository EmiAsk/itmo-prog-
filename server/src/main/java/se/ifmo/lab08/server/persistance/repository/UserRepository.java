package se.ifmo.lab08.server.persistance.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.entity.User;
import se.ifmo.lab08.common.exception.NotFoundException;
import se.ifmo.lab08.server.Configuration;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private static final String URL = Configuration.DB_URL;

    private static final String USER = Configuration.DB_USER;

    private static final String PASSWORD = Configuration.DB_PASSWORD;

    private static final String UPDATE_SQL = """
            UPDATE "user" SET username = ?, password = ?, role = ? WHERE id = ?;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO "user"(username, password, salt, role) values (?, ?, ?, ?);
            """;

    private static final String FIND_BY_USERNAME = """
            SELECT * FROM "user" WHERE username = ?;
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM "user" WHERE id = ?;
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM "user" WHERE id = ?;
            """;

    private static final String FIND_ALL = """
            SELECT * FROM "user";
            """;

    @Override
    public User save(User user) throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (user.getId() == null) {
                var statement = connection.prepareStatement(SAVE_SQL);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getSalt());
                statement.setString(4, user.getRole().name());
                statement.executeUpdate();

                return findByUsername(user.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
            }
            var statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
            return user;
        }
    }

    @Override
    public Optional<User> findById(long id) {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(mapRowToEntity(resultSet));
        } catch (SQLException e) {
            logger.error(e.toString());
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(FIND_ALL);
            var resultSet = statement.executeQuery();
            var list = new ArrayList<User>();
            while (resultSet.next()) {
                list.add(mapRowToEntity(resultSet));
            }
            return list;
        }
    }

    @Override
    public boolean deleteById(long id) throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public Optional<User> findByUsername(String username) {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(FIND_BY_USERNAME);
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(mapRowToEntity(resultSet));
        } catch (SQLException e) {
            logger.error(e.toString());
            return Optional.empty();
        }
    }

    private User mapRowToEntity(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("salt"),
                Role.valueOf(resultSet.getString("role"))
        );
    }
}
