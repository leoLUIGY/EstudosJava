package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class DescendingStudentComparator implements Comparator<Student>{
	public int compare(Student student1,Student student2) {
		return Integer.compare(student1.getId(), student2.getId());
	}
}


public class StudentsCollectionRunner {

	public static void main(String[] args) {
		List<Student> students = List.of(new Student(1, "leonidas"),
				new Student(5, "ygor"),
				new Student(3, "leonardo"));
		
		List<Student> studentAl = new ArrayList<>(students);
		
		
		System.out.println(students);
		
		studentAl.sort( new DescendingStudentComparator());
		
		System.out.println(studentAl);
		
		
	}

}
