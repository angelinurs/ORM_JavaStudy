package resultMap_collection_advance.vo;

import java.util.List;

public class DiviVO {
	String dept_no;
	String dept_name;
	
	List<CheifVO> c_list;

	public String getDept_no() { return dept_no; }

	public void setDept_no(String dept_no) { this.dept_no = dept_no; }

	public String getDept_name() { return dept_name; }

	public void setDept_name(String dept_name) { this.dept_name = dept_name; }

	public List<CheifVO> getC_list() { return c_list; }
	public void setC_list(List<CheifVO> c_list) { this.c_list = c_list; }
	
}
