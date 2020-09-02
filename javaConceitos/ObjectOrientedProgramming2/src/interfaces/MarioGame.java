package interfaces;

public class MarioGame implements GamingConsole{

	@Override
	public void up() {
		// TODO Auto-generated method stub
		System.out.println("jump");
	}

	@Override
	public void down() {
		// TODO Auto-generated method stub
		System.out.println("goes into a hole");
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		System.out.println("");
	}

	@Override
	public void right() {
		// TODO Auto-generated method stub
		System.out.println("go forward");
	}

}
