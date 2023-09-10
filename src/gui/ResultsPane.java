package gui;

//Student Name 		:	Amy Anderson
//Student Id Number	:	C00276123
//Purpose 			:	Results Pane Class

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ResultsPane extends JScrollPane {
	
	private ResultSet rs;
	private ResultSetMetaData metadata;
	private JTable resulttable = new JTable();
	private DefaultTableModel tableinfo;
	
	public ResultsPane() {
		setViewportView(resulttable);
		// make table uneditable
		resulttable.setDefaultEditor(Object.class, null);
		// select only one row at a time
		resulttable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public JTable getResultTable() {
		return resulttable;
	}
	
	public void updateTable(ResultSet rs) {
		try {
			this.rs = rs;
			this.metadata = rs.getMetaData();
			tableinfo = new DefaultTableModel();
			for(int i = 1; i <= metadata.getColumnCount(); i++) {
				tableinfo.addColumn(metadata.getColumnName(i));
			}
			
			while(rs.next()) {
				Object[] rowData = new Object[metadata.getColumnCount()];
				for(int i = 0; i < metadata.getColumnCount(); i++) {
					rowData[i] = rs.getObject(i+1);
				}
				tableinfo.addRow(rowData);
			}
			
			resulttable.setModel(tableinfo);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
