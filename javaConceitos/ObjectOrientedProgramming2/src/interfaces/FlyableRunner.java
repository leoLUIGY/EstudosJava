package interfaces;

import java.util.ArrayList;

public class FlyableRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Flyable[] flyingObjects = {new Bird(), new Aeroplane()};
		
		for(Flyable fly:flyingObjects){
			fly.fly();
		}
	}

}
