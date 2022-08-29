package am.vo;

public class DeptVO {
	// departments테이블에서 부서번호와 부서명을 가져오려 한다.
	String dept_no, dept_name;

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
}
