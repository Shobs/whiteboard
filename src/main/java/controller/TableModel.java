package main.java.controller;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	Canvas c;
	
	public TableModel(Canvas c)
	{
		this.c = c;
	}
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
