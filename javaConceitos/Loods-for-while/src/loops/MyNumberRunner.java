package loops;

public class MyNumberRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyNumber number = new MyNumber(10);
		
		System.out.println("is prime "+ number.isPrime());
		
		System.out.println("total sum "+ number.sumUptoN());
		
		System.out.println("the number of divisors without is " + number.sumOfDivisors());
		number.printANumberTriangle();
		
	}

}
