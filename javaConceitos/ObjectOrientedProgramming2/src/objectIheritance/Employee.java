package objectIheritance;

import java.math.BigDecimal;

public class Employee extends Person{
	private String title;
	private String employer;
	private String employeeGrade;
	private BigDecimal salary;

	
	public Employee(String name, String title) {
		super(name);
		this.title = title;
		System.out.println("leonidas");
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getEmployer() {
		return employer;
	}
	
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public String getEmployeeGrade() {
		return employeeGrade;
	}
	
	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}
	
	public BigDecimal getSalary() {
		return salary;
	}
	
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String toString() {
		return String.format(super.getEmail() +" title -%s, employer -%s, employeeGrade -%s,salary -%s",
				title, employer, employeeGrade, salary);
	}

}
