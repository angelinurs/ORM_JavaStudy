package pm.vo;

import java.util.List;

public class DiviVO {
	String dept_no, dept_name;
	List<ChiefVO> c_list; //부서장들
	ChiefVO cvo;//현 부서장
	List<EmpVO> e_list; // 부서의 구성원(사원)들
	
	public ChiefVO getCvo() {
		return cvo;
	}
	public void setCvo(ChiefVO cvo) {
		this.cvo = cvo;
	}
	public List<EmpVO> getE_list() {
		return e_list;
	}
	public void setE_list(List<EmpVO> e_list) {
		this.e_list = e_list;
	}
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
	public List<ChiefVO> getC_list() {
		return c_list;
	}
	public void setC_list(List<ChiefVO> c_list) {
		this.c_list = c_list;
	}
}
