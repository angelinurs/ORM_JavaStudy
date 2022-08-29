package am.client;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import am.vo.BookVO;

public class Main2 {

	public static void main(String[] args) throws Exception {
		Reader r = Resources.getResourceAsReader("am/config/config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(r);
		r.close();
		
		SqlSession ss = factory.openSession(true);//true는 auto commit기능이다.
		
		//---- vo를 이용한 추가 작업-----------
		BookVO bvo = new BookVO();
		bvo.setB_title("우리가 바라보는");
		bvo.setB_author("test");
		bvo.setB_press("출판사 혼");
		bvo.setB_price("12000");
		
		int count = ss.insert("book.add2", bvo);
		//ss.commit();은 안해도 자동으로 처리됨!
		
		
		BookVO bvo1 = new BookVO();
		bvo1.setB_title("도서1");
		bvo1.setB_author("test");
		bvo1.setB_press("출판사 혼");
		bvo1.setB_price("12400");
		
		BookVO bvo2 = new BookVO();
		bvo2.setB_title("도서2");
		bvo2.setB_author("test");
		bvo2.setB_press("출판사 혼");
		bvo2.setB_price("22000");
		
		BookVO bvo3 = new BookVO();
		bvo3.setB_title("도서3");
		bvo3.setB_author("test");
		bvo3.setB_press("출판사 혼");
		bvo3.setB_price("12750");
		
		BookVO bvo4 = new BookVO();
		bvo4.setB_title("도서4");
		bvo4.setB_author("test");
		bvo4.setB_press("출판사 혼");
		bvo4.setB_price("32000");
		
		ArrayList<BookVO> b_list = new ArrayList<BookVO>();
		b_list.add(bvo1);
		b_list.add(bvo2);
		b_list.add(bvo3);
		b_list.add(bvo4);
		
		HashMap<String, List<BookVO>> map = new HashMap<String, List<BookVO>>();
		map.put("list", b_list);
		
		count = ss.insert("book.add3", map);// 여러개의 레코드를 추가한다.
		
		List<BookVO> list = ss.selectList("book.total");
		
		for(BookVO vo : list)
			System.out.println(vo.getB_id()+"/"+vo.getB_author()+"/"+
					vo.getB_press()+"/"+vo.getB_price());
		
		System.out.println("------------------------------------");
		
		ArrayList<String> id_list = new ArrayList<String>();
		id_list.add("2");
		id_list.add("5");
		id_list.add("3");
		id_list.add("9");
		
		Map<String, ArrayList<String>> mm = new HashMap<String, ArrayList<String>>();
		mm.put("id_list", id_list);
		
		list = ss.selectList("book.select_id", mm);
		for(BookVO vo : list)
			System.out.println(vo.getB_id()+"/"+vo.getB_author()+"/"+
					vo.getB_press()+"/"+vo.getB_price());
		
		if(ss != null)
			ss.close();

	}

}
