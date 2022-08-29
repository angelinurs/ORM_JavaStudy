package pm.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pm.vo.ChiefVO;
import pm.vo.DiviVO;

public class Main1 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("pm/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		//------------------------------------------------------------------
		SqlSession ss = factory.openSession();

		List<DiviVO> list = ss.selectList("division.all");
		
		for(DiviVO vo : list) {
			System.out.println(vo.getDept_no()+" - "+ vo.getDept_name());
			List<ChiefVO> c_list = vo.getC_list();
			for(ChiefVO cvo : c_list)
				System.out.println("\t "+cvo.getEmp_no()+" / "+ cvo.getFirst_name()+
						" / "+cvo.getFrom_date()+" / "+cvo.getTo_date());
		}
		
		if(ss != null)
			ss.close();
	}

}




