package resultMap_start.vo;

public class EmployeeVO {
	String emp_no;
	String birth_date;
	String first_name;
	String hire_date;
	
	// difference column name hire_date : hdate
	String hdate;
	
	public String getHire_date() { return hire_date; }
	public void setHire_date(String hire_date) { this.hire_date = hire_date; }
	
	public String getHdate() { return hdate; }
	public void setHdate(String hdate) { this.hdate = hdate; }
	
	public String getEmp_no() { return emp_no; }
	public void setEmp_no(String emp_no) { this.emp_no = emp_no; }
	
	public String getBirth_date() { return birth_date; }
	public void setBirth_date(String birth_date) { this.birth_date = birth_date; }
	
	public String getFirst_name() { return first_name; }
	public void setFirst_name(String first_name) { this.first_name = first_name; }

}
