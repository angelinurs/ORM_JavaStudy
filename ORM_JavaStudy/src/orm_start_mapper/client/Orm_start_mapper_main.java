package orm_start_mapper.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import orm_start_mapper.vo.EmployeeVO;

public class Orm_start_mapper_main {

	public static void main(String[] args) {
		
		SqlSession ss = null;
		
		try {
			
			/* **********************
			 *  begin get SqlSession
			 */
			Reader r = Resources.getResourceAsReader( "orm_start_mapper/config/config.xml" );
			
			ss = new SqlSessionFactoryBuilder().build( r ).openSession();
			
			r.close();
			
			/* **********************
			 *  end get SqlSession
			 *  - 위에 까지가 최초 한번 수행 하는 구간.
			 */
			
			System.out.println( "input first name" );
			
			Scanner sc = new Scanner( System.in );
			String fname = sc.nextLine();
			
			List<EmployeeVO> list = ss.selectList( "search_name", fname );
			
			list.forEach( ( x )-> { System.out.println( x.toString() ); } );
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if( ss != null ) ss.close();
		}

	}

}
