package resultMap_ex.vo;

public class TotalVO {
	private String emp_no;
	private String from_date;
	
	private EmpVO evo;
	private DepartmentVO dvo;
	
	public String getEmp_no() { return emp_no; }
	public void setEmp_no(String emp_no) { this.emp_no = emp_no; }
	
	public String getFrom_date() { return from_date; }
	public void setFrom_date(String from_date) { this.from_date = from_date; }
	
	public EmpVO getEvo() { return evo; }
	public void setEvo(EmpVO evo) { this.evo = evo; }
	
	public DepartmentVO getDvo() { return dvo; }
	public void setDvo(DepartmentVO dvo) { this.dvo = dvo; }
}
