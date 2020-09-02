package interfaces;

public class GameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MarioGame game = new MarioGame();
		GamingConsole game =  new ChessGame();
		game.up();
		game.down();
		game.right();
		game.left();
		
	}

}
