package am.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.DeptEmpVO;

public class Main2 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		//----------------------------------------------------------------
		
		SqlSession ss = factory.openSession();
		List<DeptEmpVO> list = ss.selectList("emp.total");
		
		for(DeptEmpVO vo : list)
			System.out.println(vo.getEmp_no()+"/"+vo.getF_name()+
					"/"+vo.getB_date()+"/"+vo.getGender()+"/"+vo.getH_date()+
					"/"+vo.getDeptno()+"/"+vo.getDvo().getDept_name());
		
		if(ss != null)
			ss.close();
	}

}
