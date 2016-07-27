package org.iqu.persistence.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/slavedb";
	private static final String USER = "root";
	private static final String PASSWORD = "1234";

	public static Connection createConnection() throws SQLException {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection myConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		return myConnection;
	}

	public static NewsDAO getNewsDAO() {
		return new NewsDAOImpl();
	}

	public static EventDAO getEventDAO() {
		return new EventDAOImpl();
	}

}
