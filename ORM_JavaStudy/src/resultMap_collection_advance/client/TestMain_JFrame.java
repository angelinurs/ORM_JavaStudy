package resultMap_collection_advance.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_collection_advance.vo.CheifVO;
import resultMap_collection_advance.vo.DiviVO;

public class TestMain_JFrame extends JFrame {
	
	JTable table;
	String[][] data;
	String[] columns = { "dept_code", "dept_name" };
	
	SqlSessionFactory factory = null;
	
	List<DiviVO> list = null;
	
	JPanel msgPane;
			
	
	public TestMain_JFrame()	{
		super( "resultMap collections" );
		
		setFactory();
		readDepartments();
		
		setMainPane();
		pack();
		
		setBounds( 300, 300, 300, 300 );
		setVisible( true );
		
		runEvents();
	}

	private void runEvents() {
		addWindowListener( new WindowAdapter() {

			@Override public void windowClosing(WindowEvent e) {
				System.exit( 0 );
			}
		});
		
		table.addMouseListener( new MouseAdapter() {

			@Override public void mousePressed(MouseEvent e) {
				int clickCount = e.getClickCount();
				
				if( clickCount == 2 ) {
					int selectedRow = table.getSelectedRow();
					
					String[] msgColumns = { "Emp no", "First name", "From date", "To date" };
					
					drawMsgFrom( selectedRow, msgColumns );
					
					JOptionPane.showMessageDialog( null, msgPane );
				}
			}
		});
	}
	
	private void drawMsgFrom( int selectedRow, String[] msgColumns) {
		List<CheifVO> c_list =  list.get( selectedRow ).getC_list();
		
		String[][] msgData = new String[ c_list.size() ][ msgColumns.length ];
		
		int count = 0;
		for( CheifVO cvo :  c_list ) {
			msgData[ count ][ 0 ] = cvo.getEmp_no();
			msgData[ count ][ 1 ] = cvo.getFirst_name();
			msgData[ count ][ 2 ] = cvo.getFrom_date();
			msgData[ count ][ 3 ] = cvo.getTo_date();
			count++;
		}
		
		JTable msgTable = new JTable( new DefaultTableModel( msgData, msgColumns ) );
		
		msgPane = new JPanel();
		msgPane.add( new JScrollPane( msgTable ) );
	}

	private void readDepartments() {
		SqlSession ss = factory.openSession();
		
		list = ss.selectList( "division.all" );
		
		if( ss != null ) ss.close();
		
		data = new String[ list.size() ][ columns.length ];
		
		int rowIndex = 0;
		for( DiviVO vo : list ) {
			data[ rowIndex ][ 0 ] = vo.getDept_no();
			data[ rowIndex ][ 1 ] = vo.getDept_name();
			rowIndex++;
		}
		
	}

	private void setFactory() {
		
		Reader r = null;
		try {
			r = Resources.getResourceAsReader( "resultMap_collection_advance/config/config.xml" );
			factory = new SqlSessionFactoryBuilder().build( r );
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if( r != null ) r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void setMainPane() {
		
		table = new JTable( new DefaultTableModel( data, columns ) );
		
		add( new JScrollPane( table ) );
	}
	
	

	public static void main(String[] args) {
		new TestMain_JFrame();
	}

}
