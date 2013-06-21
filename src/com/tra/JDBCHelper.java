package com.tra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCHelper {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet c) {
		try {
			if (c != null)
				c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement c) {
		try {
			if (c != null)
				c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection c) {
		try {
			if (c != null)
				c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {

		try {
			Connection con = DriverManager.getConnection(Constants.URL,
					Constants.UID, Constants.PWD);
			return con;

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("NO CON");
			e.printStackTrace();
			return null;
		}

	}

}
