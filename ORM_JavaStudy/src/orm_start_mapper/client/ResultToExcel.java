package orm_start_mapper.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

// xls
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

// xlsx
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import orm_start_mapper.vo.EmployeeVO;

public class ResultToExcel {
	
	// supported .xls
	HSSFWorkbook wb = null;
	HSSFSheet sheet = null;
	Row row = null;
	Cell cell = null;
	
	// supported .xlsx
	XSSFWorkbook xwb = null;
	XSSFSheet xsheet = null;
	XSSFRow xrow = null;
	XSSFCell xcell = null;
	
	List<EmployeeVO> list;
	String[] names;
	
	public ResultToExcel( List< EmployeeVO > list, String[] names ) {
		this.list = list;
		this.names = names;
		
		int rowNumber = 0;
		short columnNumber = 0;
		
		try {
			xwb = new XSSFWorkbook();
			xsheet = xwb.createSheet( "Employees Table" );
			
			// create column names
			xrow = xsheet.createRow( rowNumber++ );
			
			for( String name : names ) {
				xcell = xrow.createCell( columnNumber++ );
				xcell.setCellValue( name );
			}
			
			System.out.println( columnNumber );
			
			String savefile = "./employees.xlsx";
			
			xwb.write( new FileOutputStream( new File( savefile ) ) );
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if( xwb != null ) xwb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
