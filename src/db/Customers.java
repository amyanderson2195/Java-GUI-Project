package db;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Customer table

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customers extends Table {
	
	public Customers(Database database) {
		super(database);
		String[] columns = {"CustomerID", "FirstName", "LastName", "PhoneNumber", "Email", "Address"};
		setName("CUSTOMERS");
		setColumns(columns);
	}
	
	// handles all table insertion actions
	public String insert(Object[] fields) {
		Connection connection = null;
		PreparedStatement pstat = null;
		
		try{
			connection = getDatabase().connect();
			pstat = connection.prepareStatement("INSERT INTO CUSTOMERS (FirstName, LastName, PhoneNumber, Email, Address) VALUES (?,?,?,?,?)");
			// replace question marks with values. cast them to their original types
			pstat.setString(1, (String)fields[0]);
			pstat.setString(2, (String)fields[1]);
			pstat.setString(3, (String)fields[2]);
			pstat.setString(4, (String)fields[3]);
			pstat.setString(5, (String)fields[4]);
			// insert new table entry
			int i = pstat.executeUpdate();
			return i + " record successfully added to the table.";
		}
		catch(SQLException e) {
			return e.getMessage();
		}
		finally {
			try {
				pstat.close();
				connection.close();
			}
			catch (Exception e){
				return e.getMessage();
			}
		}
	}

	public String update(Object[] fields) {
		Connection connection = null;
		PreparedStatement pstat = null;
		
		try {
			connection = getDatabase().connect();
			pstat = connection.prepareStatement("UPDATE CUSTOMERS SET FirstName=?, LastName=?, PhoneNumber=?, Email=?, Address=? WHERE CustomerID=?");
			
			pstat.setString(1, (String)fields[1]);
			pstat.setString(2, (String)fields[2]);
			pstat.setString(3, (String)fields[3]);
			pstat.setString(4, (String)fields[4]);
			pstat.setString(5, (String)fields[5]);
			pstat.setInt(6, Integer.parseInt((String)fields[0]));
			
			int i = pstat.executeUpdate();
			return i + " record successfully updated in the table.";
		}
		catch(SQLException sqlException) {
			return sqlException.getMessage();
		}
		finally {
			try {
				pstat.close();
				connection.close();
			}
			catch (Exception exception){
				return exception.getMessage();
			}
		}
	}
}
