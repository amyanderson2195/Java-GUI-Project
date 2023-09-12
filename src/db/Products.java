package db;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Products table

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Products extends Table {
	
	public Products(Database database) {
		super(database);
		String[] columns = {"ProductID", "Description", "Quantity", "Price"};
		setName("PRODUCTS");
		setColumns(columns);
	}
	
	// handles all table insertion actions
	public String insert(Object[] fields) {
		Connection connection = null;
		PreparedStatement pstat = null;
		int i = 0;
		
		try{
			connection = getDatabase().connect();
			pstat = connection.prepareStatement("INSERT INTO PRODUCTS (Description, Quantity, Price) VALUES (?,?,?)");
			// replace question marks with values. cast them to their original types
			pstat.setString(1, (String)fields[0]);
			pstat.setInt(2, Integer.parseInt((String)fields[1]));
			pstat.setBigDecimal(3, BigDecimal.valueOf(Double.parseDouble((String)fields[2])));
			// insert new table entry
			i = pstat.executeUpdate();
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
			pstat = connection.prepareStatement("UPDATE PRODUCTS SET Description=?, Quantity=?, Price=? WHERE ProductID=?");
			
			// replace question marks with values. cast them to their original types
			pstat.setString(1, (String)fields[1]);
			pstat.setInt(2, Integer.parseInt((String)fields[2]));
			pstat.setBigDecimal(3, BigDecimal.valueOf(Double.parseDouble((String)fields[3])));
			pstat.setInt(4, Integer.parseInt((String)fields[0]));
			
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
	
	// return quantity of items in stock
	public int getCurrentStock(int productID) {
		Connection connection = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			connection = getDatabase().connect();
			pstat = connection.prepareStatement("SELECT Quantity FROM PRODUCTS WHERE ProductID = ?");
			pstat.setInt(1, productID);
			rs = pstat.executeQuery();
			
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstat.close();
				connection.close();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// update quantity in stock
	public int setCurrentStock(int productID, int newQuantity) {
		Connection connection = null;
		PreparedStatement pstat = null;
		
		try {
			connection = getDatabase().connect();
			pstat = connection.prepareStatement("UPDATE PRODUCTS SET Quantity=? WHERE ProductID=?");
			
			// replace question marks with values. cast them to their original types
			pstat.setInt(1, newQuantity);
			pstat.setInt(2, productID);
			
			int i = pstat.executeUpdate();
			return i;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally {
			try {
				pstat.close();
				connection.close();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
