package se.ifmo.lab08.server.persistance.repository;

import se.ifmo.lab08.server.Configuration;
import se.ifmo.lab08.common.entity.House;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HouseRepository implements Repository<House> {

    private static final String URL = Configuration.DB_URL;

    private static final String USER = Configuration.DB_USER;

    private static final String PASSWORD = Configuration.DB_PASSWORD;

    private static final String UPDATE_SQL = """
            UPDATE house SET name = ?, year = ?, number_of_flats = ? WHERE id = ?;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO house(name, year, number_of_flats) values (?, ?, ?)
            RETURNING id, name, year, number_of_flats;
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM house WHERE id = ?;
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM house WHERE id = ?;
            """;

    private static final String FIND_ALL = """
            SELECT * FROM house;
            """;

    @Override
    public House save(House house) throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (house.getId() == null) {
                var statement = connection.prepareStatement(SAVE_SQL);
                statement.setString(1, house.getName());
                statement.setLong(2, house.getYear());
                statement.setInt(3, house.getNumberOfFlatsOnFloor());
                var rs = statement.executeQuery();
                rs.next();
                return mapRowToEntity(rs);
            }
            var statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, house.getName());
            statement.setLong(2, house.getYear());
            statement.setInt(3, house.getNumberOfFlatsOnFloor());
            statement.setInt(4, house.getId());
            statement.executeUpdate();
            return house;
        }
    }

    @Override
    public Optional<House> findById(long id) throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(mapRowToEntity(resultSet));
        }
    }

    @Override
    public List<House> findAll() throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(FIND_ALL);
            var resultSet = statement.executeQuery();
            var list = new ArrayList<House>();
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

    private House mapRowToEntity(ResultSet resultSet) throws SQLException {
        return new House()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setYear(resultSet.getLong("year"))
                .setNumberOfFlatsOnFloor(resultSet.getInt("number_of_flats"));
    }
}
