package pm.client;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Reader;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pm.vo.ChiefVO;
import pm.vo.DiviVO;
import pm.vo.EmpVO;

public class DeptTest extends JFrame {

	JTable dept_table;
	String[] c_name = {"부서코드", "부서명", "인원수"};
	
	SqlSessionFactory factory;
	List<DiviVO> list;
	
	public DeptTest() {
		dept_table = new JTable(new DefaultTableModel(null, c_name));
		this.add(new JScrollPane(dept_table));
		
		this.setBounds(300, 200, 300, 400);
		this.setVisible(true);
		
		dbConn();//데이터베이스 연결
		total();//전체부서 보기
		
		//이벤트 감지자 등록
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);//프로그램 종료
			}
		});
		
		dept_table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int cnt = e.getClickCount();
				if(cnt == 2) {
					//JTable에서 더블클릭한 경우!
					int idx = dept_table.getSelectedRow();//선택된 행 번호
					
					// 얻어낸 행번호를 가지고 list에서 사용자가 
					// 더블클릭으로 선택한 부서정보를 가져온다.
					DiviVO vo = list.get(idx);
					
					//부서정보안에 cvo라는 부서장 정보와 e_list라는 구성원들을 가지고
					ChiefVO cvo = vo.getCvo();
					
					//화면을 구성하여 표현한다.
					JPanel p = new JPanel(new BorderLayout());
					JLabel cvo_p = new JLabel("부서장: "+cvo.getFirst_name()+"("+cvo.getEmp_no()+")");
					p.add(cvo_p, BorderLayout.NORTH);
					
					List<EmpVO> e_list = vo.getE_list();// 사원들
					String[] cname = {"사번","이름","성별","입사일"};
					String[][] data = new String[e_list.size()][cname.length];
					
					int i=0;
					for(EmpVO evo : e_list) {
						data[i][0] = evo.getEmp_no();//사번
						data[i][1] = evo.getFirst_name();//이름
						data[i][2] = evo.getGender();//성별
						data[i][3] = evo.getHire_date();//입사일
						i++;
					}
					
					JTable table = new JTable(data, cname);
					p.add(new JScrollPane(table));
					
					JOptionPane.showConfirmDialog(
						DeptTest.this, p, vo.getDept_name(), JOptionPane.YES_NO_OPTION);
				}
			}
		});
	}
	
	private void dbConn() {
		try {
			Reader r = Resources.getResourceAsReader("pm/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
			this.setTitle("준비완료");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void total() {
		SqlSession ss = factory.openSession();
		list = ss.selectList("division.all");
		viewTable();
		if(ss != null)
			ss.close();
	}
	
	private void viewTable() {
		String[][] data = new String[list.size()][c_name.length];
		int i=0;
		for(DiviVO dvo : list) {
			List<EmpVO> e_list = dvo.getE_list();//부서에 포함된 사원들 정보
			data[i][0] = dvo.getDept_no();
			data[i][1] = dvo.getDept_name();
			data[i][2] = String.valueOf(e_list.size());
			i++;
		}
		dept_table.setModel(new DefaultTableModel(data, c_name));
	}
	
	public static void main(String[] args) {
		// 프로그램 시작
		new DeptTest();
	}

}
