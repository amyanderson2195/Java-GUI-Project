package db;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Sales table

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sales extends Table {
	
	private Products products;
	
	public Sales(Database database, Products products) {
		super(database);
		String[] columns = {"SaleID", "CustomerID", "ProductID", "Quantity", "Date"};
		setName("SALES");
		setColumns(columns);
		this.products = products;
	}
	
	// handles all table insertion actions
	public String insert(Object[] fields) {
		Connection connection = null;
		PreparedStatement pstat = null;
		
		int currentStock = products.getCurrentStock(Integer.parseInt((String)fields[1]));
		int newStock = currentStock - Integer.parseInt((String)fields[2]);
		// if you want to buy more than is in stock
		if(newStock < 0) {
			return "There is not enough of that product in stock.";
		}
		else if (products.setCurrentStock(Integer.parseInt((String)fields[1]), newStock) == 1){ // update products table and do the following if an entry is changed
			try{
				connection = getDatabase().connect();
				pstat = connection.prepareStatement("INSERT INTO SALES (CustomerID, ProductID, Quantity, Date) VALUES (?,?,?,?)");
				// replace question marks with values. cast them to their original types
				pstat.setInt(1, Integer.parseInt((String)fields[0]));
				pstat.setInt(2, Integer.parseInt((String)fields[1]));
				pstat.setInt(3, Integer.parseInt((String)fields[2]));
				pstat.setDate(4, Date.valueOf((String)fields[3]));
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
		else {
			return "Sale unsuccessful.";
		}
	}

	// update unused on sales table
	public String update(Object[] fields) {
		return null;
	}
}
