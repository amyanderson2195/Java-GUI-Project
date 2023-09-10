package gui;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Update Window class. Pops up to allow user to update details

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import db.Table;

public class UpdateWindow extends JFrame{

	private Table table;
	private ResultsPane resultspane;
	
	// text fields
	private JTextField[] textfields;
	
	private JPanel titlepanel = new JPanel();
	private JPanel buttonpanel = new JPanel();
	private JButton update = new JButton("Update");
	
	public UpdateWindow(Object[] info, Table table, ResultsPane resultspane) {
		super("Update record in " + table.getName() + " table");
		setLayout(new FlowLayout()); //allows the jpanels to display next to each other properly
		setSize(350, 500);
		setLocation(250,200);
		setVisible(true);
		
		this.table = table;
		this.resultspane = resultspane;
		
		add(titlepanel);
		titlepanel.add(new JLabel("Update record in " + table.getName()));
		
		textfields = new JTextField[table.getColumns().length];
		
		// loop through columns and add information to the form
		for(int i = 0; i < table.getColumns().length; i++) {
			JPanel panel = new JPanel();
			add(panel);
			panel.add(new JLabel(table.getColumns()[i]));
			JTextField textfield = new JTextField(info[i].toString());
			textfield.setPreferredSize(new Dimension(200,25));
			panel.add(textfield);
			
			// add text field to the array
			textfields[i] = textfield;
			
			// field is not editable if it is a primary key
			if(i == 0) {
				textfield.setEnabled(false);
			}
		}
		
		UpdateHandler updatehandler = new UpdateHandler();
		
		add(buttonpanel);
		buttonpanel.add(update);
		update.addActionListener(updatehandler);
	}
	
	private class UpdateHandler implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String[] details = new String[textfields.length];
			
			// place field information into an array
			for(int i = 0; i < details.length; i++) {
				details[i] = textfields[i].getText();
			}
			
			// insert into table
			JOptionPane.showMessageDialog(rootPane, (Object)table.update(details));
			table.display(resultspane);
		}
		
	}
}
