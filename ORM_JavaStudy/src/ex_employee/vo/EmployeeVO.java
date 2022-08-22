package ex_employee.vo;

public class EmployeeVO {
	int row_number;
	String emp_no;
	String birth_date;
	String first_name;
	String last_name;
	String gender;
	String hire_date;
	
	public int getRowNumber() { return row_number; }
	public void setRowNumber(int rowNumber) { this.row_number = rowNumber; }
	
	public String getEmp_no() { return emp_no; }
	public void setEmp_no(String emp_no) { this.emp_no = emp_no; }
	
	public String getBirth_date() { return birth_date; }
	public void setBirth_date(String birth_date) { this.birth_date = birth_date; }
	
	public String getFirst_name() { return first_name; }
	public void setFirst_name(String first_name) { this.first_name = first_name; }
	
	public String getLast_name() { return last_name; }
	public void setLast_name(String last_name) { this.last_name = last_name; }
	
	public String getGender() { return gender; }
	public void setGender(String gender) { this.gender = gender; }
	
	public String getHire_date() { return hire_date; }
	public void setHire_date(String hire_date) { this.hire_date = hire_date; }
}
