package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() throws ClassNotFoundException {
		String sql = "SELECT a.*,b.address,b.address2, b.address3  from personal a,addressInfo b where a.id =b.id order by id;";
		String outputStr = "";

		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:C:/pg/db/myfriend.sqlite3");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);

			ResultSet rs = statement.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					System.out.print(rs.getString(i));
					;
					System.out.print(i < rsmd.getColumnCount() ? "," : "");
					outputStr = outputStr + rs.getString(i) + ",";
				}
				System.out.print(System.getProperty("line.separator"));
				outputStr = outputStr + System.getProperty("line.separator");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println(e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return outputStr;
	}

	@RequestMapping("/next")
	public String index2() {
		return "Hello Spring Boot2!";
	}
}
