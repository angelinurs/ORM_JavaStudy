package pm.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Reader;
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

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pm.vo.BookVO;

public class BookFrame extends JFrame {

	JMenuBar bar;
	JMenu search_m;
	JMenuItem total_item, searchId_item, searchBook_item;
	
	JTable table;
	String[] cname = {"아이디","도서명","저자","출판사","가격"};
	
	SqlSessionFactory factory;
	
	public BookFrame() {
		bar = new JMenuBar();
		search_m = new JMenu("검색");
		total_item = new JMenuItem("전체보기");
		searchId_item = new JMenuItem("ID검색...");
		searchBook_item = new JMenuItem("도서및출판사 검색...");
		
		search_m.add(total_item);
		search_m.addSeparator();
		search_m.add(searchId_item);
		search_m.addSeparator();
		search_m.add(searchBook_item);
		
		bar.add(search_m);
		this.setJMenuBar(bar);//현재 창에 메뉴바 설정
		
		table = new JTable(new MyModel(cname, null));
		this.add(new JScrollPane(table));
		
		this.setBounds(300, 200, 600, 500);
		this.setVisible(true);
		
		dbConnected();// DB연결 *****************************
		
		// 각 이벤트 감지자 등록 (창의 종료버튼을 클릭할 때, 전체보기 메뉴아이템을 클릭할 때,....)
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// 프로그램 종료
				System.exit(0);
			}
		});
		
		searchId_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// ID검색을 클릭했을 때
				String str = JOptionPane.showInputDialog("검색할 ID: ");
				//System.out.println(str);
				
				String[] ar = str.split(",");
				if(ar.length < 1) // 콤마만 입력한 경우
					return;
				
				Map<String, String[]> map = new HashMap<String, String[]>();
				map.put("ar", ar);
				
				SqlSession ss = factory.openSession();
				List<BookVO> list = ss.selectList("book.search_id", map);
				
				if(ss != null)
					ss.close();
				if(list.size() > 0) {
					//list를 2차원 배열로 변환한다.
					String[][] data = new String[list.size()][cname.length];
					
					int i=0;
					for(BookVO vo:list) {
						data[i][0] = vo.getB_id();
						data[i][1] = vo.getB_title();
						data[i][2] = vo.getB_author();
						data[i][3] = vo.getB_press();
						data[i][4] = vo.getB_price();
						i++;
					}
					table.setModel(new MyModel(cname, data));
				}
			}
		});
		
		searchBook_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 도서및출판사 검색을 클릭했을 때
				
				JPanel pp = new JPanel(new GridLayout(2, 3));
				pp.add(new JLabel("도서:"));
				JTextField bookName_tf = new JTextField(10);
				pp.add(bookName_tf);
				
				pp.add(new JLabel("출판사:"));
				JTextField press_tf = new JTextField(10);
				pp.add(press_tf);
				
				int cmd = JOptionPane.showConfirmDialog(
						null, pp, "검색", JOptionPane.YES_NO_OPTION);
				
				if(cmd == JOptionPane.YES_OPTION) {
					//사용자가 입력한 값들을 가져온다.
					String bookName = bookName_tf.getText().trim();
					String press = press_tf.getText().trim();
					
					//System.out.println(bookName+"/"+press);
					Map<String, String> m = new HashMap<String, String>();
					
					if(bookName.length() > 0)
						m.put("bookName", bookName);
					
					if(press.length() > 0)
						m.put("press", press);
					
					
				}
			}
		});
		
	}
	
	private void dbConnected() {
		try {
			Reader r = Resources.getResourceAsReader("pm/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
			setTitle("DB연결완료");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		new BookFrame();

	}

}
