package db;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Database Class

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	// database information
	private String url;
	private String user;
	private String password;
	
	public Database(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	// connect to the database
	public Connection connect() throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}
	
}
