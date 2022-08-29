package pm;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Ex2 {

	public static void main(String[] args) throws Exception {
		// 1) 환경설정 파일(config.xml)읽기 - 이런 것을 하기 위해서는 
		// 현재 프로젝트에 MyBatis라이브러리(mybatis-3.5.10.jar)가 등록되어야 한다.
		Reader r = Resources.getResourceAsReader("pm/config.xml");
		
		// 2) 팩토리를 만드는 일꾼을 생성한다.
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		// 3) 2)에서 만든 일꾼을 통해 팩토리를 생성
		SqlSessionFactory factory = builder.build(r);
		r.close(); //reader닫기
		
		// 4) 3)에서 생성된 팩토리를 통해 SqlSession을 얻어낸다.
		SqlSession ss = factory.openSession();
		
		// 5) 원하는 SQL문(mapper)을 호출하거나 결과(List)를 받는다.
		List<EmpVO> list = ss.selectList("emp.all");
		
		// 6) 반복문으로 출력하자
		for(EmpVO vo : list)
			System.out.println(vo.getEmp_no()+"/"+vo.getFirst_name()+"/"+vo.getHire_date());
		
		System.out.println("전체보기 끝------------------------");
		
		Scanner scan = new Scanner(System.in);
		System.out.println("성별검색(M/F) :");
		//키보드로 부터 문자열 하나를 받는다.
		String cmd = scan.nextLine();
		
		// F 또는 f이면 그대로 사용하고 그렇지 않으면 무조건 M으로 초기화 시킨다.
		if(!cmd.equalsIgnoreCase("f")) //f가 아닐 경우
			cmd = "M";
		
		list = ss.selectList("emp.search_gender", cmd); // 호출하고자 하는 SQL문의 id, 파라미터 값
		for(EmpVO vo : list)
			System.out.println(vo.getEmp_no()+"/"+vo.getFirst_name()+"/"+vo.getHire_date());
		
		System.out.println("총 "+list.size()+"건 검색------------------");
		
		// 7) SqlSession닫기
		if(ss != null)
			ss.close();

	}

}
