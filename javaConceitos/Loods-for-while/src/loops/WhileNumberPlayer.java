package loops;

public class WhileNumberPlayer {
	private int limit;
	public WhileNumberPlayer(int limit)
	{
		this.limit = limit;
	}
	
	public void printSquaresUptoLimit() {
		int squares = 0;
		System.out.print("Squares: ");
		while(squares*squares <=limit) {
			System.out.print(squares*squares + " ");
			squares++;
		}
		System.out.println();
	}
	
	public void printCubesUpToLimit() {
		int squares = 0;
		System.out.print("Cubes: ");
		while(squares*squares*squares<=limit) {
			System.out.print(squares*squares*squares + " ");
			squares++;
		}
	}
	
}
