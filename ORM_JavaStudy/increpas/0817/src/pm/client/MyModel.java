package pm.client;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel {
	//JTable에서 표현할 컬럼명들
	String[] c_name;
	String[][] data;
	
	public MyModel(String[][] data , String[] c) {
		this.c_name = c;
		this.data = data;
		if(data == null) 
			this.data = new String[0][0];		
		
	}

	@Override
	public int getRowCount() {
		// data라는 2차원배열에 저장된 1차원 배열의 길이
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// JTable에 표현할 컬럼의 수 반환
		return c_name.length; //컬럼명이 나타나지 않으며, 컬럼명을 나타나게 하려면 getColumnName함수
	}							// 재정의 해야 함!

	@Override
	public String getColumnName(int column) {
		// getColumnCount()에서 반환된 값 만큼 호출되는 곳
		return c_name[column];
	}

	@Override
	public Object getValueAt(int row, int column) {
		// getRowCount()에서 반환된 값 * getColumnCount()에서 반환된 값 만큼 호출되는 곳
		//예를 들어 getColumnCount()에서 4를, getRowCount()에서 9를 4*9 =36
		// 현재 메서드가 
		// getValueAt(0,0)  getValueAt(0,1)   getValueAt(0,2)   getValueAt(0,3)  
		// getValueAt(1,0)  getValueAt(1,1)   getValueAt(1,2)   getValueAt(1,3)
		// getValueAt(2,0)  getValueAt(2,1)   getValueAt(2,2)   getValueAt(2,3)
		// getValueAt(3,0)  getValueAt(3,1)   getValueAt(3,2)   getValueAt(3,3)
		// getValueAt(4,0)  getValueAt(4,1)   getValueAt(4,2)   getValueAt(4,3)
		// getValueAt(5,0)  getValueAt(5,1)   getValueAt(5,2)   getValueAt(5,3)
		// getValueAt(6,0)  getValueAt(6,1)   getValueAt(6,2)   getValueAt(6,3)
		// getValueAt(7,0)  getValueAt(7,1)   getValueAt(7,2)   getValueAt(7,3)
		// getValueAt(8,0)  getValueAt(8,1)   getValueAt(8,2)   getValueAt(8,3)
		//JTable이렇게 36번 호출한다.
		return data[row][column];
	}
	
	
}
