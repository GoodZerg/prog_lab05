package com.bugulminator.lab6.collection;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class DatabaseManager {
    private DatabaseManager() {}
    
    private static Connection connection = null;
    private static Session session = null;
    private static final int L_PORT = 1337;
    private static final int R_PORT = 5432;
    private static int assignedPort = L_PORT;
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
        assignedPort = session.setPortForwardingL(L_PORT, DB_HOST, R_PORT);
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
                "distance int NOT NULL, owner varchar NOT NULL, CONSTRAINT fk_coords FOREIGN KEY (coordinates) " +
                "REFERENCES Coordinates(id), CONSTRAINT fk_from FOREIGN KEY (fromLocation) REFERENCES Locations(id), " +
                "CONSTRAINT fk_to FOREIGN KEY (toLocation) REFERENCES Locations(id), CONSTRAINT fk_owner FOREIGN KEY " +
                "(owner) REFERENCES Users(login), CHECK (distance > 1))";
        statement.executeUpdate(CREATE_ROUTES_TABLE_QUERY);
        System.out.println("Database is ready");
    }

    public static void truncate() throws SQLException {
        var statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE TABLE STUDY_GROUP CASCADE");
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