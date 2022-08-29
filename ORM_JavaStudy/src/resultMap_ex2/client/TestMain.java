package resultMap_ex2.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_ex2.vo.EmployeeVO;
import resultMap_ex2.vo.NewDepartmentVO;


public class TestMain {

	public static void main(String[] args) {
//		concatEmpToDept();
		
		// dbConnect
		Reader r = null;
		SqlSessionFactory factory = null;
		try {
			r = Resources.getResourceAsReader( "resultMap_ex2/config/config.xml" );
			factory = new SqlSessionFactoryBuilder().build( r );
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if( r != null ) r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		SqlSession ss  = factory.openSession();
		
		List<NewDepartmentVO> list = ss.selectList( "dept_detail.all" );
		
		if( ss != null ) ss.close();
		
		System.out.println( "input dept no : " );
		Scanner sc = new Scanner( System.in );
		String inputDept = sc.next();
		
		StringBuffer sb = new StringBuffer();
		
		for( NewDepartmentVO vo : list ) {
			
			if( inputDept.equalsIgnoreCase( vo.getDept_no() ) ) {
				List<EmployeeVO> e_list =  vo.getE_list();
				for( EmployeeVO evo : e_list ) {
					
					sb.append( evo.getDept_no() ).append( " / " );
					sb.append( evo.getEmp_no() ).append( " / " );
					sb.append( evo.getFirst_name() ).append( " / " );
					sb.append( evo.getSalary() ).append( "\n" );
				}
				
			}
//			sb.append( vo.getDept_no() ).append( " / " );
//			sb.append( vo.getDept_name() ).append( " / " );
//			sb.append( vo.getCvo().getFirst_name() ).append( " / " );
		}
		
		System.out.println( sb.toString() );

	}

}
