package orm_start_mapper.client;

import javax.swing.table.DefaultTableModel;

public class MyModel extends DefaultTableModel {
	String[][] data;
	String[] names;
	
	public MyModel( String[][] data, String[] names ) {
		this.names = names;
		this.data = ( data == null ) ? new String[0][0] : data;
	}

	// necessary method
	@Override public int getRowCount() {
		return data.length;
	}

	// necessary method
	@Override public int getColumnCount() {
		return names.length;
	}

	// necessary method
	@Override public Object getValueAt(int row, int column) {
		return data[ row ][ column ];
	}

	@Override public String getColumnName(int column) {
		return names[ column ];
	}
	
}
