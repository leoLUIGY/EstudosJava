package exceptionHandling;

import java.util.Scanner;

public class FinallyRunner {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = null;
		
		try {
		scanner = new Scanner(System.in);
		int[] numbers = {1,2,3,4};
		
		int number = numbers[5];
		
		
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("scanner closed");
			scanner.close();
		}
	}

}
