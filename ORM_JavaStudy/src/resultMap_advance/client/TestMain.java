package resultMap_advance.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_advance.vo.BuseoVO;
import resultMap_advance.vo.SawonVO;

public class TestMain {

	public static void main(String[] args) {
		Reader r = null; 
		SqlSessionFactory factory = null;
		try {
			r = Resources.getResourceAsReader( "resultMap_advance/config/config.xml" );
			factory = new SqlSessionFactoryBuilder().build( r );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSession ss = factory.openSession();
		List<BuseoVO> list = ss.selectList( "employee.all" );
		
		for( BuseoVO vo : list ) {
			SawonVO svo = vo.getSvo();
			System.out.println( vo.getDept_no() + " / " +
								vo.getDept_name() + " / " +
								svo.getEmp_no() + " / " +
								svo.getFirst_name()
								);
		}
		
		if( ss != null ) ss.close();

	}

}
