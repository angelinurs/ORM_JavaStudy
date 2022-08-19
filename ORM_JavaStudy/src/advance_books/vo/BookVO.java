package advance_books.vo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BookVO {
	String b_id;
	String b_name;
	String b_price;
	String b_isbn;
	String a_id;
	
	public BookVO() { }
	
	public BookVO( String b_name, String b_price, String b_isbn, String a_id) {
		this.b_id = "";
		this.b_name = b_name;
		this.b_price = b_price;
		this.b_isbn = b_isbn;
		this.a_id = a_id;
	}

	public String getB_id() { return b_id; }
	public void setB_id(String b_id) { this.b_id = b_id; }

	public String getB_name() { return b_name; }
	public void setB_name(String b_name) { this.b_name = b_name; }

	public String getB_price() { return b_price; }
	public void setB_price(String b_price) { this.b_price = b_price; }

	public String getB_isbn() { return b_isbn; }
	public void setB_isbn(String b_isbn) { this.b_isbn = b_isbn; }

	public String getA_id() { return a_id; }
	public void setA_id(String a_id) { this.a_id = a_id; }
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append( getB_id() ).append( " / " );
		sb.append( getB_name() ).append( " / " );
		sb.append( getB_price() ).append( " / " );
		sb.append( getB_isbn() ).append( " / " );
		sb.append( getA_id() ).append( "\n" );
		
		return sb.toString();
	}
	
	// to map method
	public Map toMap( ) {
		
		Field[] fields  = this.getClass().getDeclaredFields();
		
		Map<String, Object> map = new HashMap<>();
		
			try {
				
				for( Field field : fields ) {
					map.put( field.getName(), field.get( this ) );
				}
				
				return map;
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				return null;
			}
	}
}
