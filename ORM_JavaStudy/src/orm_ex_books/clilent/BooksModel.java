package orm_ex_books.clilent;

import javax.swing.table.AbstractTableModel;

public class BooksModel extends AbstractTableModel {
	Object[][] data;
	String[] names;
	
	public BooksModel( Object[][] data, String[] names ) {
		this.data = ( data == null )? new Object[0][0] : data; 
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
}
