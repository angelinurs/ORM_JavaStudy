package orm_start;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestMainSecond {

	public static void main(String[] args) {
		
		// STEP 1.
		// 환경설정파일 ( config.xml ) 파일 읽기
		// 현재 프로젝트에 mybatis.jar 라이브러리가 등록되어야 한다.
		SqlSession ss = null;
		try {
			Reader r = Resources.getResourceAsReader( "orm_start/config.xml" );
			
			// STEP 2.
			// 팩토리 만드는 일꾼을 생성한다.
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			
			// STEP 3.
			// step 2. 에서 만든 object 를 이용해 factory 생성
			SqlSessionFactory factory = builder.build( r );
			
			// STEP 4.
			// step 3. 에서 만든 factory 를 이용해 sql session 생성
			ss = factory.openSession();
			
			// STEP 5.
			// 원하는 sql 문 ( mapper ) 를 호출하거나 결과( List ) 를 받는다.
			// 검색 할 내용을 보낼때 selectList 에 object 추가
			Scanner sc = new Scanner( System.in );
			String gender;
			do {
				System.out.println( "which gender ? ");
				gender = sc.next();
				
				if( gender.equalsIgnoreCase( "f" ) || gender.equalsIgnoreCase( "m" ) ) {
					break;
				} else {
					System.out.println( "Input just M/F ");
				}
				
			} while ( true );
			
			List< EmployeeVO > list = ss.selectList( "emp.gender", gender );
			
			// STEP 6.
			// 저장된 객체 리스트 출력해보기
			list.forEach( (x)-> { System.out.println(x.toString());} );
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			// STEP 7.
			// sql session 종료
			if( ss != null ) ss.close();
		}

	}

}
