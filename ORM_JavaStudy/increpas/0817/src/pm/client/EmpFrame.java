package pm.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.HashMap;
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

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pm.vo.EmpVO;

public class EmpFrame extends JFrame {

	JTable table;
	
	JMenuBar bar;
	JMenu search_m;
	JMenuItem total_item, searchName_item, exit_item;
	JMenuItem empno_item, hdate_item;

	//JTable에서 표현할 컬럼명들
	String[] c_name = {"사번","이름", "성씨", "생일", "입사일"};
	
	private SqlSessionFactory factory;
	
	public EmpFrame() {
		bar = new JMenuBar();
		
		search_m = new JMenu("검색");
		total_item = new JMenuItem("전체보기");
		searchName_item = new JMenuItem("이름검색...");
		exit_item = new JMenuItem("종료");
		
		empno_item = new JMenuItem("사번검색...");
		hdate_item = new JMenuItem("입사일검색...");
		
		search_m.add(total_item);
		search_m.add(searchName_item);
		search_m.add(empno_item);
		search_m.add(hdate_item);
		search_m.addSeparator();
		search_m.add(exit_item);
		
		//JMenu를 JMenuBar에 추가한다.
		bar.add(search_m);
		
		//현재 창에 메뉴바 설정
		this.setJMenuBar(bar);
		
		this.add(new JScrollPane(table = new JTable()));//화면 가운데에 넣기
		table.setModel(new MyModel(null, c_name));
		
		this.setBounds(300, 200, 600, 500);
		this.setVisible(true);
		
		dbConnect();//db연결 ***************************
		
		//이벤트 감지자 등록
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// 프로그램 종료
				System.exit(0);
			}
		});
		
		total_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 전체보기를 선택했을 때
				setTotal();
			}
		});
		
		searchName_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 이름검색을 선택했을 때
				searchName();
			}
		});
		
		empno_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 사번 검색을 선택했을 때
				searchEmpno();				
			}
		});
		
		hdate_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 입사일 검색을 선택했을 때
				JPanel p1 = new JPanel(new GridLayout(2, 3));
				
				JTextField begin_txt = new JTextField(10);
				JTextField end_txt = new JTextField(10);
				
				p1.add(new JLabel("시작:"));
				p1.add(begin_txt);
				p1.add(new JLabel("종료:"));
				p1.add(end_txt);
				
				int cmd = JOptionPane.showConfirmDialog(null, p1, "검색", JOptionPane.YES_NO_OPTION);
				if(cmd == JOptionPane.YES_OPTION) {
					String begin_str = begin_txt.getText().trim();
					String end_str = end_txt.getText().trim();
					
					//System.out.println(begin_str+"/"+end_str);
					
					searchHireDate(begin_str, end_str);
				}
			}
		});
	}
	
	private void dbConnect() {
		try {
			Reader r = Resources.getResourceAsReader("pm/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void searchHireDate(String begin, String end) {
		//인자로 받은 begin과 end를 Map구조에 저장한다.
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("begin", begin);
		map.put("end", end);
		
		// emp.search_hire_date라는 mapper를 호출하기 위해 위의 map을 준비했다.
		SqlSession ss = factory.openSession();
		
		List<EmpVO> list = ss.selectList("emp.search_hire_date", map);
		
		String[][] data = new String[list.size()][c_name.length];
		
		int i=0;
		for(EmpVO vo : list) {
			data[i][0] = vo.getEmp_no();//사번
			data[i][1] = vo.getFirst_name();//이름
			data[i][2] = vo.getLast_name();//성씨
			data[i][3] = vo.getBirth_date();//생일
			data[i][4] = vo.getHire_date();//입사일
			i++;
		}
		
		DecimalFormat df = new DecimalFormat("#,###");
		String cnt = df.format(list.size());
		this.setTitle("총 "+cnt+"건 검색");
		
		table.setModel(new MyModel(data, c_name));
		
		if(ss != null)
			ss.close();
	}
	
	private void searchEmpno() {
		String no = JOptionPane.showInputDialog("검색할 사번을 입력하세요");
		
		if(no == null || no.trim().length() < 1)
			return;
		
		SqlSession ss = factory.openSession();
		//사번 검색은 있다면 오로지 하나만 넘어오기 때문에 EmpVO로 받는다.
		
		EmpVO vo = ss.selectOne("emp.search_empno", no);
		
		if(vo == null)
			JOptionPane.showMessageDialog(this, "검색된 정보가 없습니다.");
		else {
			//검색된 자원이 있는 경우!
			String[][] data = new String[1][c_name.length];
			data[0][0] = vo.getEmp_no();
			data[0][1] = vo.getFirst_name();
			data[0][2] = vo.getLast_name();
			data[0][3] = vo.getBirth_date();
			data[0][4] = vo.getHire_date();
			
			table.setModel(new MyModel(data, c_name));
			
			this.setTitle("총 1건 검색");
			
		}
		if(ss != null)
			ss.close();
	}
	
	private void searchName() {
		String name = JOptionPane.showInputDialog("검색할 이름을 입력하세요");
		//사용자가 검색하기 위해 입력한 검색어가 name에 저장된다.
		
		if(name == null || name.trim().length() < 1)
			return;
		
		SqlSession ss = factory.openSession();
		List<EmpVO> list = ss.selectList("emp.search_name", name);
		
		//위에서 받은 list를 2차원배열로 만든다.
		String[][] data = new String[list.size()][c_name.length];
		
		int i=0;
		for(EmpVO vo:list) {
			data[i][0] = vo.getEmp_no();//사번
			data[i][1] = vo.getFirst_name();
			data[i][2] = vo.getLast_name();
			data[i][3] = vo.getBirth_date();
			data[i][4] = vo.getHire_date();
			i++;
		}
		
		table.setModel(new MyModel(data, c_name));
		DecimalFormat df = new DecimalFormat("#,###");
		String cnt = df.format(list.size());
		this.setTitle("총 "+cnt+"건 검색");
		
		if(ss != null)
			ss.close();
	}
	
	private void setTotal() {
		SqlSession ss = factory.openSession();
		List<EmpVO> list = ss.selectList("emp.total");
		
		//위에서 받은 list를 2차원배열로 만든다.
		String[][] data = new String[list.size()][c_name.length];
		int i = 0;
		for(EmpVO vo : list) {
			data[i][0] = vo.getEmp_no(); //사번
			data[i][1] = vo.getFirst_name(); //이름
			data[i][2] = vo.getLast_name(); //성씨
			data[i][3] = vo.getBirth_date(); //생일			
			data[i][4] = vo.getHire_date(); //입사일
			i++;
		}
		
		table.setModel(new MyModel(data, c_name));// JTable 내용이 변경된다.
		//3자리 콤마 찍기
		DecimalFormat df = new DecimalFormat("#,###");
		String cnt = df.format(list.size());
		this.setTitle("총 "+cnt+"건 검색");
		
		if(ss != null)
			ss.close();
	}
	
	public static void main(String[] args) {
		//프로그램 시작
		new EmpFrame();
	}

}
