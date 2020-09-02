package objectIheritance;

public class StudentRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Student student = new Student();
		//student.setEmail("leonidas200999@hotmail.com");
		
		Employee employee = new Employee("leonidas", "programmer");
		employee.setEmail("leonidas200999@hotmail.com");
		employee.setPhoneNumber("11961608269");
		employee.setEmployeeGrade("A");
		employee.setTitle("programmer");
		System.out.println(employee);
		
		
	}

}
