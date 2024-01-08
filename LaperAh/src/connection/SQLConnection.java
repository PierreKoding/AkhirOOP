package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class SQLConnection {
	static Scanner sc = new Scanner(System.in);
	static Connection conn;
	static String url = "jdbc:mysql://localhost:3306/";
	static String dbName = "laperah(2.0)";
	static String userName = "root";
	static String pass = "";
	
	public static Connection createConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName,userName,pass);
		} catch (Exception e) {
			System.out.println("gagal");
		    e.printStackTrace();
		}return conn;
	}
	
	public static void closeConnection() {
		
		try {
		conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	
}