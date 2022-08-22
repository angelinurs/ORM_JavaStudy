package ex_employee.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import ex_employee.vo.EmployeeVO;

public class Employee_JFrame extends JFrame {
	
	JMenuBar bar;
	JMenu file_mnu;
	JMenuItem add_item,
			  search_item;
	
	JPanel mainPane,
		   northPane,
		   southPane;
	
	JComboBox<String> comboBox;
	JTextField keywordText;
	JButton confirmBtn;
	
	JTable table;
	
	JButton[] btns;
	
	// per paging
	int begin = 1;
	int end = 60000;
	int pagePerBlock = 60000;
	int currentPage = 1;
	int totalPage = 5;
	
	// assets
	String[] columns = { "Row_Number", "Emp_no", "Birth_date", "First_name", "Last_name", "Gender", "Hire_date" }; 
	
	SqlSessionFactory factory;
	
	// dialogue form components
	JPanel viewPane;
	
	JLabel noLabel, birthLabel, 
		fnameLabel, lnameLabel, genderLabel, hire_dateLabel;
	JTextField noTextfield, birthTextfield, 
			fnameTextfield, lnameTextfield, genderTextfield, hire_dateTextfield;
	
	public Employee_JFrame() {
		super( "Employee ORM JFrame ex" );
		
		getSqlSessionFactory();
		
		setMainMenubar();
		
		setMainPane();
		
		setBounds( 300, 300, 500, 500 );
		setVisible( true );
		
		runEvents();
		
	}

