package am.vo;

import java.util.List;

public class EmpVO {
	String emp_no, first_name, hire_date;
	List<SalVO> s_list;

	public List<SalVO> getS_list() {
		return s_list;
	}

	public void setS_list(List<SalVO> s_list) {
		this.s_list = s_list;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getHire_date() {
		return hire_date;
	}

	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}
}
