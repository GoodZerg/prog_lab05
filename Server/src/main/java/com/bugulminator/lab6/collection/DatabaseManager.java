package com.bugulminator.lab6.collection;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import com.bugulminator.lab6.collection.data.Coordinates;
import com.bugulminator.lab6.collection.data.Location;
import com.bugulminator.lab6.collection.data.Route;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class DatabaseManager {
    private DatabaseManager() {}
    
    private static Connection connection = null;
    private static Session session = null;
    private static final int L_PORT = 1337;
    private static final int R_PORT = 5432;
    private static final String URL = "jdbc:postgresql://localhost:" + L_PORT + "/studs";
    private static final String HOST = "helios.se.ifmo.ru";
    private static final String DB_HOST = "pg";
    private static final int PORT = 2222;
    private static final String USER = "s367231";

    public static void connect() throws SQLException, JSchException {
        String heliosPass = System.getenv("HELIOS_PASS");
        String dbPass = System.getenv("DB_PASS");
        if (heliosPass == null || dbPass == null) {
            throw new SQLException("HELIOS_PASS or DB_PASS is not set");
        }
        JSch jsch = new JSch();
        session = jsch.getSession(USER, HOST, PORT);
        session.setPassword(heliosPass);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing SSH connection");
        session.connect();
        System.out.println("SSH connection established");
        int assignedPort = session.setPortForwardingL(L_PORT, DB_HOST, R_PORT);
        System.out.println("Port successfully forwarded (" + "localhost:" + assignedPort + " -> " + USER + "@" + HOST + ":" + R_PORT + ")");
        System.out.println("Establishing connection to database");
        connection = DriverManager.getConnection(URL, USER, dbPass);
        System.out.println("Connection to database established");
        try {
            prepareDB();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void prepareDB() throws SQLException {
        System.out.println("Preparing database for further work");
        var statement = connection.createStatement();
        // Create users table
        final String CREATE_USERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " +
                "Users (login varchar NOT NULL PRIMARY KEY, password varchar NOT NULL)";
        statement.executeUpdate(CREATE_USERS_TABLE_QUERY);
        // Create coordinates table
        final String CREATE_COORDINATES_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " +
                "Coordinates (id serial NOT NULL PRIMARY KEY, x bigint NOT NULL, " +
                "y real NOT NULL, CHECK(x < 211), CHECK(y < 899))";
        statement.executeUpdate(CREATE_COORDINATES_TABLE_QUERY);
        // Create locations table
        final String CREATE_LOCATION_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS Locations" +
                " (id serial NOT NULL PRIMARY KEY, x bigint NOT NULL, " +
                "y int NOT NULL, name varchar NOT NULL, CHECK(name != ''))";
        statement.executeUpdate(CREATE_LOCATION_TABLE_QUERY);
        // Create routes table
        final String CREATE_ROUTES_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS Routes" +
                "(id serial NOT NULL PRIMARY KEY, name varchar NOT NULL, coordinates serial NOT NULL, " +
                "creationDate timestamp NOT NULL, fromLocation serial NOT NULL, toLocation serial NOT NULL, " +
                "distance int NOT NULL, owner varchar, CONSTRAINT fk_coords FOREIGN KEY (coordinates) " +
                "REFERENCES Coordinates(id), CONSTRAINT fk_from FOREIGN KEY (fromLocation) REFERENCES Locations(id), " +
                "CONSTRAINT fk_to FOREIGN KEY (toLocation) REFERENCES Locations(id), CONSTRAINT fk_owner FOREIGN KEY " +
                "(owner) REFERENCES Users(login), CHECK (distance > 1))";
        statement.executeUpdate(CREATE_ROUTES_TABLE_QUERY);
        System.out.println("Database is ready");
    }

    public static Coordinates getCoordinates(int id) throws SQLException {
        var statement = connection.prepareStatement("SELECT * FROM Coordinates WHERE id = ?");
        statement.setInt(1, id);
        var res = statement.executeQuery();
        if (!res.next()) {
            throw new SQLException("Unable to get row with such id");
        }
        return new Coordinates(res.getInt(2), res.getFloat(3));
    }

    public static Location getLocation(int id) throws SQLException {
        var statement = connection.prepareStatement("SELECT * FROM Locations WHERE id = ?");
        statement.setInt(1, id);
        var res = statement.executeQuery();
        if (!res.next()) {
            throw new SQLException("Unable to get row with such id");
        }
        return new Location(res.getLong(2), res.getInt(3), res.getString(4));
    }

    public static void truncate() throws SQLException {
        var statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE TABLE Routes CASCADE");
    }

    public static void load(DeqCollection<Route> collection) throws SQLException {
        var counterStatement = connection.createStatement();
        var counter = counterStatement.executeQuery("SELECT COUNT(*) FROM Routes");
        counter.next();
        int maxAmount = counter.getInt(1);
        var statement = connection.prepareStatement("SELECT * FROM Routes");
        var res = statement.executeQuery();
        int amount = 0;
        System.out.println("Loading routes from database");
        while (res.next()) {
            try {
                var route = new Route();
                route.setId(res.getInt(1));
                route.setName(res.getString(2));
                route.setCoordinates(getCoordinates(res.getInt(3)));
                route.setCreationDate(res.getTimestamp(4).toLocalDateTime().toLocalDate());
                route.setFrom(getLocation(res.getInt(5)));
                route.setTo(getLocation(res.getInt(6)));
                route.setDistance(res.getInt(7));
                route.setOwner(res.getString(8));

                collection.getStorage().add(route);
                System.out.print("Loaded " + ++amount + "/" + maxAmount + " routes (" + amount * 100 / maxAmount + "%)\r");
            } catch (Throwable ex) {
                System.out.print("Row with incorrect data (" + ex.getMessage() + ")");
            }
        }
        System.out.println("Load complete");
    }

    public static void save(DeqCollection<Route> data) {
        System.out.println("Saving routes to database");
        final int[] counter = {0};
        try {
            DatabaseManager.truncate();
            var groups = data.getStorage();
            groups.forEach(route -> {
                try {
                    DatabaseManager.putRoute(route);
                } catch (SQLException ex) {
                    System.err.println("Error while saving route to database");
                    ex.printStackTrace();
                }
                System.out.print("Saved " + ++counter[0] + "/" + groups.size()
                        + " routes (" + counter[0] * 100 / groups.size() + "%)\r");
            });
        } catch (SQLException ex) {
            System.err.println("Database is inaccessible");
            return;
        }
        System.out.println("Collection is saved to database");
    }

    public static void removeRoute(int id) throws SQLException {
        var statement = connection.prepareStatement("DELETE FROM Routes WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
        System.out.print("XXX");
        // TODO remove fk values
    }

    public static void removeByOwner(String owner) throws SQLException {
        var statement = connection.prepareStatement("DELETE FROM Routes WHERE owner = ?");
        statement.setString(1, owner);
        statement.executeUpdate();
        // TODO remove fk values
    }

    public static void updateRoute(Route route, int id) throws SQLException {
        removeRoute(id);
        putRouteWithId(route, id);
    }

    public static int putCoordinates(Coordinates coordinates) throws SQLException {
        final var x = coordinates.getX();
        final var y = coordinates.getY();
        var statement = connection.prepareStatement("INSERT INTO Coordinates VALUES (DEFAULT, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, x);
        statement.setFloat(2, y);
        statement.executeUpdate();
        var generated = statement.getGeneratedKeys();
        generated.next();
        return generated.getInt(1);
    }

    public static int putLocation(Location location) throws SQLException {
        final var x = location.getX();
        final var y = location.getY();
        final var name = location.getName();
        var statement = connection.prepareStatement("INSERT INTO Locations VALUES (DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, x);
        statement.setFloat(2, y);
        statement.setString(3, name);
        statement.executeUpdate();
        var generated = statement.getGeneratedKeys();
        generated.next();
        return generated.getInt(1);
    }

    public static void putRoute(Route route) throws SQLException {
        final var name = route.getName();
        final var coordinates = route.getCoordinates();
        final var creationDate = route.getCreationDate();
        final var from = route.getFrom();
        final var to = route.getTo();
        final var distance = route.getDistance();
        final var owner = route.getOwner();
        var statement = connection.prepareStatement("INSERT INTO Routes VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setInt(2, putCoordinates(coordinates));
        statement.setTimestamp(3, Timestamp.valueOf(creationDate.atStartOfDay())); // Kinda sus
        statement.setInt(4, putLocation(from));
        statement.setInt(5, putLocation(to));
        statement.setInt(6, distance);
        statement.setString(7, owner);
        statement.executeUpdate();
        var generated = statement.getGeneratedKeys();
        generated.next();
        var id = generated.getInt(1);
        route.setId(id);
    }

    public static void putRouteWithId(Route route, int id) throws SQLException {
        final var name = route.getName();
        final var coordinates = route.getCoordinates();
        final var creationDate = route.getCreationDate();
        final var from = route.getFrom();
        final var to = route.getTo();
        final var distance = route.getDistance();
        final var owner = route.getOwner();
        var statement = connection.prepareStatement("INSERT INTO Routes VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setInt(3, putCoordinates(coordinates));
        statement.setTimestamp(4, Timestamp.valueOf(creationDate.atStartOfDay())); // Kinda sus
        statement.setInt(5, putLocation(from));
        statement.setInt(6, putLocation(to));
        statement.setInt(7, distance);
        statement.setString(8, owner);
        statement.executeUpdate();
    }

    public static boolean auth(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        try {
            final String GET_USER_QUERY = "SELECT * FROM Users WHERE login = ? AND password = ?";
            final String passHash = genHash(password);
            var statement = connection.prepareStatement(GET_USER_QUERY);
            statement.setString(1, login);
            statement.setString(2, passHash);
            var res = statement.executeQuery();
            return res.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean register(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        try {
            final String INSERT_NEW_USER_QUERY = "INSERT INTO Users VALUES(?, ?)";
            final String passHash = genHash(password);
            var statement = connection.prepareStatement(INSERT_NEW_USER_QUERY);
            statement.setString(1, login);
            statement.setString(2, passHash);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static String genHash(String password) {
        try {
            var md = MessageDigest.getInstance("md2");
            md.reset();
            md.update(password.getBytes());
            byte[] digest = md.digest();
            var hash = new BigInteger(1,digest);
            return hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        try {
            if (connection != null) {
                System.out.println("Closing connection to database");
                connection.close();
                System.out.println("Connection to database closed");
            }
            if (session != null) {
                System.out.println("Disconnecting from SSH");
                session.disconnect();
                System.out.println("Disconnected from SSH");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}