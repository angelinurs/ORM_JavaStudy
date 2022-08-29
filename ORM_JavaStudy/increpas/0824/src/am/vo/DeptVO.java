package am.vo;

import java.util.List;

public class DeptVO {
	String dept_no, dept_name;
	
	//각 부서에 포함된 사원정보들
	List<EmpVO> e_list;

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public List<EmpVO> getE_list() {
		return e_list;
	}

	public void setE_list(List<EmpVO> e_list) {
		this.e_list = e_list;
	}
}
