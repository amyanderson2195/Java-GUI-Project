package db;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	General table class

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gui.ResultsPane;

public abstract class Table {
	
	private String name;
	private String[] columns;
	private Database database;
	
	public Table(Database database) {
		setDatabase(database);
	}
	
	public Table(Database database, String[] columns) {
		setDatabase(database);
		setColumns(columns);
	}
	
	// setter methods
	public void setDatabase(Database database) {
		this.database = database;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	
	// getter methods
	public Database getDatabase() {
		return database;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getColumns() {
		return columns;
	}
	
	// handles all table insertion actions
	public abstract String insert(Object[] fields);
	
	// handles all table update actions
	public abstract String update(Object[] fields);
	
	// handles all table display actions
	public void display(ResultsPane resultspane) {
		Connection connection = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		
		try {
			connection = getDatabase().connect();
			pstat = connection.prepareStatement("SELECT * FROM " + name);
			rs = pstat.executeQuery();
			resultspane.updateTable(rs);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstat.close();
				connection.close();
			}
			catch(Exception exception) {
				exception.printStackTrace();
			}
		}	
	}
	
	// handles all table deletion actions
	private String delete(String table, String idName, int idNumber) {
		Connection connection = null;
		PreparedStatement pstat = null;
		
		try {
			connection = getDatabase().connect();
			pstat = connection.prepareStatement("DELETE FROM " + name + " WHERE " + idName + "=?");
			pstat.setInt(1, idNumber);
			int i = pstat.executeUpdate();
			return i + " record successfully removed from the table.";
		}
		catch(SQLException sqlException) {
			return sqlException.getMessage();
		}
		finally {
			try {
				pstat.close();
				connection.close();
			}
			catch(Exception exception) {
				return exception.getMessage();
			}
		}
	}
	
	// delete entry using only an id
	public String delete(int id) {
		return delete(getName(), getColumns()[0], id);
	}
}
