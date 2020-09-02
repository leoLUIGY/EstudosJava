package coditions;

import java.util.Scanner;

public class MenuRunner {
	static int number1;
	static int number2;
	static int choice;
	static Scanner scanner;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		scanner = new Scanner(System.in);
		
		System.out.println("digite o dia da semana de 0 a 6");
		int dayNumber = scanner.nextInt();
		System.out.println(isWeekDay(dayNumber));
		System.out.println(determineNameOfDay(dayNumber));
		
		System.out.println("Digite um mes de 1 a 12");
		int monthNumber = scanner.nextInt();
		System.out.println(determineNameOfMonth(monthNumber));
		//allOperation();
	
	}
	
	static void allOperation() {
		
		System.out.println("Enter Number1: ");
		number1 = scanner.nextInt();
		
		System.out.println("Enter Number2: ");
		number2 = scanner.nextInt();
		
		System.out.println("1 - add\n" +"2 - Subtract\n" + "3 - Divide\n" +"4 - Multiply\n");
		
		System.out.println("choose operation: ");
		choice = scanner.nextInt();
		if(operation() == 0) {
			System.out.println("valor invalido");
			allOperation();
		}else {
			System.out.println("o resultado é " + operation());
		}
	}
	private static int operation()
	{
		switch (choice) {
			case 1:
				return number1 + number2;
			case 2:
				return number1 - number2;
			case 3:
				return number1 / number2;
			case 4:
				return number1 * number2;
			default:
				return 0;
		} 
		
	}
	
	public static boolean isWeekDay(int dayNumber)
	{
		switch(dayNumber){
			case 0:
				return true;
			case 6:
				return true;
			default:
				return false;
		}
	}
	
	public static String determineNameOfMonth(int monthNumber) {
		switch(monthNumber)
		{
			case 1:
				return "janeiro";
			case 2:
				return "Fevereiro";
			case 3:
				return "Março";
			case 4:
				return "Abril";
			case 5:
				return "Maio";
			case 6:
				return "junho";
			case 7:
				return "julho";
			case 8:
				return "agosto";
			case 9:
				return "Setembro";
			case 10:
				return "outubro";
			case 11:
				return "novembro";
			case 12:
				return "Dezembro";
			default:
				return "esse não é um mes";
		}
	}
	
	public static String determineNameOfDay(int dayNumber) {
		switch(dayNumber)
		{
			case 0:
				return "domingo";
			case 1:
				return "segunda";
			case 2:
				return "terça";
			case 3:
				return "quarta";
			case 4:
				return "quinta";
			case 5:
				return "sexta";
			case 6:
				return "sabado";
			default:
				return "esse não é um dia da semana";
		}
	}
}
