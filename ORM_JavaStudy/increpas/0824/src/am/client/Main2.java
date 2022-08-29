package am.client;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.DeptEmpVO;
import am.vo.EmpVO;

public class Main2 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		//------------------------------------------------------------------
		SqlSession ss = factory.openSession();
		
		List<EmpVO> list = ss.selectList("emp_mgr.getManager");
		
		for(EmpVO vo : list) {
			List<DeptEmpVO> d_list = vo.getD_list();//해당 부서에 근무하는 사원정보들
			System.out.println(vo.getEmp_no() +"-"+vo.getFirst_name()+"-"+vo.getGender());
			for(DeptEmpVO dvo : d_list)
				System.out.println("\t "+dvo.getDept_no()+"/"+dvo.getDept_name()+
						"/"+dvo.getFrom_date()+"/"+dvo.getTo_date());
		}
		
		if(ss != null)
			ss.close();
	}
}
