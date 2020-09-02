package introduction;

public class MotoBikeRunner {
	public static void main(String[] args)
	{
		MotorBike ducati = new MotorBike(100);
		MotorBike honda = new MotorBike(200);
		
		ducati.start();
		honda.start();
		
		ducati.increaseSpeed(100);
		System.out.printf("the speed of the ducati is %d", ducati.getSpeed()).println();
		ducati.decreaseSpeed(100);
		System.out.printf("the new speed of the ducati is %d", ducati.getSpeed()).println();
		
		honda.increaseSpeed(100);
		System.out.printf("the speed of honda is %d", honda.getSpeed()).println();
		honda.decreaseSpeed(50);
		System.out.printf("the new speed of honda is %d", honda.getSpeed()).println();

	}
}
