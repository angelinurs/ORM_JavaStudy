package advance_books.client;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel {
	Object[][] list;
	Object[] names;
	
	public CustomTableModel( Object[][] list, Object[] names ) {
		this.list = ( list == null ) ? new Object[0][0]: list;
		this.names = names;
	}

	@Override public int getRowCount() {
		return list.length;
	}

	@Override public int getColumnCount() {
		return names.length;
	}

	@Override public Object getValueAt(int rowIndex, int columnIndex) {
		return list[ rowIndex ][ columnIndex ];
	}

	@Override public String getColumnName(int column) {
		return (String)names[ column ];
	}
	

}
