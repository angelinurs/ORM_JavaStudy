package pm;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Ex3 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("pm/conf.xml");
		
		SqlSessionFactoryBuilder builder = 
				new SqlSessionFactoryBuilder();
		
		//위의 builder를 만든 이유는 SqlSessionFactory를 만들기 위해서다.
		SqlSessionFactory factory = builder.build(r);
		r.close();
		
		//factory를 만든 이유는 필요할 때마다 SqlSession을 만들기 위해서다.
		SqlSession ss = factory.openSession();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("부서번호:");
		String cmd = scan.nextLine();
		
		List<DeptVO> list = ss.selectList("dept.search", cmd);
		
		for(DeptVO vo: list)
			System.out.println(vo.getDept_no()+"/"+vo.getDept_name());
		
		if(ss != null)
			ss.close();

	}

}
