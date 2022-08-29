package am;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
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

import am.vo.EmpVO;

public class EmpFrame extends JFrame {

	JTable table;
	JPanel south_p;
	
	JMenuBar bar;
	JMenu file_m;
	JMenuItem total_item, search_item, exit_item;
	String[] c_name = {"번호", "사번", "이름", "생일", "입사일"};
	
	//DB관련 객체
	SqlSessionFactory factory;
	
	//페이징 기법에 사용될 변수들
	int begin = 1;
	int end = 30;
	int pagePerBlock = 100000;// 한 페이지에 보여질 항목 수
	int cPage = 1;//현재 페이지
	int totalPage = 5;//총 페이지
	
	JButton[] bt_ar;
	
	public EmpFrame() {
		table = new JTable(new DefaultTableModel(null, c_name));
		this.add(new JScrollPane(table));
		
		dbConnected();// DB연결 - SqlSessionFactory를 만든다.
		setTotalPage();//전체페이지 값을 얻어낸다.
		
		south_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bt_ar = new JButton[totalPage];//필요한 버튼 갯수만큼 배열 생성(JButton을 만든 것은 아닌다.)
		
		for(int i=0; i<totalPage; i++) {
			String num = String.valueOf(i+1);
			
			south_p.add(bt_ar[i] = new JButton(num));//JButton을 만들면서 JPanel에 추가
			
			//만든 JButton에게 이벤트를 지정한다.
			bt_ar[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// 클릭한 버튼의 Text값을 얻어낸다.
					String value = e.getActionCommand();
					
					// value가 "1"이면 begin에는 1, end에는 30
					// value가 "2"이면 begin에는 31, end에는 60
					// value가 "3"이면 begin에는 61, end에는 90
					// value가 "4"이면 begin에는 91, end에는 120
					// value가 "5"이면 begin에는 121, end에는 150
					cPage = Integer.parseInt(value);// 1 , 2, 3 ,.... : 페이지 번호값
					begin = (cPage-1)*pagePerBlock+1;
					end = begin+(pagePerBlock-1);
					
					total();
				}
			});
		}
		
		this.add(south_p, BorderLayout.SOUTH);
		
		//메뉴 작업
		bar = new JMenuBar();
		file_m = new JMenu("파일");
		total_item = new JMenuItem("전체보기");
		search_item = new JMenuItem("검색...");
		exit_item = new JMenuItem("종료");
		
		file_m.add(total_item);
		file_m.add(search_item);
		file_m.addSeparator();
		file_m.add(exit_item);
		
		bar.add(file_m);
		
		this.setJMenuBar(bar);//현재 창에 메뉴바 설정
		
		this.setBounds(300, 200, 600, 500);
		this.setVisible(true);
		
		//이벤트 감지자 등록
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);// 프로그램 종료
			}
		});
		
		total_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				total(); //전체보기 기능 호출
			}
		});
		
		search_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 검색... 선택 시
				
				//화면 작업
				JPanel p = new JPanel(new GridLayout(3, 2));
				JTextField empno_tf = new JTextField(12);
				JTextField name_tf = new JTextField(12);
				JTextField hdate_tf = new JTextField(12);
				
				p.add(new JLabel("사번 :"));
				p.add(empno_tf);
				p.add(new JLabel("이름 :"));
				p.add(name_tf);
				p.add(new JLabel("입사일 :"));
				p.add(hdate_tf);
				
				int cmd = JOptionPane.showConfirmDialog(
					EmpFrame.this, p, "검색", JOptionPane.YES_NO_OPTION);
				
				// cmd의 값이 JOptionPane.YES_OPTION일 때만 검색되어야 한다.
				//if(cmd != JOptionPane.YES_OPTION) 
				//	return;
				if(cmd == JOptionPane.YES_OPTION) {
					Map<String, String> map = new HashMap<String, String>();
					
					// 사용자가 검색하기 위해 입력한 값들을 얻어낸다.
					String empno = empno_tf.getText().trim();
					if(empno != null && empno.length() > 0)
						map.put("empno", empno);
					
					String name = name_tf.getText().trim();
					if(name.length() > 0)
						map.put("name", name);
					
					String hdate = hdate_tf.getText().trim();
					if(hdate.length() > 0)
						map.put("hdate", hdate);
					
					SqlSession ss = factory.openSession();
					List<EmpVO> list = ss.selectList("emp.search", map);
					
					String[][] data = new String[list.size()][c_name.length];
					int i=0;
					for(EmpVO vo : list) {
						data[i][0] = String.valueOf(vo.getRownum());// 행번호
						data[i][1] = vo.getEmp_no();// 사번
						data[i][2] = vo.getFirst_name();// 이름
						data[i][3] = vo.getBirth_date();// 생일
						data[i][4] = vo.getHire_date();// 입사일
						i++;
					}
					
					table.setModel(new DefaultTableModel(data, c_name));//JTable에 넣어서 표현하기
					
					if(ss != null)
						ss.close();
				}
				
				
			}
		});
	}
	
	private void dbConnected() {
		try {
			Reader r = Resources.getResourceAsReader("am/config/config.xml");//환경설정 파일(config.xml) 경로
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
			this.setTitle("연결완료!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//전체페이지 값을 설정
	private void setTotalPage() {
		SqlSession ss = factory.openSession();
		
		//총 레코드 수
		int totalCount = ss.selectOne("emp.totalCount");
		
		//총 레코드 수를 받았으니 이제 총 페이지 수를 구한다.
		//totalPage = totalCount/pagePerBlock;
		
		// 나머지가 조금이라도 있을 때 totalPage+1을 한다.
		//if(totalCount%pagePerBlock != 0)
		//	totalPage++;
		
		totalPage = (int) Math.ceil((double)totalCount/pagePerBlock);
		// ceil함수는 소수점 이하가 있으면 무조건 올림을 한다. 예) 3.1 => 4.0
		
		if(ss != null)
			ss.close();
	}
	
	//전체보기 기능
	private void total() {
		
		for(int i=0; i<bt_ar.length; i++) {
			if(i+1 == cPage) //현재 페이지 알아내기
				bt_ar[i].setForeground(Color.red);
			else
				bt_ar[i].setForeground(Color.black);
		}
		
		SqlSession ss = factory.openSession();// **********************
		
		//전체보기 emp.all을 호출하여 넘어온 결과(List<EmpVO>)를 2차원 배열로 만든다.
		//List<EmpVO> list = ss.selectList("emp.all");
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("begin", begin);
		map.put("end", end);
		
		List<EmpVO> list = ss.selectList("emp.total", map);
		
		String[][] data = new String[list.size()][c_name.length];
		int i=0;
		for(EmpVO vo : list) {
			data[i][0] = String.valueOf(vo.getRownum());// 행번호
			data[i][1] = vo.getEmp_no();// 사번
			data[i][2] = vo.getFirst_name();// 이름
			data[i][3] = vo.getBirth_date();// 생일
			data[i][4] = vo.getHire_date();// 입사일
			i++;
		}
		
		table.setModel(new DefaultTableModel(data, c_name));//JTable에 넣어서 표현하기
		
		if(ss != null)
			ss.close();
	}
	
	public static void main(String[] args) {
		// 프로그램 시작
		new EmpFrame();
	}

}
