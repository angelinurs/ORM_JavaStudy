package am.vo;

import java.util.List;

public class EmpVO {
	String emp_no, first_name, gender;
	
	List<DeptEmpVO> d_list;

	public List<DeptEmpVO> getD_list() {
		return d_list;
	}

	public void setD_list(List<DeptEmpVO> d_list) {
		this.d_list = d_list;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	
}
