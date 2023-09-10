package gui;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Insert Window class. Pops up to allow user to enter insert details

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import db.*;

public class InsertWindow extends JFrame{
	
	private Table table;
	private ResultsPane resultspane;
	
	// text fields
	private JTextField[] textfields;
	
	private JPanel titlepanel = new JPanel();
	private JPanel buttonpanel = new JPanel();
	private JButton insert = new JButton("Insert");
	
	public InsertWindow(Table table, ResultsPane resultspane) {
		super("Insert record into "+ table.getName());
		setLayout(new GridLayout(0, 1)); //allows the jpanels to display next to each other properly
		setSize(350, 500);
		setLocation(250,200);
		setVisible(true);
		
		this.table = table;
		this.resultspane = resultspane;
		
		add(titlepanel);
		titlepanel.add(new JLabel("Insert record into " + table.getName()));
		
		textfields = new JTextField[table.getColumns().length-1];
		
		// loop through columns and add information to the form
		for(int i = 1; i < table.getColumns().length; i++) {
			JPanel panel = new JPanel();
			add(panel);
			panel.add(new JLabel(table.getColumns()[i]));
			JTextField textfield = new JTextField();
			textfield.setPreferredSize(new Dimension(200,25));
			panel.add(textfield);
			
			// add text field to the array
			textfields[i-1] = textfield;
		}
		
		InsertionHandler insertionhandler = new InsertionHandler();
		
		add(buttonpanel);
		buttonpanel.add(insert);
		insert.addActionListener(insertionhandler);
	}
	
	private class InsertionHandler implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String[] details = new String[textfields.length];
			
			// place field information into an array
			for(int i = 0; i < details.length; i++) {
				details[i] = textfields[i].getText();
			}
			// insert into table
			JOptionPane.showMessageDialog(rootPane, (Object)table.insert(details));
			table.display(resultspane);
		}
		
	}
}
