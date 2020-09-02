package functionalProgramming;

import java.util.List;

public class FPNumberRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> numbers = List.of(2,2,5,34,5,56,7,6,8);
		
		List.of("Apple", "Ant", "Bat").stream().map(s->s.toLowerCase()).forEach(p -> System.out.println(p));
		
		/*numbers.stream().distinct().sorted().forEach(e->System.out.println(e));
		int sum = numbers.stream().reduce(0, (number1, number2) -> number1+number2);
		System.out.println(sum);*/
	}

}
