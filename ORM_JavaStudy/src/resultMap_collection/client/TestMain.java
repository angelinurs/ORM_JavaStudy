package resultMap_collection.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_collection.vo.DeptVO;
import resultMap_collection.vo.EmpVO;

public class TestMain {

	public static void main(String[] args) {
		Reader r = null;
		SqlSessionFactory factory = null;
		SqlSession ss = null;
		
		try {
			r = Resources.getResourceAsReader( "resultMap_collection/config/config.xml" );
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
		
		ss = factory.openSession();
		
		List<DeptVO> list = ss.selectList( "dept_emp.all" );
		
		if( ss != null ) ss.close();
		
		StringBuffer sb = new StringBuffer();
		
		for( DeptVO vo : list ) {
			List<EmpVO> emp_list =  vo.getEmp_list();
			
			sb.append( vo.getDept_no() ).append( " / " );
			sb.append( vo.getDept_name() ).append( " / " );
			sb.append( emp_list.size() ).append( " 명\n" );
			
//			for( EmpVO evo : emp_list ) {
//				
//				sb.append( vo.getDept_no() ).append( " / " );
//				sb.append( vo.getDept_name() ).append( " / " );
//				sb.append( evo.getEmp_no() ).append( " / " );
//				sb.append( evo.getFirst_name() ).append( "\n" );
//			}
		}
		
		System.out.println( sb.toString());

	}

}
