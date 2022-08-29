package resultMap_ex.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import resultMap_ex.vo.DepartmentVO;
import resultMap_ex.vo.EmpVO;
import resultMap_ex.vo.NewDepartmentVO;
import resultMap_ex.vo.TotalVO;

public class TestMain {
	public static void concatEmpToDept() {
		
		// dbConnect
		Reader r = null;
		SqlSessionFactory factory = null;
		try {
			r = Resources.getResourceAsReader( "resultMap_ex/config/config.xml" );
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
		
		List<TotalVO> list = ss.selectList( "total_emp.all" );
		
		if( ss != null ) ss.close();
		
		StringBuffer sb = new StringBuffer();
		for( TotalVO vo : list ) {
			EmpVO evo =  vo.getEvo();
			DepartmentVO dvo = vo.getDvo();
			
			sb.append( vo.getEmp_no() ).append( " / " );
			sb.append( vo.getFrom_date() ).append( " / " );
			sb.append( evo.getFirst_name() ).append( " / " );
			sb.append( evo.getGender() ).append( " / " );
			sb.append( evo.getHire_date() ).append( " / " );
			sb.append( dvo.getDept_no() ).append( " / " );
			sb.append( dvo.getDept_name() ).append( "\n" );
		}
		
		System.out.println( sb.toString() );
		
	}

	public static void main(String[] args) {
//		concatEmpToDept();
		
		// dbConnect
		Reader r = null;
		SqlSessionFactory factory = null;
		try {
			r = Resources.getResourceAsReader( "resultMap_ex/config/config.xml" );
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
		
		StringBuffer sb = new StringBuffer();
		
		for( NewDepartmentVO vo : list ) {
			sb.append( vo.getDept_no() ).append( " / " );
			sb.append( vo.getDept_name() ).append( " / " );
			sb.append( vo.getCvo().getFirst_name() ).append( " / " );
			sb.append( vo.getE_list().size() ).append( "\n" );
		}
		
		System.out.println( sb.toString() );

	}

}
