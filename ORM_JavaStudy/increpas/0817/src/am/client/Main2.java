package am.client;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.EmpVO;

public class Main2 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		
		//------------------------------------------------------------------
		SqlSession ss = factory.openSession();
		
		//전체 사원들 받기
		List<EmpVO> list = ss.selectList("emp.all");
		
		// 위에서 받은 List구조를 2차원 배열로 만들어보자!
		// 보고싶은 컬럼명들을 1차원 배열로 준비
		String[] c_name = {"사번","이름","입사일","성별"};
		String[][] ar = new String[list.size()][c_name.length];//2차원 배열이 생성되었다.
				// 하지만 안에 자원들을 비어있는 상태다. 다시 말해서 list에 있는 자원들을
				// ar에 채워야 한다.
		
		//for(int i=0; i<list.size(); i++) {
		//	EmpVO vo = list.get(i); // List에서 vo객체를 하나씩 가져온다.
		int i = 0;
		for( EmpVO vo : list) {
			
			ar[i][0] = vo.getEmp_no();//사번
			ar[i][1] = vo.getFirst_name();//이름
			ar[i][2] = vo.getHire_date();//입사일
			ar[i][3] = vo.getGender();//성별
			i++;
		}
		
		// 2차원 배열에 자원들을 확인 하는 반복문
		for(int a=0; a<ar.length; a++) {
			for(int b=0; b<ar[a].length; b++) {
				System.out.printf("%s\t", ar[a][b]);				
			}
			System.out.println();//줄바꿈
		}
	}

}