	private void runEvents() {
		addWindowListener( new WindowAdapter() {

			@Override public void windowClosing(WindowEvent e) {
				System.exit( 0 );
			}
		});
		
		confirmBtn.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				String keyword = keywordText.getText().trim();
				searchOne( keyword );
			}
		});
		
		keywordText.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				String keyword = keywordText.getText().trim();
				searchOne( keyword );
			}
		});
		
		add_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				viewRecordForm();
				insert( true );
			}
		});
		
		search_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				viewRecordForm();
				noTextfield.setText( "" );
				
				int index = JOptionPane.showConfirmDialog( null, viewPane, 
						"Search record", JOptionPane.YES_NO_CANCEL_OPTION );
				
				if( index == JOptionPane.YES_OPTION ) {
					searchMutli();
				}
			}
		});
		
		table.addMouseListener( new MouseAdapter() {

			@Override public void mousePressed(MouseEvent e) {
				int count = e.getClickCount();
				
				if( count == 2 ) {
					viewRecordForm();
					//
					int row = table.getSelectedRow();
					noTextfield.setText( (String)table.getValueAt( row, 0 ) );
					birthTextfield.setText( (String)table.getValueAt( row, 1 ) );
					fnameTextfield.setText( (String)table.getValueAt( row, 2 ) );
					lnameTextfield.setText( (String)table.getValueAt( row, 3 ) );
					genderTextfield.setText( (String)table.getValueAt( row, 4 ) );
					hire_dateTextfield.setText( (String)table.getValueAt( row, 5 ) );
					//
					
					insert( false );
				}
			}
		});
	}
	
	private void getTotalPage() {
		SqlSession ss = factory.openSession( true );
		
		int count = ss.selectOne( "employee.total_page" );
		
		totalPage = ( count % pagePerBlock == 0 )? count / pagePerBlock: 
			count / pagePerBlock + 1;
//		totalPage = (int) Math.ceil( (double) count / pagePerBlock );
		
		if( ss != null ) ss.close();
	}
	
	private void getTotalPage( String mapperName, Map<String, String> map ) {
		SqlSession ss  = factory.openSession();
		
		List<EmployeeVO> list = ss.selectList( mapperName, map );
		int count = list.size();
		
		totalPage = ( count % pagePerBlock == 0 )? count / pagePerBlock: 
												   count / pagePerBlock + 1;
		if( ss != null ) ss.close();
	}
	
	private void getSqlSessionFactory() {
		Reader r = null;
		try {
			r = Resources.getResourceAsReader( "ex_employee/config/config.xml" );
			
			factory = new SqlSessionFactoryBuilder().build( r );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if( r != null ) r.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void select( List<EmployeeVO> list ) {
		
		String[][] doubleList = new String[ list.size() ][ columns.length ];
		
		int count = 0;
		
		for( EmployeeVO vo : list ) {
			doubleList[ count ][ 0 ] = String.valueOf( vo.getRowNumber() );
			doubleList[ count ][ 1 ] = vo.getEmp_no();
			doubleList[ count ][ 2 ] = vo.getBirth_date();
			doubleList[ count ][ 3 ] = vo.getFirst_name();
			doubleList[ count ][ 4 ] = vo.getLast_name();
			doubleList[ count ][ 5 ] = vo.getGender();
			doubleList[ count ][ 6 ] = vo.getHire_date();
			count++;
		}
		
		table.setModel( new DefaultTableModel( doubleList, columns ) );
	}

	protected void viewRecordForm() {
		viewPane = new JPanel( new GridLayout( 6, 2 ) );
		
		viewPane.add( noLabel = new JLabel( "Employee No : " ) );
		viewPane.add( noTextfield = new JTextField( 10 ) );
		viewPane.add( birthLabel = new JLabel( "Birth date : " ) );
		viewPane.add( birthTextfield = new JTextField( 10 ) );
		viewPane.add( fnameLabel = new JLabel( "First name : " ) );
		viewPane.add( fnameTextfield = new JTextField( 10 ) );
		viewPane.add( lnameLabel = new JLabel( "Last name : " ) );
		viewPane.add( lnameTextfield = new JTextField( 10 ) );
		viewPane.add( genderLabel = new JLabel( "Gender : " ) );
		viewPane.add( genderTextfield = new JTextField( 10 ) );
		viewPane.add( hire_dateLabel = new JLabel( "Hire : " ) );
		viewPane.add( hire_dateTextfield = new JTextField( 10 ) );
		
	}
	
	protected void insert( boolean isNew ) {
		noTextfield.setEditable( false );

		SqlSession ss = factory.openSession( true );
		
		if( isNew ) {
			int last_emp_no = ss.selectOne( "employee.last_id" );
			String emp_no = String.valueOf( last_emp_no + 1 );
			noTextfield.setText( emp_no );
		}
		
		int index = JOptionPane.showConfirmDialog( null, viewPane, 
						"Add record", JOptionPane.YES_NO_CANCEL_OPTION );
		
		if( index == JOptionPane.YES_OPTION ) {
			String emp_no = noTextfield.getText().trim();
			String birth = birthTextfield.getText().trim();
			String fname = fnameTextfield.getText().trim();
			String lname = lnameTextfield.getText().trim();
			String gender = genderTextfield.getText().trim().toUpperCase();
			String hire_date = hire_dateTextfield.getText().trim();
			
			Map<String, String> map = new HashMap<>();
			map.put( "emp_no", emp_no );
			map.put( "birth_date", birth );
			map.put( "first_name", fname );
			map.put( "last_name", lname );
			map.put( "gender", gender );
			map.put( "hire_date", hire_date );
			
			if( isNew ) {
				ss.insert( "employee.add", map );
			} else {
				ss.update( "employee.update", map );
			}
			
			if( ss != null ) ss.close();
			
			searchOne( emp_no );
		}
	}
	
	protected void searchOne( String keyword ) {
		if( keyword.length() < 1) {
			return;
		}
		
		int comboIndex = comboBox.getSelectedIndex();
		String comboItem = comboBox.getItemAt( comboIndex ).toLowerCase();
		
		Map<String, String> map = new HashMap<>();
		map.put( comboItem, keyword );
		
		String mapperName = "employee.search";
		getTotalPage(mapperName, map);
		
		setBtns(mapperName, map);
		
	}
	
	protected void searchMutli() {
		String emp_no = noTextfield.getText().trim();
		String birth = birthTextfield.getText().trim();
		String fname = fnameTextfield.getText().trim();
		String lname = lnameTextfield.getText().trim();
		String gender = genderTextfield.getText().trim().toUpperCase();
		String hire_date = hire_dateTextfield.getText().trim();
		
		Map<String, String> map = new HashMap<>();
		if( emp_no.length() > 0 ) { map.put( "emp_no", emp_no ); }
		if( birth.length() > 0 ) { map.put( "birth_date", birth ); }
		if( fname.length() > 0 ) { map.put( "first_name", fname ); }
		if( lname.length() > 0 ) { map.put( "last_name", lname ); }
		if( gender.length() > 0 ) { map.put( "gender", gender ); }
		if( hire_date.length() > 0 ) { map.put( "hire_date", hire_date ); }
		
		String mapperName = "employee.search";
		getTotalPage(mapperName, map);
		setBtns(mapperName, map);

	}

	private void setMainPane() {
		add( mainPane = new JPanel( new BorderLayout() ) );
		
		mainPane.add( northPane = new JPanel( new BorderLayout() ), BorderLayout.NORTH );
		
		northPane.add( comboBox = new JComboBox<>( new DefaultComboBoxModel<>( columns )), BorderLayout.WEST );
		comboBox.setBackground( Color.white );
		northPane.add( keywordText = new JTextField( 10 ), BorderLayout.CENTER );
		northPane.add( confirmBtn = new JButton( "Find" ), BorderLayout.EAST );
		
		table = new JTable( new DefaultTableModel( null, columns ) ); 
		
		mainPane.add( new JScrollPane( table ), BorderLayout.CENTER );
		
		mainPane.add( southPane = new JPanel( new FlowLayout( FlowLayout.LEFT ) ), BorderLayout.SOUTH );
		
		getTotalPage();
		Map<String, String> map = new HashMap<>();
		String mapperName = "employee.all_with_index";
		setBtns( mapperName, map );
		
	}

	private void setBtns( String mapperName, Map<String, String> map ) {
		
		southPane.removeAll();
		
		btns = new JButton[ totalPage ];
		
		for( int idx = 0; idx < totalPage; idx++ ) {
			southPane.add( btns[ idx ] =  new JButton( String.valueOf( idx + 1 ) ) );
			btns[ idx ].addActionListener( new ActionListener() {
				
				@Override public void actionPerformed(ActionEvent e) {
					String no = e.getActionCommand();
					
					currentPage = Integer.parseInt( no );
					
					begin = ( currentPage - 1 ) * pagePerBlock + 1;
					end  = currentPage * pagePerBlock;
					
					System.out.println( begin + " / " + end + " / " + currentPage );
					
					map.put( "begin", String.valueOf( begin ) );
					map.put( "end", String.valueOf( end ) );
					System.out.println( map.toString() );
					
					SqlSession ss = factory.openSession();
					
					List<EmployeeVO> list = ss.selectList( mapperName, map );
					
					select( list );
					
					if( ss != null ) ss.close();
				}
			});
		}
		
		southPane.updateUI();
	}

	private void setMainMenubar() {
		bar = new JMenuBar();
		
		bar.add( file_mnu = new JMenu( "File" ) );
		file_mnu.add( add_item = new JMenuItem( "Add Employee" ) );
		file_mnu.add( search_item = new JMenuItem( "Search Employee" ) );
		
		setJMenuBar( bar );
	}

	public static void main(String[] args) {
		new Employee_JFrame();
	}
}
