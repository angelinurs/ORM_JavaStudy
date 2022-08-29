package am.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.DeptVO;
import am.vo.EmpVO;

public class Main1 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		//------------------------------------------------------------------
		SqlSession ss = factory.openSession();
		
		List<DeptVO> list = ss.selectList("dept_emp.all");
		
		for(DeptVO vo : list) {
			List<EmpVO> e_list = vo.getE_list();//해당 부서에 근무하는 사원정보들
			System.out.println(vo.getDept_no() +"-"+vo.getDept_name()
				+"("+e_list.size()+"명)");
		}
		
		if(ss != null)
			ss.close();
	}

}
