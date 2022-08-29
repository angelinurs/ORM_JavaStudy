package pm;

public class DeptVO {
	//departments테이블에서 필요로한 dept_no와 dept_name이라는 컬럼을
	//멤버변수로 선언!
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
