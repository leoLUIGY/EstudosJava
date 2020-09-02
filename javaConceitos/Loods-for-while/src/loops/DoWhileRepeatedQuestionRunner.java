package loops;

import java.util.Scanner;

public class DoWhileRepeatedQuestionRunner {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Scanner scanner = new Scanner(System.in);
		int number = -1;
		do {
			if(number>0)
				System.out.println("the Cube is "+ (number * number * number));
			
			System.out.print("Enter a number: ");
			number = scanner.nextInt();
		}while(number>0);
		System.out.println("Thank you! You have a fun");

	}

}

