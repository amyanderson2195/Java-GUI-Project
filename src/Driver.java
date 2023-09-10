//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Driver Class

import db.*;
import gui.*;

public class Driver {

	public static void main(String[] args) {
		
		// instantiate database class
		Database database = new Database("jdbc:mysql://localhost/purchases", "root", "password");
		
		// instantiate table classes
		Customers customers = new Customers(database);
		Products products = new Products(database);
		Sales sales = new Sales(database, products);
		
		// create array of tables
		Table[] tables = {customers, products, sales};
		
		// display main window
		new MainWindow(tables);
	}
	
}
