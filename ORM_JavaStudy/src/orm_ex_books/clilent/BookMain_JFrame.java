package orm_ex_books.clilent;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
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
import javax.swing.table.TableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import orm_ex_books.vo.BookVO;

public class BookMain_JFrame extends JFrame {
	
	JScrollPane jsPane;
	
	JTable table;
	TableModel model;
	
	JMenuBar bar;
	
	JMenu find_mnu;
	JMenu insert_mnu;
	
	JMenuItem selectAll_item,
			  selectNameAndPrice_item,
			  selectNumber_item;
	
	JMenuItem insert_item;
	
	String[] columns = { "book number", "book name", "book price", "book isbn", "author id" };
	
	SqlSessionFactory factory = null;
	
	public BookMain_JFrame() {
		super( "Book info" );
		
		setMainMenubar();
		setJsPane();
		
		pack();
		
		getSqlSession();
		
		setBounds( 300, 300, 500, 500 );
		setVisible( true );
		
		addWindowListener( new WindowAdapter() {

			@Override public void windowClosing(WindowEvent e) {
				System.exit( 0 );
			}
		});
		
		selectAll_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				
				SqlSession ss = factory.openSession();
				
				List<BookVO> list = ss.selectList( "book.selectAll" );

				runSelectQuery( list );
				
				if( ss != null ) ss.close();
				
			}
		});
		
		selectNumber_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				String inputDiag = JOptionPane.showInputDialog( "도서번호들을 입력하세요." );
				
				if( inputDiag !=null && inputDiag.length() >  0 ) {
					String[] inputList = inputDiag.trim().split( "," );
					
					ArrayList<String> inputArray = new ArrayList<>();
					
					for( String value : inputList ) {
						String item = value.trim();
						
						if( item.length() > 0 ) {
							inputArray.add( value.trim() );
						}
					}
					
					if( inputArray.size() < 1 ) {
						return;
					}
					
					Map< String, ArrayList<String> > map = new HashMap<>();
					map.put( "id_list", inputArray );
					
					
					SqlSession ss = factory.openSession();
					
					List<BookVO> list = ss.selectList( "book.search_id_in", map );
					
					runSelectQuery( list );
					
					if( ss != null ) ss.close();
					
				}
				
			}
		});
		
		insert_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				
				JPanel mainInsertPane = new JPanel( new GridLayout( 3, 1 ) );
				
				JPanel nameInsertPane, priceInsertPane, isbnInsertPane;
				
				JLabel nameLabel, priceLabel, isbnLabel;
				JTextField nameText, priceText, isbnText;
				
				mainInsertPane.add( nameInsertPane = new JPanel() );
				mainInsertPane.add( priceInsertPane = new JPanel() );
				mainInsertPane.add( isbnInsertPane = new JPanel() );
				
				nameInsertPane.add( nameLabel = new JLabel( "도서 제목 : " ) );
				nameInsertPane.add( nameText = new JTextField( 10 ) );
				
				priceInsertPane.add( priceLabel = new JLabel( "도서 가격 : " ) );
				priceInsertPane.add( priceText = new JTextField( 10 ) );
				
				isbnInsertPane.add( isbnLabel = new JLabel( "도서 isbn : " ) );
				isbnInsertPane.add( isbnText = new JTextField( 10 ) );
				
				int val = JOptionPane.showConfirmDialog( null, mainInsertPane );
				
				String name = nameText.getText().trim();
				String price = priceText.getText().trim();
				String isbn = isbnText.getText().trim();
				
				if( val == JOptionPane.YES_OPTION ) {
				
					if( name.length() < 1 || price.length() < 1 || isbn.length() < 1 ) {
						System.out.println( "not allow null data" );
						return;
					}
					
					Map<String, String> map =  new BookVO( name, price, isbn, "1" ).toMap();
					
					SqlSession ss = factory.openSession( true );
					
					ss.insert( "add_map", map );
					
					if( ss != null ) ss.close();
				}
			}
		});
		
		selectNameAndPrice_item.addActionListener( new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				JPanel mainSelectPane = new JPanel( new GridLayout( 2, 1 ) );
				
				JPanel nameSelectPane, priceSelectPane;
				
				JLabel nameSelectLabel, priceSelectLabel;
				JTextField nameSelectText, priceSelectText;
				
				mainSelectPane.add( nameSelectPane = new JPanel() );
				mainSelectPane.add( priceSelectPane = new JPanel() );
				
				nameSelectPane.add( nameSelectLabel = new JLabel( "도서 제목 : " ) );
				nameSelectPane.add( nameSelectText = new JTextField( 10 ) );
				
				priceSelectPane.add( priceSelectLabel = new JLabel( "도서 가격 : " ) );
				priceSelectPane.add( priceSelectText = new JTextField( 10 ) );
				
				int val = JOptionPane.showConfirmDialog( null, mainSelectPane );
				
				String name = nameSelectText.getText().trim();
				String price = priceSelectText.getText().trim();
				
				if( val == JOptionPane.YES_OPTION ) {
				
					Map<String, String> map = new HashMap<>();
					
					if( name.length() > 0 ) {
						map.put( "b_name", name );
					}
					if( price.length() > 0 ) {
						map.put( "b_price", price );
					}
					
					System.out.println( map.toString() );
					
					SqlSession ss = factory.openSession();
					
					List<BookVO> list = ss.selectList( "book.search_name_and_price", map );
					
					runSelectQuery( list );
					
					if( ss != null ) ss.close();
					
				}
			}
		});
	}
	
	private void runSelectQuery( List<BookVO> list ) {
		String[][] doubleList = new String[ list.size() ][ columns.length ];
		
		for( int idx = 0; idx < list.size(); idx++ ) {
			
			BookVO vo  = list.get( idx );
			
			doubleList[ idx ][ 0 ] = vo.getB_id();
			doubleList[ idx ][ 1 ] = vo.getB_name();
			doubleList[ idx ][ 2 ] = vo.getB_price();
			doubleList[ idx ][ 3 ] = vo.getB_isbn();
			doubleList[ idx ][ 4 ] = vo.getA_id();
		}
		
		table.setModel( new BooksModel( doubleList, columns ) );
	}
	
	private void setMainMenubar() {
		bar = new JMenuBar();
		bar.add( find_mnu = new JMenu( "찾기") );
		bar.add( insert_mnu = new JMenu( "도서정보 추가") );
		
		find_mnu.add( selectAll_item = new JMenuItem( "전체보기" ) );
		find_mnu.add( selectNameAndPrice_item = new JMenuItem( "책 이름과 책 가격으로 검색") );
		find_mnu.add( selectNumber_item = new JMenuItem( "책번호로 찾기") );
		
		insert_mnu.add( insert_item = new JMenuItem( "책 추가하기") );
		
		setJMenuBar( bar );
	}

	private void setJsPane() {
		model = new BooksModel( null, columns );
		table = new JTable( model );
		
		add( jsPane = new JScrollPane( table ) );
	}
	
	private int getSqlSession() {
		try {
			Reader r = Resources.getResourceAsReader( "orm_ex_books/config/config.xml" );
			
			factory = new SqlSessionFactoryBuilder().build( r );
			
			r.close();
			
			return 0;
			
		} catch (IOException e) {
			
			return 1;
//			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		new BookMain_JFrame();
	}

}
