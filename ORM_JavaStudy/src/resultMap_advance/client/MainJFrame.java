package resultMap_advance.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

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
import javax.swing.plaf.metal.MetalSplitPaneUI;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_advance.vo.BuseoVO;
import resultMap_advance.vo.SawonVO;

public class MainJFrame extends JFrame {
	
	JMenuBar bar;
	JMenu searchMnu;
	JMenuItem searchAll_item;
	
	JTable table;
	
	List<BuseoVO> list;
	int rowIndex;
	
	JPanel mainPane;
//	JPanel southPane;
	JPanel msgPane;
	
	// session
	SqlSessionFactory factory;
	
	// assets
	String configuration = "resultMap_advance/config/config.xml";
	String[] columns = { "Dept_no", "Dept_name", "Emp_no", "first_name" };
	
	public MainJFrame() {
		super( "Result Map ex." );
		
		setMainPane();
		pack();
		
		setSessionFactory();
		
		setBounds( 300, 300, 400, 300 );
		setVisible( true );
		setResizable( false );
		
		setEvents();
		
	}

	private void setEvents() {
		addWindowListener( new WindowAdapter() {

			@Override public void windowClosing(WindowEvent e) {
				System.exit( 0 );
			}
		});
		
		searchAll_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				
				SqlSession ss = factory.openSession();
				list = ss.selectList( "employee.all" );
				
				if( ss != null ) ss.close();
				
				String[][] doubleList = new String[ list.size() ][ columns.length ];
				
				int colIndex = 0;
				for( BuseoVO bvo : list ) {
					doubleList[ colIndex ][ 0 ] = bvo.getDept_no();
					doubleList[ colIndex ][ 1 ] = bvo.getDept_name();
					doubleList[ colIndex ][ 2 ] = bvo.getSvo().getEmp_no();
					doubleList[ colIndex ][ 3 ] = bvo.getSvo().getFirst_name();
					colIndex++;
				}
				
				table.setModel( new DefaultTableModel( doubleList, columns ) );
			}
		});
		
		table.addMouseListener( new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int clickCount = e.getClickCount();
				
				if( clickCount == 2 ) {
					rowIndex = table.getSelectedRow();
					
					viewDialogue( );
				}
			}
			
		});
		
	}

	protected void viewDialogue(  ) {
		
		SawonVO svo = list.get( rowIndex ).getSvo();
		
		
		msgPane = new JPanel( new GridLayout( 6,  2 ) );
		
		msgPane.add( new JLabel( "Emp no : ") );
		msgPane.add( new JTextField( svo.getEmp_no() ) );
		
		msgPane.add( new JLabel( "First name : ") );
		msgPane.add( new JTextField( svo.getFirst_name() ) );
		
		msgPane.add( new JLabel( "Last name : ") );
		msgPane.add( new JTextField( svo.getLast_name() ) );
		
		msgPane.add( new JLabel( "Birth date : ") );
		msgPane.add( new JTextField( svo.getBirth_date() ) );
		
		msgPane.add( new JLabel( "Gender : ") );
		msgPane.add( new JTextField( svo.getGender() ) );
		
		msgPane.add( new JLabel( "Hire date : ") );
		msgPane.add( new JTextField( svo.getHire_date() ) );
		
		JOptionPane.showMessageDialog( null, msgPane, "Detail", JOptionPane.YES_NO_OPTION );
	}

	private void setMainPane() {
		
		bar = new JMenuBar();
		
		bar.add( searchMnu = new JMenu( "Find" ) );
		searchMnu.add( searchAll_item = new JMenuItem( "View all" ) );
		
		setJMenuBar( bar );
		
		add( mainPane = new JPanel( new BorderLayout() ), BorderLayout.CENTER );
		table = new JTable( new DefaultTableModel( null, columns ) );
		mainPane.add( new JScrollPane( table ), BorderLayout.CENTER );
		
//		table.setEnabled( false );
	}
	
	private void setSessionFactory() {
		Reader r = null;
		try {
			r = Resources.getResourceAsReader( configuration );
			
			factory = new SqlSessionFactoryBuilder().build( r );
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if( r != null ) r.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new MainJFrame();
	}

}
