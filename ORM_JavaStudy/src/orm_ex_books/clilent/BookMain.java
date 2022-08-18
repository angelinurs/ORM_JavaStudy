package orm_ex_books.clilent;

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

import orm_ex_books.vo.BookVO;

public class BookMain {

	public static void main(String[] args) {
		
		SqlSession ss = null;
		
		try {
			Reader r = Resources.getResourceAsReader( "orm_ex_books/config/config.xml" );
			
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build( r );
			
			r.close();
			
			 
			// - map 으로 insert 할 경우. 
			/*
			// true 로 해줄경우 commit 을 안해도 됨.
			ss = factory.openSession();
			
			
			HashMap<String, String> map = new HashMap<>();
			
			//
			//  - foreign key problem
			//  + preparing dependency table with foreign key
			// insert into `publisher`.author ( a_name, cellPhone, email, birth_date, authorcol )
			// values ( 'ellen', '010-9999-7777', 'ellen@books.com', '1982-09-14', '*_*' );
			
			// #{name}, #{price}, #{isbn}, #{a_id}
			map.put( "name", "dorosi" );
			map.put( "price", "10000" );
			map.put( "isbn", "1234567890" );
			map.put( "a_id", "1" );
			
			int cnt = ss.insert( "book.add_map", map );
			
			if( cnt > 0 ) {
				// transaction -> 기록지와 같은것
				ss.commit();
				System.out.println( cnt );
			}
			*/
			
			// - vo 로 insert 할 경우.
			/*
			ss = factory.openSession( true );
			
			BookVO bvo = new BookVO( "alice", "11000", "098654321", "1" );
			
			int count = ss.insert( "book.add_vo", bvo );
			*/
			
			// - list 로 저장된 경우.
			/*
			ss = factory.openSession( true );
			
			ArrayList<BookVO> list = new ArrayList<>();
			
			list.add( new BookVO( "alice", "11000", "098654321", "1") );
			list.add( new BookVO( "lion", "11000", "098654321", "1") );
			list.add( new BookVO( "dophin", "11000", "098654321", "1") );
			list.add( new BookVO( "dog", "11000", "098654321", "1") );
			
			HashMap< String, List<BookVO> > map = new HashMap<>();
			map.put( "List", list );
			
			int count = ss.insert( "book.add_arraylist", map );
			*/
			
			// - select query 에서의 `IN` 사용을 위한 자료형 만들기
			// + select query 사용시 table value 의 modify 가 일어나지 않아
			//    commit() 을 할 필요가 없으므로 openSession() method 를 사용할때, 
			//    true 지정을 해주지 않아도 된다.
			ss = factory.openSession();
			
			ArrayList<String> id_list = new ArrayList<>();
			id_list.add( "2" );
			id_list.add( "4" );
			id_list.add( "6" );
			id_list.add( "9" );
			
			Map<String, ArrayList<String> > id_list_map = new HashMap<>();
			id_list_map.put("id_list", id_list );
			
			List<BookVO> id_lists =  ss.selectList( "book.search_id_in", id_list_map );
			
			id_lists.forEach( (x)-> { System.out.println( x.toString() );} );
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( ss != null ) ss.close();
		}

	}

}
