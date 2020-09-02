package functionalProgramming;

import java.util.List;

public class FunctionalProgrammingRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = List.of("Apple", "Banana", "Cat", "dog");
		printWithFP(list);
	}
	
	private static void printBasic(List<String> list) {
		for(String string: list) {
			System.out.println(string);
		} 
	}
	
	private static void printWithFP(List<String> list) {
		list.stream().forEach(element -> System.out.println(element));
	}
	private static void printWithFiltering(List<String> list) {
		list.stream()
			.filter(element -> element.endsWith("at"))
				.forEach(element -> System.out.println(element));
	}
}
