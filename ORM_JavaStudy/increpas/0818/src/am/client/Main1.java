package am.client;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main1 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		//----  초기작업 ---------------------------------------------------
		
		SqlSession ss = factory.openSession();
		
		//저장하고자 하는 정보를 Map구조에 저장한다.
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", "자바바이블");
		map.put("author", "오경주외2");
		map.put("press", "한빛미디어");
		map.put("price", "29000");
		
		int cnt = ss.insert("book.add", map);
		if(cnt > 0) {
			ss.commit();// 트랜잭션(기록지) : DB에 적용하고 마무리 하라는 뜻 
			System.out.println(cnt+":저장 완료!");
		}
		if(ss != null)
			ss.close();
	}

}
