package am.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.EmpVO;

public class Main1 {

	public static void main(String[] args) throws Exception {
		// 1) 환경설정 파일(config.xml)을 읽기하기 위한 Reader생성
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		
		// 2) SqlSessionFactory를 만들 수 있는 Builder생성
		//SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		// 3) 위에서 생성한 builder를 통해 SqlSessionFactory생성
		//SqlSessionFactory factory = builder.build(r);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		// --------------------------- 최초 한번 수행하는 것이 좋다. ------------------
		
		// 4) SQL문장을 모두 호출 및 실행 할 수 있는 SqlSession객체를 
		//	factory를 통해 얻어낸다.
		SqlSession ss = factory.openSession();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("검색할 이름:");
		String fname = scan.nextLine(); //키보드로 부터 문자열 받기
		
		//emp.search_name을 호출하면서 인자로 fname이 가지고 있는 값을 전달한다.
		List<EmpVO> list = ss.selectList("emp.search_name", fname);
		System.out.println("검색결과:");
		for(EmpVO vo : list) {
			System.out.println(vo.getEmp_no()+"/"+vo.getFirst_name()+"/"+vo.getHire_date());
		}
		
		if(ss != null)
			ss.close();
		
	}

}






