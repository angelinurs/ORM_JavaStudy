package am.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Reader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.EmpVO;
import am.vo.SalVO;

public class DeptFrame extends JFrame{

	JPanel north_p;
	JButton bt1, bt2;
	JTable table;
	String[] c_name = {"사번", "이름", "입사일"};
	
	SqlSessionFactory factory;
	List<EmpVO> list;
	
	public DeptFrame() {
		
		north_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		north_p.add(bt1 = new JButton("Marketing"));
		north_p.add(bt2 = new JButton("Development"));
		this.add(north_p, BorderLayout.NORTH);
		
		table = new JTable(new DefaultTableModel(null, c_name));
		this.add(new JScrollPane(table));
		
		this.setBounds(300, 200, 300, 400);
		this.setVisible(true);
		
		dbConn();//데이터베이스 연결
		
		//이벤트 감지자 등록
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);//프로그램 종료
			}
		});
		
		bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				total("d001");//Marketing
			}
		});
		bt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				total("d005");//Development
			}
		});
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int cnt = e.getClickCount();
				
				if(cnt == 2) {
					//JTable에서 더블클릭한 경우
					// 더블클릭해서 선택된 행 번호 얻기
					int rownum = table.getSelectedRow();
					
					//얻어낸 행번호를 가지고 선택된 사원의 정보를 list에서 
					//가져온다.
					EmpVO evo = list.get(rownum);
					
					//해당 사원이 지금까지 받아왔던 급여스토리를 얻어낸다.
					List<SalVO> s_list = evo.getS_list();
					
					String[] cname = {"사번","이름","급여","근무시작일","근무종료일"};
					String[][] data = new String[s_list.size()][cname.length];
					
					int i=0;
					for(SalVO svo : s_list) {
						data[i][0] = svo.getEmp_no();
						data[i][1] = evo.getFirst_name();
						data[i][2] = svo.getSalary();
						data[i][3] = svo.getFrom_date();
						data[i][4] = svo.getTo_date();
						i++;
					}
					
					JPanel p = new JPanel(new BorderLayout());
					p.add(new JScrollPane(new JTable(data, cname)));
					
					JOptionPane.showConfirmDialog(
							DeptFrame.this, p, evo.getFirst_name(), JOptionPane.YES_NO_OPTION);
				}
			}
		});
	}
	
	private void dbConn() {
		try {
			Reader r = Resources.getResourceAsReader("am/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
			this.setTitle("준비완료");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void total(String deptno) { //부서코드를 받아야 한다.
		SqlSession ss = factory.openSession();
		list = ss.selectList("emp.searchEmp", deptno);//부서별 검색
		viewTable();
		if(ss != null)
			ss.close();
	}
	
	private void viewTable() {
		String[][] data = new String[list.size()][c_name.length];
		int i=0;
		for(EmpVO vo : list) {
			
			data[i][0] = vo.getEmp_no();
			data[i][1] = vo.getFirst_name();
			data[i][2] = vo.getHire_date();
			i++;
		}
		table.setModel(new DefaultTableModel(data, c_name));
	}
	public static void main(String[] args) {
		new DeptFrame();

	}

}
