package advance_books.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

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

import advance_books.vo.BookVO;

public class Book_JFrame extends JFrame {
	
	JMenuBar bar;
	JMenu file_mnu;
	JMenuItem add_file,
			  exit_file;
	
	JPanel mainPane;
	JPanel northPane,
		   southPane;
	
	JComboBox<String> combo;
	JTextField wordTextfield;
	JButton searchBtn;
	
	JLabel bottomMsgLabel;
	
	JTable table;
	CustomTableModel model;
	
	String[] columnNames = { "Id", "Title", "Price", "ISBN", "Author ID" };
	
	SqlSessionFactory factory = null;
	
	public Book_JFrame() {
		super( "combo box search book info" );
		
		setMainMenubar();
		
		add( mainPane = new JPanel( new BorderLayout() ) );
		
		setNorthPane();
		setCenterPane();
		setSouthPane();
		
		getSqlFactory();
		
		setBounds( 300, 300, 500, 500 );
		setVisible( true );
		
		runApplicationEvents();
		
	}

	private void runApplicationEvents() {
		wordTextfield.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				searchWord();
			}
		});
		
		searchBtn.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				searchWord();
			}

		});
		
		add_file.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				JPanel addRecordPane = new JPanel( new GridLayout( 3, 2 ) );
				
				JLabel titleLabel, priceLabel, isbnLabel;
				JTextField titleTextfield, priceTextfield, isbnTextfield;
				
				addRecordPane.add( titleLabel = new JLabel( "Title : " ) );
				addRecordPane.add( titleTextfield = new JTextField( 10 ) );
				addRecordPane.add( priceLabel = new JLabel( "Price : " ) );
				addRecordPane.add( priceTextfield = new JTextField( 10 ) );
				addRecordPane.add( isbnLabel = new JLabel( "ISBN : " ) );
				addRecordPane.add( isbnTextfield = new JTextField( 10 ) );
				
				int optionIndex = 
						JOptionPane.showConfirmDialog( null, addRecordPane, 
									"Add book info", JOptionPane.YES_NO_OPTION );
				
				if( optionIndex == JOptionPane.YES_OPTION ) {
					
					String name = titleTextfield.getText().trim();
					String price = priceTextfield.getText().trim();
					String isbn = isbnTextfield.getText().trim();
					
					
					if( name.length() < 1 ) {
						JOptionPane.showMessageDialog( null, "Input name" );
						titleTextfield.requestFocus();
						return;
					}
					if( price.length() < 1 ) {
						JOptionPane.showMessageDialog( null, "Input name" );
						priceTextfield.requestFocus();
						return;
					}
					if( isbn.length() < 1 ) {
						JOptionPane.showMessageDialog( null, "Input name" );
						isbnTextfield.requestFocus();
						return;
					}
					
					SqlSession ss = factory.openSession( true );
					
					/* ***********************
					 *  insert to with map
					 */
					
//					Map<String, String> map = new HashMap<>();
//					map.put( "name", name );
//					map.put( "price", price );
//					map.put( "isbn", isbn );
//					
//					ss.insert( "book.insert", map );

					/* ***********************
					 *  insert to with VO
					 */

					BookVO insertData = new BookVO( name, price, isbn, "1" );
					ss.insert( "book.add_vo", insertData );
					
					bottomMsgLabel.setText( "success!!");
					
					
					/* ***********************
					 *  check result of insert
					 */
					Map<String, String> map = new HashMap<>();
					map.put( "search_type", "3");
					map.put( "searchValue", isbn );
					
					List<BookVO> list =  ss.selectList( "book.search", map );
					
					String[][] doublelist = new String[1][ columnNames.length ];
					int index = 0;
					for( BookVO vo : list ) {
						doublelist[ index ][0] = vo.getB_id();
						doublelist[ index ][1] = vo.getB_name();
						doublelist[ index ][2] = vo.getB_price();
						doublelist[ index ][3] = vo.getB_isbn();
						doublelist[ index ][4] = vo.getA_id();
						index++;
					}
					
					table.setModel( new DefaultTableModel( doublelist, columnNames ) );
					
					if( ss != null ) ss.close();
					
				}
			}
		});
		
		table.addMouseListener( new MouseAdapter() {

			@Override public void mousePressed(MouseEvent e) {
				int clickCount = e.getClickCount();
				
				if( clickCount == 2 ) {
					JPanel addRecordPane = new JPanel( new GridLayout( 5, 2 ) );
					
					JLabel idLabel, titleLabel, priceLabel, isbnLabel, a_idLabel;
					JTextField idTextfield, titleTextfield, priceTextfield, isbnTextfield, a_idTextfield;
					
					int selectedRowCount = table.getSelectedRow();
					
					String b_id = (String) table.getValueAt( selectedRowCount, 0 );
					String name = (String) table.getValueAt( selectedRowCount, 1 );
					String price = (String) table.getValueAt( selectedRowCount, 2 );
					String isbn = (String) table.getValueAt( selectedRowCount, 3 );
					String a_id = (String) table.getValueAt( selectedRowCount, 4 );
					
					addRecordPane.add( idLabel = new JLabel( "Id : " ) );
					addRecordPane.add( idTextfield = new JTextField( 10 ) );
					addRecordPane.add( titleLabel = new JLabel( "Title : " ) );
					addRecordPane.add( titleTextfield = new JTextField( 10 ) );
					addRecordPane.add( priceLabel = new JLabel( "Price : " ) );
					addRecordPane.add( priceTextfield = new JTextField( 10 ) );
					addRecordPane.add( isbnLabel = new JLabel( "ISBN : " ) );
					addRecordPane.add( isbnTextfield = new JTextField( 10 ) );
					addRecordPane.add( a_idLabel = new JLabel( "Author Id : " ) );
					addRecordPane.add( a_idTextfield = new JTextField( 10 ) );
					
					idTextfield.setText( b_id );
					titleTextfield.setText( name );
					priceTextfield.setText( price );
					isbnTextfield.setText( isbn );
					a_idTextfield.setText( a_id );
					
					idTextfield.setEditable( false );
					a_idTextfield.setEditable( false );
					
					int optionIndex = 
							JOptionPane.showConfirmDialog( null, addRecordPane, 
									"Add book info", JOptionPane.PLAIN_MESSAGE );
					
					if( optionIndex != JOptionPane.YES_OPTION ) {
						return;
					}
					
					String bid = b_id; 
					String bname = titleTextfield.getText().trim();
					String bprice = priceTextfield.getText().trim();
					String bisbn = isbnTextfield.getText().trim();
					
					Map<String, String> map = new HashMap<>();
					map.put( "b_id", bid );
					if( bname != name ) map.put( "b_name", bname );
					if( bprice != price ) map.put( "b_price", bprice );
					if( bisbn != isbn ) map.put( "b_isbn", bisbn );
					
					if( map.isEmpty() ) {
						return;
					}
					
					SqlSession ss = factory.openSession( true );
					
					ss.update( "book.modify", map );
					
					bottomMsgLabel.setText( "success to modify" );
					
					if( ss != null ) ss.close();
					
					/* ***********************
					 *  check result of insert
					 */
					ss = factory.openSession();
					
					Map<String, String> mapObj = new HashMap<>();
					mapObj.put( "search_type", "0");
					mapObj.put( "searchValue", bid );
					
					
					List<BookVO> list =  ss.selectList( "book.search", mapObj );
					
					String[][] doublelist = new String[ list.size() ][ columnNames.length ];
					int index = 0;
					for( BookVO vo : list ) {
						doublelist[ index ][0] = vo.getB_id();
						doublelist[ index ][1] = vo.getB_name();
						doublelist[ index ][2] = vo.getB_price();
						doublelist[ index ][3] = vo.getB_isbn();
						doublelist[ index ][4] = vo.getA_id();
						index++;
					}
					
					
					table.setModel( new DefaultTableModel( doublelist, columnNames ) );
					
					if( ss != null ) ss.close();
				}
				
				
			}
			
		});
		
		addWindowListener( new WindowAdapter() {
			
			@Override public void windowClosing(WindowEvent e) {
				
				System.exit( 0 );
			}
		});
		
		exit_file.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				System.exit( 0 );
			}
		});
		
	}

	private void searchWord() {
		int comboIndex = combo.getSelectedIndex();
		
		String keyword = wordTextfield.getText().trim();
		
		if( keyword.length() < 1 ) {
			wordTextfield.setText( "" );
			wordTextfield.requestFocus();
			return;
		}
		
		Map<String, String> map = new HashMap<>();
		
		map.put( "search_type", String.valueOf( comboIndex ) );
		map.put( "searchValue", keyword );
		
		SqlSession ss = factory.openSession();
		
		List<BookVO> list = ss.selectList( "book.search", map );
		
		if( list.size() < 1 ) {
			JOptionPane.showMessageDialog( null, "There is not data", 
							"Check this message.", JOptionPane.OK_OPTION );
			bottomMsgLabel.setText( "There is not `" + wordTextfield.getText() + "` " + combo.getItemAt( comboIndex ) );
			wordTextfield.requestFocus();
			return;
			
		}
		
		bottomMsgLabel.setText( "find " + list.size() + " records");
		
		String[][] resultOfSelet = new String[ list.size() ][ columnNames.length ];
		
		
		
		int index = 0;
		for( BookVO vo : list ) {
			System.out.println( vo.toString() );
			resultOfSelet[ index ][ 0 ] = vo.getB_id();
			resultOfSelet[ index ][ 1 ] = vo.getB_name();
			resultOfSelet[ index ][ 2 ] = vo.getB_price();
			resultOfSelet[ index ][ 3 ] = vo.getB_isbn();
			resultOfSelet[ index ][ 4 ] = vo.getA_id();
			index++;
		}
		
		table.setModel( new DefaultTableModel( resultOfSelet, columnNames ) );
		
		if( ss != null ) ss.close();
		
	}
	
	private void getSqlFactory() {
		
		Reader r = null;
		
		try {
			r = Resources.getResourceAsReader( "advance_books/config/config.xml" );
			factory = new SqlSessionFactoryBuilder().build( r );
			System.out.println( "Database is accessed!!");
		} catch (IOException ioe) {
			System.out.println( "Database is not accessed!!");
			ioe.printStackTrace();
		} finally {
				try {
					if( r != null ) r.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
		}
	}

	private void setNorthPane() {
		mainPane.add( northPane = new JPanel( new BorderLayout() ), BorderLayout.NORTH );
		
		northPane.add( combo = new JComboBox<String>(), BorderLayout.WEST );
		
		combo.setModel( new DefaultComboBoxModel<String>( columnNames ));
		combo.setBackground( Color.WHITE );
		
		northPane.add( wordTextfield = new JTextField( 10 ) );
		northPane.add( searchBtn = new JButton( "SEARCH" ), BorderLayout.EAST );
		
		wordTextfield.setFont( new Font( "serif", Font.BOLD, 16 ) );
	}
	
	private void setCenterPane() {
		
		// AbstactTableModel override
//		table = new JTable( new CustomTableModel( null, columnNames ) );
		
		// DefaultTableModel
		table = new JTable( new DefaultTableModel( null, columnNames ) );
		
		mainPane.add( new JScrollPane( table ) );
		
	}
	
	private void setSouthPane() {
		mainPane.add( southPane = new JPanel( new BorderLayout() ), BorderLayout.SOUTH );
		southPane.add( bottomMsgLabel = new JLabel( "Welcome to book infomation system."), BorderLayout.WEST );
		
	}

	private void setMainMenubar() {
		bar = new JMenuBar();
		bar.add( file_mnu = new JMenu( "File" ) );
		
		file_mnu.add( add_file = new JMenuItem( "Add File" ) );
		file_mnu.add( exit_file = new JMenuItem( "Exit" ) );
		
		setJMenuBar( bar );
		
	}

	public static void main(String[] args) {
		new Book_JFrame();
	}

}
