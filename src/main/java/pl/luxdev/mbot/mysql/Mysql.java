package pl.luxdev.mbot.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {

	private static Mysql inst;

	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;
	private Connection connection;

	public Mysql() {
		inst = this;
		this.hostname = "localhost";
		this.port = "3306";
		this.database = "mbot_";
		this.user = "root";
		this.password = "";
		this.connection = null;
		this.firstConnection();
	}

	public Connection openConnection() {
		try {
			if (checkConnection())
				return connection;
			Class.forName("com.mysql.jdbc.Driver");
			String s = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;
			connection = DriverManager.getConnection(s, this.user, this.password);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void firstConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + this.hostname + ":" + this.port + "/?user=" + this.user + "&password="
					+ this.password;
			Connection conn = DriverManager.getConnection(url);
			Statement s = conn.createStatement();
			s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.database);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkConnection() {
		try {
			return connection != null && !connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection != null;
	}

	public Connection getConnection() {
		if (!checkConnection())
			openConnection();
		return connection;
	}

	public boolean closeConnection() {
		if (connection == null)
			return false;
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public ResultSet executeQuery(String query) {
		try {
			if (!checkConnection())
				openConnection();
			if (connection == null)
				return null;
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int executeUpdate(String query) {
		try {
			if (!checkConnection())
				openConnection();
			if (connection == null) {
				openConnection();
				if (connection == null)
					return 0;
			}
			Statement statement = connection.createStatement();
			if (statement == null)
				return 0;
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			if (e.getSQLState().equals("42S21"))
				return 4221;
			e.printStackTrace();
		}
		return 0;
	}

	public static Mysql getInst() {
		if (inst == null)
			return new Mysql();
		return inst;
	}

}
