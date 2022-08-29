package resultMap_collection.vo;

import java.util.List;

public class DeptManagerVO {
	private String emp_no;
	private String dept_no;
	private String from_date;
	private String to_date;
	
	private String dept_name;
	
	private List<EmpVO> e_list;
	
	public String getDept_name() { return dept_name; }
	public void setDept_name(String dept_name) { this.dept_name = dept_name; }
	
	public String getEmp_no() { return emp_no; }
	public void setEmp_no(String emp_no) { this.emp_no = emp_no; }
	
	public String getDept_no() { return dept_no; }
	public void setDept_no(String dept_no) { this.dept_no = dept_no; }
	
	public String getFrom_date() { return from_date; }
	public void setFrom_date(String from_date) { this.from_date = from_date; }
	
	public String getTo_date() { return to_date; }
	public void setTo_date(String to_date) { this.to_date = to_date; }
	
	public List<EmpVO> getE_list() { return e_list; }
	public void setE_list(List<EmpVO> e_list) { this.e_list = e_list; }

}
