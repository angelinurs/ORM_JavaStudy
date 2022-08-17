package orm_start_mapper.client;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	Object[][] data;
	String[] names;
	
	public TableModel( Object[][] data, String[] names ) {
		this.data = ( data == null ) ? new String[0][0] : data;
		this.names = names;
	}

	@Override public int getRowCount() {
		return data.length;
	}

	@Override public int getColumnCount() {
		return names.length;
	}

	@Override public Object getValueAt(int rowIndex, int columnIndex) {
		return data[ rowIndex ][ columnIndex ];
	}

	@Override public String getColumnName(int column) {
		return names[ column ];
	}

	@Override public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex != 0;
	}

	@Override public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[ rowIndex ][ columnIndex ] = aValue;
	}
	
	
}
