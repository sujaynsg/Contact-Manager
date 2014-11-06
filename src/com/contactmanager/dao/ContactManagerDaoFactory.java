package com.contactmanager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ContactManagerDaoFactory {

	public static Connection createconnection() {

		final String DRIVER = "com.mysql.jdbc.Driver";
		String newDB = "contactmanager";
		//final String DBURL = "jdbc:mysql://localhost/contactmanager";
	      final String DBURL ="jdbc:mysql://" + "127.0.0.1" + ":" + "3306" + "/" + newDB + "";
		final String DBUSER = "root";
		final String DBPASS = "";

		Connection connection = null;

		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			System.out.println("Database connection is established");
		} catch (ClassNotFoundException ce) {
			System.out.println("CLASS exception occured");
		} catch (SQLException e) {
			System.out.println("SQL Sexception occured");
		}
		return connection;

	}

}
