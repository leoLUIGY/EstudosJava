package loops;

public class MyNumber {
	private int number;
	public MyNumber(int number)
	{
		this.number = number;
	}
	
	public boolean isPrime() {
		if(number <=2) {
			return false;
		}
		for(int i = 2; i<=number-1; i++) {
			if (number%i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public int sumUptoN() {
		int numberSum = 0;
		
		for(int i = 1; i<=number;i++) {
			numberSum +=i;
		}
		
		return numberSum;
	}
	
	public int sumOfDivisors() {
		int divisors = 0;
		
		for(int i = 2; i<number; i++) {
			if (number%i == 0) {
				divisors+= i;
			}
		}
		
		return divisors;
	}
	
	public void printANumberTriangle() {
		for(int i = 0; i<=number; i++) {
			for(int j =0; j<= number-i; j++) {
				System.out.print(" ");
			}
			for(int j = 1; j<=i; j++) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
}
