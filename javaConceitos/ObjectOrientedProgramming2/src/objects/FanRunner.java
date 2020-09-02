package objects;

public class FanRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fan fan  = new Fan("manufacture 1", 0.4444, "blue");
		
		fan.switchOn();
		fan.setSpeed((byte)5);
		System.out.println(fan);
	}

}
