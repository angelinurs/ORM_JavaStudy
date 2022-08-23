package resultMap_start.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_start.vo.EmployeeVO;

public class TestMain {

	public static void main(String[] args) {
		//
		Reader r = null;
		SqlSessionFactory factory = null;
		try {
			r = Resources.getResourceAsReader( "resultMap_start/config/config.xml" );
			
			factory = new SqlSessionFactoryBuilder().build( r );
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
				try {
					if( r != null ) r.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
		}
		
		//
		SqlSession ss = factory.openSession();
		
		List<EmployeeVO> list = ss.selectList( "employee.all" );
		
		for( EmployeeVO vo : list ) {
			System.out.println( vo.getEmp_no() + " / " + 
								vo.getFirst_name() + " / " + 
								vo.getBirth_date() + " / " +
//								vo.getHire_date()
								vo.getHdate()
								);
		}
		
		if( ss != null ) ss.close();

	}

}
