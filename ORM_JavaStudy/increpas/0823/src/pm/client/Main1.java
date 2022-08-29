package pm.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pm.vo.BuseoVO;
import pm.vo.SawonVO;

public class Main1 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("pm/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		//------------------------------------------------------------------
		
		SqlSession ss = factory.openSession();
		List<BuseoVO> list = ss.selectList("dept.all");
		
		for(BuseoVO vo : list) {
			SawonVO svo = vo.getSvo();// 부서장 정보
			System.out.println(vo.getDept_no()+"/"+vo.getDept_name()+
					"/"+svo.getEmp_no()+"/"+svo.getFirst_name());
		}
			
		if(ss != null)
			ss.close();
	}

}






