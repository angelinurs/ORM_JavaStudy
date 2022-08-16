package orm_start;

public class EmployeeVO {
	
	String emp_no;
	String first_name;
//	String last_name;
	String hire_date;
	String gender;
	
	public String getEmp_no() { return emp_no; }
	public void setEmp_no(String emp_no) { this.emp_no = emp_no; }
	
	public String getFirst_name() { return first_name; }
	public void setFirst_name(String first_name) { this.first_name = first_name; }
	
//	public String getLast_name() { return last_name; }
//	public void setLast_name(String last_name) { this.last_name = last_name; }
	
	public String getHire_date() { return hire_date; }
	public void setHire_date(String hire_date) { this.hire_date = hire_date; }
	
	public String getGender() { return gender; }
	public void setGender(String gender) { this.gender = gender; }
	
	public String toString()	{
		return getEmp_no() + " / " + getFirst_name() + " / " + 
			getHire_date() + " / " + getGender();
	}
	
//	public String toString()	{
//		return getEmp_no() + " / " + 
//			getFirst_name() + " / " + getLast_name() + " / " + 
//			getHire_date();
//	}

}
