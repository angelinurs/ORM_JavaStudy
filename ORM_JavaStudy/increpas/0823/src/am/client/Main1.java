package am.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.EmpVO;

public class Main1 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		
		//-------------------------------------------------------------------
		//원하는 SQL문(mapper)을 호출하기 위해 SqlSession을 얻어낸다.
		SqlSession ss = factory.openSession();
		
		//원하는 SQL문 호출
		List<EmpVO> list = ss.selectList("emp.all");
		
		for(EmpVO vo : list)
			System.out.println(vo.getEmp_no()+"/"+vo.getFirst_name()+
					"/"+vo.getBirth_date()+"/"+vo.getHdate());
		
		if(ss != null)
			ss.close();
	}

}











