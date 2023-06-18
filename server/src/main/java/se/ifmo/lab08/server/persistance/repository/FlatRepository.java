package se.ifmo.lab08.server.persistance.repository;

import se.ifmo.lab08.common.entity.*;
import se.ifmo.lab08.server.Configuration;
import se.ifmo.lab08.common.exception.NotFoundException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlatRepository implements Repository<Flat> {

    private static final String URL = Configuration.DB_URL;

    private static final String USER = Configuration.DB_USER;

    private static final String PASSWORD = Configuration.DB_PASSWORD;

    private static final String UPDATE_SQL = """
            UPDATE flat SET
            name = ?,
            x_coord = ?,
            y_coord = ?,
            area = ?,
            number_of_rooms = ?,
            furnish = ?,
            view = ?,
            transport = ?,
            house_id = ?,
            owner_id = ?
            WHERE id = ?;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO flat(
                name,
                x_coord,
                y_coord,
                area,
                number_of_rooms,
                furnish,
                view,
                transport,
                house_id,
                owner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING *;
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM flat WHERE id = ?;
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM flat WHERE id = ?;
            """;

    private static final String FIND_ALL = """
            SELECT * FROM flat;
            """;

    private static final String DELETE_BY_OWNER_ID = """
            DELETE FROM flat WHERE owner_id = ?;
            """;

    private final HouseRepository houseRepository;

    private final UserRepository userRepository;

    public FlatRepository(HouseRepository houseRepository, UserRepository userRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Flat save(Flat flat) throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false);
            if (findById(flat.getId()).isEmpty()) {
                var statement = connection.prepareStatement(SAVE_SQL);
                statement.setString(1, flat.getName());
                statement.setLong(2, flat.getCoordinates().getX());
                statement.setFloat(3, flat.getCoordinates().getY());
                statement.setFloat(4, flat.getArea());
                statement.setLong(5, flat.getNumberOfRooms());
                statement.setString(6, flat.getFurnish() == null ? null : flat.getFurnish().name());
                statement.setString(7, flat.getView().name());
                statement.setString(8, flat.getTransport() == null ? null : flat.getTransport().name());
                // saving nested objects
                var owner = userRepository.save(flat.getOwner());
                var house = houseRepository.save(flat.getHouse());

                statement.setInt(9, house.getId());
                statement.setInt(10, owner.getId());
                var rs = statement.executeQuery();
                rs.next();
                flat = mapRowToEntity(rs);
                connection.commit();
                return flat;
            }

            var statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, flat.getName());
            statement.setLong(2, flat.getCoordinates().getX());
            statement.setFloat(3, flat.getCoordinates().getY());
            statement.setFloat(4, flat.getArea());
            statement.setLong(5, flat.getNumberOfRooms());
            statement.setString(6, flat.getFurnish() == null ? null : flat.getFurnish().name());
            statement.setString(7, flat.getView().name());
            statement.setString(8, flat.getTransport() == null ? null : flat.getTransport().name());
            statement.setLong(11, flat.getId());
            // saving nested objects
            var owner = userRepository.save(flat.getOwner());
            var house = houseRepository.save(flat.getHouse());

            statement.setInt(9, house.getId());
            statement.setInt(10, owner.getId());
            statement.executeUpdate();

            connection.commit();

            return flat;
        }
    }

    @Override
    public List<Flat> findAll() throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(FIND_ALL);
            var resultSet = statement.executeQuery();
            var list = new ArrayList<Flat>();
            while (resultSet.next()) {
                list.add(mapRowToEntity(resultSet));
            }
            return list;
        }
    }

    public boolean deleteByOwnerId(long id) throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(DELETE_BY_OWNER_ID);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }


    @Override
    public Optional<Flat> findById(long id) throws SQLException {
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
    public boolean deleteById(long id) throws SQLException {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Flat mapRowToEntity(ResultSet resultSet) throws SQLException {
        var x = resultSet.getLong("x_coord");
        var y = resultSet.getFloat("y_coord");
        var coordinates = new Coordinates(x, y);

        var house = houseRepository.findById(resultSet.getLong("house_id")).orElseThrow(() -> new NotFoundException("House not found"));
        var owner = userRepository.findById(resultSet.getLong("owner_id")).orElseThrow(() -> new NotFoundException("User not found"));

        return new Flat().setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setCoordinates(coordinates)
                .setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class).toZonedDateTime())
                .setArea(resultSet.getLong("area"))
                .setNumberOfRooms(resultSet.getLong("number_of_rooms"))
                .setFurnish(Optional.ofNullable(resultSet.getString("furnish")).map(Furnish::valueOf).orElse(null))
                .setView(View.valueOf(resultSet.getString("view")))
                .setTransport(Optional.ofNullable(resultSet.getString("transport")).map(Transport::valueOf).orElse(null))
                .setHouse(house)
                .setOwner(owner);
    }
}
