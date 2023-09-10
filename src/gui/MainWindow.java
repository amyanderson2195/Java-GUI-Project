package gui;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Main Window Class. Used to carry out most actions.

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import db.*;

public class MainWindow extends JFrame{
	
	// tables
	private Table[] tables;
	private Table selectedtable;
	
	// gridbag constraints
	private GridBagConstraints c = new GridBagConstraints();
	
	// sidebar panel and buttons
	private JPanel sidebar = new JPanel(new GridLayout(0,1));
	// table information
	private JButton customers = new JButton("Customers");
	private JButton products = new JButton("Products");
	private JButton sales = new JButton("Sales");
	
	// topbar panel and buttons
	private JPanel topbar = new JPanel(new GridLayout(1,0));;
	private JButton insertbutton = new JButton("Insert");
	private JButton updatebutton = new JButton("Update");
	private JButton deletebutton = new JButton("Delete");
	
	// results pane
	private ResultsPane resultspane = new ResultsPane();
	
	public MainWindow(Table[] tables) {
		super("Purchases Database");
		setLayout(new GridBagLayout()); // allows the jpanels to display next to each other properly
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit on close
		setSize(580, 580);
		setLocation(100,100);
		setVisible(true);
		
		setSelectedTable(tables[0]);
		setTables(tables);
		
		// event handlers
		TableButtonHandler tablebuttonhandler = new TableButtonHandler();
		TopButtonHandler topbuttonhandler = new TopButtonHandler();
		TableMouseHandler tablemousehandler = new TableMouseHandler();
		
		// add sidebar
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 10;
		add(sidebar, c);
		sidebar.add(customers);
		customers.setEnabled(false);
		sidebar.add(products);
		sidebar.add(sales);
		// set up event listener for table buttons
		customers.addActionListener(tablebuttonhandler);
		products.addActionListener(tablebuttonhandler);
		sales.addActionListener(tablebuttonhandler);
		
		// add topbar
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		add(topbar, c);
		topbar.add(insertbutton);
		topbar.add(updatebutton);
		topbar.add(deletebutton);
		// disable update and delete buttons by default
		updatebutton.setEnabled(false);
		deletebutton.setEnabled(false);
		// set up event listener for top buttons
		insertbutton.addActionListener(topbuttonhandler);
		updatebutton.addActionListener(topbuttonhandler);
		deletebutton.addActionListener(topbuttonhandler);
		
		// add results pane
		c.gridy = 1;
		c.gridheight = 10;
		add(resultspane, c);
		selectedtable.display(resultspane);
		resultspane.getResultTable().addMouseListener(tablemousehandler);
		
	}
	
	// set the list of tables
	private void setTables(Table[] tables) {
		this.tables = tables;
	}
	
	// set the currently selected table
	private void setSelectedTable(Table table) {
		selectedtable = table;
	}
	
	// called when the user selects a table from the sidebar
	private class TableButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			// disable currently selected button and update currently selected table
			if(event.getSource() == customers) {
				setSelectedTable(tables[0]);
				customers.setEnabled(false);
				products.setEnabled(true);
				sales.setEnabled(true);
			}
			else if(event.getSource() == products) {
				setSelectedTable(tables[1]);
				customers.setEnabled(true);
				products.setEnabled(false);
				sales.setEnabled(true);
			}
			else if(event.getSource() == sales) {
				setSelectedTable(tables[2]);
				customers.setEnabled(true);
				products.setEnabled(true);
				sales.setEnabled(false);
			}
			selectedtable.display(resultspane);
			selectedRow();
		}
	}
	
	// called when the user selects the insert, update or delete buttons
	private class TopButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			
			if(event.getSource() == insertbutton) {
				// open the insert window
				new InsertWindow(selectedtable, resultspane);
			}
			else if(event.getSource() == updatebutton) {
				// array of information about the selected row
				Object[] info = new Object[selectedtable.getColumns().length];
				for(int i = 0; i < info.length; i++) {
					info[i] = resultspane.getResultTable().getValueAt(selectedRow(), i);
				}
				// open the update window
				new UpdateWindow(info, selectedtable, resultspane);
				
			}
			else if(event.getSource() == deletebutton) {
				// confirm deletion
				int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete this entry?", "Delete from " + selectedtable.getName(), JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					// delete from table
					JOptionPane.showMessageDialog(rootPane, (Object)selectedtable.delete(selectedId()));
				}
			}
		}
	}
	
	private class TableMouseHandler implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override // select a table entry
		public void mouseReleased(MouseEvent e) {
			selectedRow();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	private int selectedId() {
		return (int)resultspane.getResultTable().getValueAt(selectedRow(), 0);
	}
	
	// gets the selected table entry
	private int selectedRow() {
		// get selected row
		int row = resultspane.getResultTable().getSelectedRow();
		// disable or enable update/delete buttons
		if(row > -1) {
			if(selectedtable.getName() != "SALES") {
				updatebutton.setEnabled(true);
			}
			deletebutton.setEnabled(true);
		}
		else {
			updatebutton.setEnabled(false);
			deletebutton.setEnabled(false);
		}
		return row;
	}
}
