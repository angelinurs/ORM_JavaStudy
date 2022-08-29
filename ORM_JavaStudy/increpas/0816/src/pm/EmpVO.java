package pm;

public class EmpVO {
	//employees테이블로 부터 가져오고자 하는 컬럼명들을
	// 멤버변수로 선언한다.
	String emp_no, first_name, hire_date;

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
