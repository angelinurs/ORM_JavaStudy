package pm.client;

import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pm.vo.BuseoVO;
import pm.vo.SawonVO;

public class DeptFrame extends JFrame {

	JTable table;
	String[] c_name = {"부서코드", "부서명", "부장"};
	List<BuseoVO> list;
	
	SqlSessionFactory factory;
	
	public DeptFrame() {
		table = new JTable(new DefaultTableModel(null, c_name));
		this.add(new JScrollPane(table));
		
		this.setBounds(300, 200, 400, 450);
		this.setVisible(true);
		
		dbConnected();
		deptAll();// 부서 전체보기
		
		//이벤트 감지자 등록
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);//프로그램 종료
			}
		});
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int cnt = e.getClickCount();
				
				if(cnt == 2) {
					//더블클릭한 경우!
					viewDialog();
				}
			}
		});
	}
	
	//데이터베이스 연결
	private void dbConnected() {
		try {
			Reader r = Resources.getResourceAsReader("pm/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
			this.setTitle("준비완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//더블클릭시 해당부서의 부서장 정보를 보여주는 기능
	private void viewDialog() {
		//사용자가 더블클릭한 인덱스값을 얻어낸다.
		int idx = table.getSelectedRow();
		//System.out.println(idx);
		
		BuseoVO vo = list.get(idx);// 사용자가 JTable에서 더블클릭한 위치의 부서정보
		//System.out.println(vo.getDept_name());
		SawonVO svo = vo.getSvo();
		
		//vo를 가지고 대화창 화면을 만든다.
		JPanel p = new JPanel(new GridLayout(4, 2));
		
		//JTextField empno_tf = new JTextField(12);
		JTextField name_tf = new JTextField(12);
		JTextField gender_tf = new JTextField(12);
		JTextField hdate_tf = new JTextField(12);
		
		p.add(new JLabel("사번 :"));
		p.add(new JLabel(svo.getEmp_no()));
		p.add(new JLabel("이름 :"));
		p.add(name_tf);
		name_tf.setText(svo.getFirst_name());
		
		p.add(new JLabel("성별 :"));
		p.add(gender_tf);
		gender_tf.setText(svo.getGender());
		
		p.add(new JLabel("입사일 :"));
		p.add(hdate_tf);
		hdate_tf.setText(svo.getHire_date());
		
		JOptionPane.showConfirmDialog(
			this, p, vo.getDept_name(), JOptionPane.YES_NO_OPTION);
	}
	
	//부서 전체보기
	private void deptAll() {
		SqlSession ss = factory.openSession();
		
		list = ss.selectList("dept.all");
		viewTable();
		
		if(ss != null)
			ss.close();
	}
	
	private void viewTable() {
		String[][] ar = new String[list.size()][c_name.length];
		
		int i=0;
		for(BuseoVO vo : list) {
			ar[i][0] = vo.getDept_no();
			ar[i][1] = vo.getDept_name();
			ar[i][2] = vo.getSvo().getEmp_no();
			i++;
		}
		table.setModel(new DefaultTableModel(ar, c_name));
	}
	
	public static void main(String[] args) {
		new DeptFrame();
	}

}








