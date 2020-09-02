package exceptionHandling;

public class ExeptionHandlingRunner2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		method1();
	}
	
	private static void method1() {
		method2();
	}

	private static void method2() {
		try {
			//String str = null;
			//str.length();
			int[] i = {1,2};
			int number =i[3];
		}catch(NullPointerException ex) {
			System.out.println("NullPointerException");
			ex.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException ex) {
			System.out.println("IndexOutOfBoundException");
			ex.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
