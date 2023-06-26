package it.jdbc.dbhandler;

import java.io.*;
import java.sql.*;
import java.util.*;

public class bibliotecaHandler {

	private static bibliotecaHandler instance;
	private String dbConnection;
	private String dbName;
	private String dbUser;
	private String dbPassword;

	private bibliotecaHandler() {
		Properties prop = new Properties();
		try (InputStream inputStream = bibliotecaHandler.class.getClassLoader().getResourceAsStream("biblioteca.properties")) {
			
			prop.load(inputStream);
			dbConnection = prop.getProperty("url");
			dbName = prop.getProperty("name");
			dbUser = prop.getProperty("user");
			dbPassword = prop.getProperty("password");
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    public static synchronized bibliotecaHandler getInstance() {
        if (instance == null) {
            instance = new bibliotecaHandler();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbConnection + dbName, dbUser, dbPassword);
    }

    public void closeConnection() throws SQLException {
        if ( getConnection() != null) {
            try {
                getConnection().close();
                System.out.println("Connessione chiusa.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

