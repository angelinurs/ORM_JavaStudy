package orm_start_mapper.vo;

public class DepartmentVO {
	String dept_no;
	String dept_name;
	
	public String getDept_no() { return dept_no; }
	public void setDept_no(String dept_no) { this.dept_no = dept_no; }
	
	public String getDept_name() { return dept_name; }
	public void setDept_name(String dept_name) { this.dept_name = dept_name; }
	
	public String toString()	{
		return getDept_no() + " / " + getDept_name();
	}
}
