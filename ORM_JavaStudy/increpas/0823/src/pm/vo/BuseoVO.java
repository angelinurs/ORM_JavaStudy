package pm.vo;

public class BuseoVO {
	private String dept_no, dept_name;
	private SawonVO svo;// 부서장의 정보
	
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
	public SawonVO getSvo() {
		return svo;
	}
	public void setSvo(SawonVO svo) {
		this.svo = svo;
	}
}
