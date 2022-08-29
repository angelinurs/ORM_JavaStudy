package pm.client;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Reader;
import java.util.List;

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

import pm.vo.ChiefVO;
import pm.vo.DiviVO;

public class DeptFrame extends JFrame {

	SqlSessionFactory factory;
	
	JTable table;
	List<DiviVO> list;
	
	String[] c_name = {"부서코드", "부서명"};
	public DeptFrame() {
		this.add(new JScrollPane(table = new JTable(new DefaultTableModel(null, c_name))));
		
		this.setBounds(300, 200, 300, 400);
		this.setVisible(true);
		
		dbCon();//db연결
		deptList();// 부서 전체보기
		
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
				// 클릭한 수를 알아낸다.
				int cnt = e.getClickCount();
				
				if(cnt == 2) {
					//더블클릭한 경우 선택된 index값(행번호)을 얻어낸다.
					int idx = table.getSelectedRow();
					
					//모든 부서정보는 이미 list에 담겨있다. 앞서 구한 index를 이용하여
					//list로부터 부서정보(DiviVO)를 얻어낸다.
					DiviVO dvo = list.get(idx);
					List<ChiefVO> c_list = dvo.getC_list();
					//화면 작업
					JPanel p = new JPanel(new GridLayout(1, 1));
					String[] names = {"사번","이름","근무시작일","근무종료일"};
					String[][] data2 = new String[c_list.size()][names.length];
					
					int i=0;
					for(ChiefVO cvo : c_list) {
						data2[i][0] = cvo.getEmp_no();
						data2[i][1] = cvo.getFirst_name();
						data2[i][2] = cvo.getFrom_date();
						data2[i][3] = cvo.getTo_date();
						i++;
					}
					
					JTable t = new JTable(data2, names);
					p.add(new JScrollPane(t));
					
					JOptionPane.showConfirmDialog(DeptFrame.this, p);
				}
			}
		});
	}
	
	private void deptList() {
		SqlSession ss = factory.openSession();
		list = ss.selectList("division.all");
		viewTable();
		if(ss != null)
			ss.close();
	}
	
	private void viewTable() {
		String[][] data = new String[list.size()][c_name.length];
		int i=0;
		for(DiviVO vo : list) {
			data[i][0] = vo.getDept_no();
			data[i][1] = vo.getDept_name();
			i++;
		}
		table.setModel(new DefaultTableModel(data, c_name));
		
	}
	
	private void dbCon() {
		try {
			Reader r = Resources.getResourceAsReader("pm/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();
			this.setTitle("준비완료");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		// 프로그램 시작
		new DeptFrame();
	}

}
