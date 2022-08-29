package resultMap_collection.vo;

import java.util.List;

public class DeptVO {
	private String dept_no, dept_name;
	
	private List<EmpVO> emp_list;

	public String getDept_no() { return dept_no; }
	public void setDept_no(String dept_no) { this.dept_no = dept_no; }

	public String getDept_name() { return dept_name; }
	public void setDept_name(String dept_name) { this.dept_name = dept_name; }

	public List<EmpVO> getEmp_list() { return emp_list; }
	public void setEmp_list(List<EmpVO> emp_list) { this.emp_list = emp_list; }
}
