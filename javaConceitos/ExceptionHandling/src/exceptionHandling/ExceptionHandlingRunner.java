package exceptionHandling;

public class ExceptionHandlingRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		method1();
	}
	
	private static void method1() {
		method2();
	}

	private static void method2() {
		String str = null;
		str.length();
	}

}
