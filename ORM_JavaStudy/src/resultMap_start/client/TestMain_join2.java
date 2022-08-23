package resultMap_start.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_start.vo.DeptEmpVO;
import resultMap_start.vo.DeptEmpVO2;
import resultMap_start.vo.EmployeeVO;

public class TestMain_join2 {

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
		
		List<DeptEmpVO2> list = ss.selectList( "employee.search_dept_name" );
		
		for( DeptEmpVO2 vo : list ) {
			System.out.println( vo.getEmp_no() + " / " +
								vo.getFname() + " / " + 
								vo.getLname() + " / " +
								vo.getBdate() + " / " +
								vo.getHdate() + " / " +
								vo.getDept_no() + " / " +
								vo.getDept_name()
								);
		}
		
		if( ss != null ) ss.close();

	}

}
