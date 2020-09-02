package introduction;

public class BookRunner {
	public static void main(String[] args) {
		Book artOfComputerProgramming = new Book(100);
		Book effectiveJava = new Book(100);
		Book cleanCode = new Book(100);
		
		artOfComputerProgramming.increaseNoOfCopies(100);
		System.out.printf("the number of copies of art of computer is %d", artOfComputerProgramming.getNoOfCopies()).println();
		
		effectiveJava.decreaseNoOfCopies(100);
		System.out.printf("the number of copies of effective java is %d", effectiveJava.getNoOfCopies()).println();

		
		cleanCode.increaseNoOfCopies(200);
		System.out.printf("the number of copies of cleanCode is %d", cleanCode.getNoOfCopies()).println();
		
	}
}
