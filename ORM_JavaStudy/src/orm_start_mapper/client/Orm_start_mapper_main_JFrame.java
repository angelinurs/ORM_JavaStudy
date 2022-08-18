package orm_start_mapper.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import orm_start_mapper.vo.EmployeeVO;

public class Orm_start_mapper_main_JFrame extends JFrame {
	
	SqlSession ss = null;
	
	JTable table;
	TableModel model;
	
	private String[] column_names = { "emp_no" , "first_name", "last_name",
									"birth_date", "gender", "hire_date" };
	private String[][] doubleList;
	
	JMenuBar bar;
	JMenu system_mnu,
		  find_mnu;
	
	JMenuItem name_item,
			  emp_no_item,
			  hire_date_item,
			  selectAll_item;
	JMenuItem save_item,
			  exit_item;
	
	String search_keyword;
	List<EmployeeVO> list;
	
	public Orm_start_mapper_main_JFrame()	{
		super( "ORM mapper." );
		
		setMenubar();

		dbConnect();
		setJTable();
		pack();
		
		setBounds( 300, 300, 800, 800 );
		setVisible( true );
		
		setEvent();
		
	}
	
	// events
	private void setEvent() {
		
		addWindowListener( new WindowAdapter() {
			
			@Override public void windowClosing(WindowEvent e) {
				if( ss != null ) ss.close();
				
				System.exit( 0 );
			}
		});
		
		exit_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				if( ss != null ) ss.close();
				
				System.exit( 0 );
			}
		});
		
		save_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				new ResultToExcel( list, column_names );
			}
		});
		
		name_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				searchSelect( "emp.search_name" );
			}
		});
		
		emp_no_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				searchSelect( "emp.search_emp_no" );
			}
		});
		
		hire_date_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
//				searchSelect( "emp.search_hire_date" );
				
				JPanel mainMsgPain;
				
				JTextField fromDate, toDate;
				
				mainMsgPain = new JPanel( new GridLayout( 2, 3 ) );
				
				mainMsgPain.add( new JLabel( "From date" ) );
				mainMsgPain.add( fromDate = new JTextField( 10 ) );
				mainMsgPain.add( new JLabel( "To date" ) );
				mainMsgPain.add( toDate = new JTextField( 10 ) );
				
//				JOptionPane.showMessageDialog( null, mainMsgPain );
				int confirmValue = JOptionPane.showConfirmDialog( null, mainMsgPain );
				
				if( confirmValue == JOptionPane.YES_OPTION ) {
					
					String fromText = fromDate.getText().trim();
					String toText = toDate.getText().trim();
					
					if( fromText.length()  > 0 || toText.length() > 0 ) {
						
						System.out.println(fromText + " / " + toText );
						HashMap<String, String> map  = new HashMap<>();
						map.put( "from", fromText );
						map.put( "to", toText );
						
						// map 을 사용해서 다양한 parameter 를 줄 수 있다.
						list = ss.selectList( "emp.search_between_date", map );
						getSqlResults();
					}
				}
			}
		});
		
		selectAll_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				list = ss.selectList( "emp.all" );
				getSqlResults();
			}
		});
		
		// when table row is clicked, occured event
		table.addMouseListener( new MouseAdapter() {
			
			@Override public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
//				int col = table.getSelectedColumn();
				
//				StringBuffer sb = new StringBuffer();
//				
//				EmployeeVO vo =  list.get( row );
//				
//				sb.append( vo.getEmp_no() ).append( "/" );
//				sb.append( vo.getFirst_name() ).append( "/" );
//				sb.append( vo.getLast_name() ).append( "/" );
//				sb.append( vo.getGender() ).append( "/" );
//				sb.append( vo.getBirth_date() ).append( "/" );
//				sb.append( vo.getHire_date() ).append( "/" );
				
//				JOptionPane.showMessageDialog( Orm_start_mapper_main_JFrame.this, sb.toString() );
				
				StringBuffer sb = new StringBuffer();
				
				for( int idx = 0; idx < table.getColumnCount(); idx++ ) {
					sb.append( table.getModel().getValueAt( row, idx ) ).append( " / " );
				}
				JOptionPane.showMessageDialog( Orm_start_mapper_main_JFrame.this, sb.toString() );
				
			}
		});
		
	}

	private void setMenubar() {
		bar = new JMenuBar();
		
		bar.add( find_mnu = new JMenu( "Find" ) );
		bar.add( system_mnu = new JMenu( "System" ) );
		
		find_mnu.add( name_item = new JMenuItem( "find by name" ) );
		find_mnu.add( emp_no_item = new JMenuItem( "find by empNo" ) );
		find_mnu.add( hire_date_item = new JMenuItem( "find by hireDate" ) );
		find_mnu.add( selectAll_item = new JMenuItem( "selectAll" ) );
		
		
		system_mnu.add( save_item = new JMenuItem( "Save" ) );
		system_mnu.add( exit_item = new JMenuItem( "Exit" ) );
		
		setJMenuBar( bar );
		
	}

	private void setJTable() {
		model = new TableModel( null, column_names );
//		model = new TableModel( doubleList, column_names );
		table = new JTable( model );
		
		add( new JScrollPane( table ) );
	}
	
	private void searchSelect( String searchType ) {
		search_keyword = JOptionPane.showInputDialog( "Input keyword : " );
		
		if( search_keyword == null || search_keyword.trim().length() < 1) {
			return;
		}
		
		list = ss.selectList( searchType, search_keyword );
		getSqlResults();
	}
	
	private void getSqlResults() {
		
		doubleList = new String[ list.size() ][ column_names.length ];
		
		for( int idx = 0; idx < list.size(); idx++ ) {
			EmployeeVO temp = list.get( idx );
			doubleList[ idx ][ 0 ] = temp.getEmp_no();
			doubleList[ idx ][ 1 ] = temp.getFirst_name();
			doubleList[ idx ][ 2 ] = temp.getLast_name();
			doubleList[ idx ][ 3 ] = temp.getBirth_date();
			doubleList[ idx ][ 4 ] = temp.getGender();
			doubleList[ idx ][ 5 ] = temp.getHire_date();
		}
		
		table.setModel( new TableModel( doubleList, column_names ) );
		
		DecimalFormat df = new DecimalFormat( "#,###" );
		
		String count = df.format( list.size() );
		
		setTitle( count );
		
	}
	
	private void dbConnect() {
		Reader r;
		try {
			r = Resources.getResourceAsReader( "orm_start_mapper/config/config.xml" );
			
			ss = new SqlSessionFactoryBuilder().build( r ).openSession();
			
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Orm_start_mapper_main_JFrame();
	}
}
