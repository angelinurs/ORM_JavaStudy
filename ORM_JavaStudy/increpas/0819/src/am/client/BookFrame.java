package am.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import am.vo.BookVO;

public class BookFrame extends JFrame {

	JMenuBar bar;
	JMenu file_m;
	JMenuItem add_item, exit_item;
	
	JPanel north_p;
	JComboBox<String> combo;
	JTextField word_tf;
	JButton search_bt;
	
	JTable table;
	String[] c_name = {"아이디", "제목", "저자" , "출판사", "가격"};
	
	SqlSessionFactory factory;
	
	public BookFrame() {
		bar = new JMenuBar();
		file_m = new JMenu("파일");
		add_item = new JMenuItem("추가");
		exit_item = new JMenuItem("종료");
		
		file_m.add(add_item);
		file_m.addSeparator();;
		file_m.add(exit_item);
		
		bar.add(file_m);
		
		this.setJMenuBar(bar);
		
		north_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		north_p.add(combo = new JComboBox<String>());
		String[] txt_ar = {"아이디","제목","저자","가격"};
		combo.setModel(new DefaultComboBoxModel<String>(txt_ar));
		
		north_p.add(word_tf = new JTextField(12));
		word_tf.setFont(new Font("serif", Font.BOLD, 16));
		
		north_p.add(search_bt = new JButton("검색"));
		
		this.add(north_p, BorderLayout.NORTH);
		
		this.add(new JScrollPane(table = new JTable()));
		table.setModel(new DefaultTableModel(null, c_name));
		
		this.setBounds(300, 200, 500, 450);
		this.setVisible(true);
		
		dbConnected(); //DB연결!
		
		//이벤트 감지자 등록
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// 프로그램 종료
				System.exit(0);
			}
		});
		
		search_bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 검색 버튼을 클릭했을 때
				search();
				
			}
		});
		word_tf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// word_tf에서 엔터쳤을 때
				search();
				
			}
		});
		
		add_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 추가 메뉴아이템 클릭시
				JPanel p = new JPanel(new GridLayout(4, 2));
				p.add(new JLabel("제목:"));
				JTextField title_tf = new JTextField(12);
				p.add(title_tf);
				
				p.add(new JLabel("저자:"));
				JTextField author_tf = new JTextField(12);
				p.add(author_tf);
				
				p.add(new JLabel("출판사:"));
				JTextField press_tf = new JTextField(12);
				p.add(press_tf);
				
				p.add(new JLabel("가격:"));
				JTextField price_tf = new JTextField(12);
				p.add(price_tf);
				
				int cmd = JOptionPane.showConfirmDialog(null, p, "추가", JOptionPane.YES_NO_OPTION);
				
				//사용자가 Yes버튼을 클릭했을 때를 구별하여 저장을 수행한다.
				if(cmd == JOptionPane.YES_OPTION) {
					// 사용자가 입력한 제목, 저자, 출판사, 가격 정보를 받아서
					// 하나의 객체로 만든 후 저장하는 Mapper를 호출한다.
					String title = title_tf.getText().trim();
					String author = author_tf.getText().trim();
					String press = press_tf.getText().trim();
					String price = price_tf.getText().trim();
					
					BookVO vo = new BookVO();
					vo.setB_title(title);
					vo.setB_author(author);
					vo.setB_press(press);
					vo.setB_price(price);
					
					SqlSession ss = factory.openSession();
					int cnt = ss.insert("book.add", vo);
					if(cnt > 0) {
						ss.commit();// DB에 적용!
						JOptionPane.showMessageDialog(BookFrame.this, "저장 완료");
					}
					if(ss != null)
						ss.close();
				}
			}
		});
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// 더블클릭을 알아낸다.
				int cnt = e.getClickCount();
				if(cnt == 2) {
					//더블 클릭했을 때!
					// 선택된 행을 얻어낸다.
					int row = table.getSelectedRow();
					
					String b_id = (String) table.getValueAt(row, 0);
					String b_title = (String) table.getValueAt(row, 1);
					String b_author = (String) table.getValueAt(row, 2);
					String b_press = (String) table.getValueAt(row, 3);
					String b_price = (String) table.getValueAt(row, 4);
					
					//창을 만든후 원하는 곳에 넣어 표현한다.
					JPanel p = new JPanel(new GridLayout(5, 2));
					p.add(new JLabel("아이디:"));
					p.add(new JLabel(b_id)); // 기본키는 수정을 하면안된다.
					
					p.add(new JLabel("제목:"));
					JTextField title_tf = new JTextField(12);
					title_tf.setText(b_title);
					p.add(title_tf);
					
					p.add(new JLabel("저자:"));
					JTextField author_tf = new JTextField(12);
					author_tf.setText(b_author);
					p.add(author_tf);
					
					p.add(new JLabel("출판사:"));
					JTextField press_tf = new JTextField(12);
					press_tf.setText(b_press);
					p.add(press_tf);
					
					p.add(new JLabel("가격:"));
					JTextField price_tf = new JTextField(12);
					price_tf.setText(b_price);
					p.add(price_tf);
					
					int cmd = JOptionPane.showConfirmDialog(null, p, "상세보기", JOptionPane.YES_NO_OPTION);
					if(cmd == JOptionPane.YES_OPTION) {
						// 수정 작업을 하기 전에 사용자가 어떤 값을 변경했는지?
						// 확인하기가 어려울 때가 많다. 일단 모두 가져온다.
						String title = title_tf.getText().trim();
						String author = author_tf.getText().trim();
						String press = press_tf.getText().trim();
						String price = price_tf.getText().trim();
						
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", b_id);// 수정하기 위한 조건값!
						
						if(!b_title.equals(title)) //원래 제목과 다를경우
							map.put("title", title);// map에 title이라는 이름으로 저장!
						
						if(!b_author.equals(author))
							map.put("author", author);
						
						if(!b_press.equals(press))
							map.put("press", press);
						
						if(!b_price.equals(price))
							map.put("price", price);
						
						SqlSession ss = factory.openSession();
						int count = ss.update("book.edit", map);
						
						if(count > 0) {
							ss.commit();// DB적용
							
							table.setValueAt(title, row, 1);
							table.setValueAt(author, row, 2);
							table.setValueAt(press, row, 3);
							table.setValueAt(price, row, 4);
							
							JOptionPane.showMessageDialog(null, "변경완료");
						}
						
						if(ss != null)
							ss.close();
					}
				}
			}
		});
	}
	
	private void search() {
		// 콤보상자에서 선택된 인덱스를 얻어낸다. : searchType
		int idx = combo.getSelectedIndex();
		
		// 사용자가 입력한 검색단어를 얻어낸다. : searchValue
		String word = word_tf.getText().trim();
		
		// search라는 맵퍼에 전달할 파라미터인 Map구조 생성
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchType", String.valueOf(idx));
		map.put("searchValue", word);
		
		// book.search라는 맵퍼 호출
		SqlSession ss = factory.openSession();
		
		List<BookVO> list = ss.selectList("book.search", map);
		//if(list.size() > 0) {
			//JTable에 표현할 2차원 배열 생성!
			String[][] data = new String[list.size()][c_name.length];
			int i = 0;
			for(BookVO vo : list) {
				data[i][0] = vo.getB_id();//도서ID
				data[i][1] = vo.getB_title();//도서 제목
				data[i][2] = vo.getB_author();//저자
				data[i][3] = vo.getB_press();//출판사
				data[i][4] = vo.getB_price();//도서 가격
				i++;//다음 도서 정보를 가져오기 위해 i값 증가!
			}//for문의 끝
			
			table.setModel(new DefaultTableModel(data, c_name));
		//}
		if(ss != null)
			ss.close();
	}
	
	private void dbConnected() {
		try {
			Reader r = Resources.getResourceAsReader("am/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
			this.setTitle("준비완료!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// 프로그램 시작
		new BookFrame();
	}

}







