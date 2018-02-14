package ua.kas.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static Database db;

	private Connection connect;

	private Database() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connect = DriverManager.getConnection("jdbc:sqlite::resource:ua/kas/main/res/cooking.db");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Database getDB() {
		if (db == null)
			db = new Database();

		return db;
	}

	public Connection getConnect() {
		return connect;
	}
}
