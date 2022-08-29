package pm.client;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel {

	String[] c_name;//컬럼명들
	String[][] data;// 보여질 정보들
	
	
	public MyModel(String[] c_name, String[][] data) {
		this.c_name = c_name;
		this.data = data;
		if(data == null)
			this.data = new String[0][0];
	}

	@Override
	public int getRowCount() {
		// 행의 수 반환 (2차원 배열에 저장된 1차원배열의 수 반환)
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// 컬럼의 수 반한
		return c_name.length;
	}

	@Override
	public Object getValueAt(int r, int c) {
		// JTable의 각 격자에 표현할 데이터들을 반환(2차원 배열의 요소들 반환)
		return data[r][c];
	}

	@Override
	public String getColumnName(int column) {
		// 컬럼명 반환
		return c_name[column];
	}
	
	

}
