package resultMap_collection.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_collection.vo.DeptManagerVO;
import resultMap_collection.vo.EmpVO;

public class TestMain_dept_manager {

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
		
		List<DeptManagerVO> list = ss.selectList( "dept_manager.all" );
		
		if( ss != null ) ss.close();
		
		StringBuffer sb = new StringBuffer();
		
		for( DeptManagerVO vo : list ) {
			
			List<EmpVO> e_list = vo.getE_list();
			
			for( EmpVO evo : e_list ) {
				sb.append( vo.getEmp_no() ).append( " / " );
				sb.append( String.format( "%-12s", evo.getFirst_name() ) ).append( " / " );
				sb.append( vo.getDept_no() ).append( " / " );
				// vo 객체가 아닌 String 으로 받았기 때문에 2개의 properties 를 가져오지 못했다.
				// 다만, association 이나 collention 을 선언하기 전에 result 로 선언되면 가져온다.
				sb.append( vo.getDept_name() ).append( "\n==== " );
				sb.append( evo.getGender() ).append( " / " );
				sb.append( vo.getFrom_date() ).append( " / " );
				sb.append( vo.getTo_date() ).append( "\n\n" );
				
			}
			
		}
		
		System.out.println( sb.toString());

	}

}
