package ex_employee.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
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
	JMenuItem add_item;
	
	JPanel mainPane,
		   northPane,
		   southPane;
	
	JComboBox<String> comboBox;
	JTextField keywordText;
	JButton confirmBtn;
	
	JTable table;
	
	JLabel statusLabel;
	
	// assets
	String[] columns = { "Emp_no", "Birth_date", "First_name", "Last_name", "Gender", "Hire_date" }; 
	
	SqlSessionFactory factory;
	
	// dialogue form
	JPanel viewPane;
	
	JLabel noLabel, birthLabel, 
		fnameLabel, lnameLabel, genderLabel, hire_dateLabel;
	JTextField noTextfield, birthTextfield, 
			fnameTextfield, lnameTextfield, genderTextfield, hire_dateTextfield;
	
	public Employee_JFrame() {
		super( "Employee ORM JFrame ex" );
		
		setMainMenubar();
		
		setMainPane();
		
		getSqlSessionFactory();
		
		setBounds( 300, 300, 500, 500 );
		setVisible( true );
		
		runEvents();
		
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

	private void runEvents() {
		addWindowListener( new WindowAdapter() {

			@Override public void windowClosing(WindowEvent e) {
				System.exit( 0 );
			}
		});
		
		confirmBtn.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				search();
				
			}
		});
		
		keywordText.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		add_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				viewRecordForm();
				noTextfield.setEditable( false );

				int index = JOptionPane.showConfirmDialog( null, viewPane, 
								"Add record", JOptionPane.YES_NO_CANCEL_OPTION );
				
				if( index == JOptionPane.YES_OPTION ) {
					String birth = birthTextfield.getText().trim();
					String fname = fnameTextfield.getText().trim();
					String lname = lnameTextfield.getText().trim();
					String gender = genderTextfield.getText().trim();
					String hire_date = hire_dateTextfield.getText().trim();
					
					Map<String, String> map = new HashMap<>();
					map.put( "birth_date", birth );
					map.put( "first_name", fname );
					map.put( "last_name", lname );
					map.put( "gender", gender );
					map.put( "hire_date", hire_date );
					
					SqlSession ss = factory.openSession( true );
					
					ss.insert( "employee.add", map );
					
					statusLabel.setText( "Success to add record." );
					
					if( ss != null ) ss.close();
				}
			}
		});
	}

	protected void viewRecordForm() {
		viewPane = new JPanel( new GridLayout( 6, 2 ) );
		
		viewPane.add( noLabel = new JLabel( "No : " ) );
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

	protected void search() {
		String keyword = keywordText.getText().trim();
		
		if( keyword.length() < 1) {
			return;
		}
		
		int comboIndex = comboBox.getSelectedIndex();
		String comboItem = comboBox.getItemAt( comboIndex ).toLowerCase();
		
		Map<String, String> map = new HashMap<>();
		map.put( comboItem, keyword );
		
		SqlSession ss  = factory.openSession();
		
		List<EmployeeVO> list = ss.selectList( "employee.search", map );
		
		String[][] doubleList = new String[ list.size() ][ columns.length ];
		
		int count = 0;
		
		for( EmployeeVO vo : list ) {
			doubleList[ count ][ 0 ] = vo.getEmp_no();
			doubleList[ count ][ 1 ] = vo.getBirth_date();
			doubleList[ count ][ 2 ] = vo.getFirst_name();
			doubleList[ count ][ 3 ] = vo.getLast_name();
			doubleList[ count ][ 4 ] = vo.getGender();
			doubleList[ count ][ 5 ] = vo.getHire_date();
			count++;
		}
		
		table.setModel( new DefaultTableModel( doubleList, columns ) );
		
		statusLabel.setText( "Success to search. " + list.size() );
		
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
		
		mainPane.add( southPane = new JPanel( new BorderLayout() ), BorderLayout.SOUTH );
		
		southPane.add( statusLabel = new JLabel( "Welcom.") );
	}

	private void setMainMenubar() {
		bar = new JMenuBar();
		
		bar.add( file_mnu = new JMenu( "File" ) );
		file_mnu.add( add_item = new JMenuItem( "Add Book" ) );
		
		setJMenuBar( bar );
	}

	public static void main(String[] args) {
		new Employee_JFrame();
	}

}
