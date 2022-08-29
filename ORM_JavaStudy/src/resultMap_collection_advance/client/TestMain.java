package resultMap_collection_advance.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_collection_advance.vo.CheifVO;
import resultMap_collection_advance.vo.DiviVO;

public class TestMain {

	public static void main(String[] args) {
		Reader r = null;
		SqlSessionFactory factory = null;
		SqlSession ss = null;
		
		try {
			r = Resources.getResourceAsReader( "resultMap_collection_advance/config/config.xml" );
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
		
		List<DiviVO> list = ss.selectList( "division.all" );
		
		if( ss != null ) ss.close();
		
		StringBuffer sb = new StringBuffer();
		
		for( DiviVO vo : list ) {
			sb.append( vo.getDept_no() ).append( " / " );
			sb.append( vo.getDept_name() ).append( "\n " );
			
			List<CheifVO> c_list = vo.getC_list();
			
			for( CheifVO cvo : c_list ) {
				sb.append( "\t" );
				sb.append( cvo.getEmp_no() ).append( " / " );
				sb.append( String.format( "%-12s", cvo.getFirst_name() ) ).append( " / " );
				sb.append( cvo.getFrom_date() ).append( " / " );
				sb.append( cvo.getTo_date() ).append( "\n\n" );
				
			}
			
		}
		
		System.out.println( sb.toString());
	}

}
