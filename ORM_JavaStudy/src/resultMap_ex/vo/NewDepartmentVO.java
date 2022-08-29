package resultMap_ex.vo;

import java.util.List;

public class NewDepartmentVO {
	private String dept_no;
	private String dept_name;
	
	private CheifVO cvo;
	private List<EmployeeVO> e_list;
	
	public String getDept_no() { return dept_no; }
	public void setDept_no(String dept_no) { this.dept_no = dept_no; }
	
	public String getDept_name() { return dept_name; }
	public void setDept_name(String dept_name) { this.dept_name = dept_name; }
	
	public CheifVO getCvo() { return cvo; }
	public void setCvo(CheifVO cvo) { this.cvo = cvo; }
	
	public List<EmployeeVO> getE_list() { return e_list; }
	public void setE_list(List<EmployeeVO> e_list) { this.e_list = e_list; }
}
