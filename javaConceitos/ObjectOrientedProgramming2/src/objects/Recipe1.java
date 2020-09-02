package objects;

public class Recipe1 extends AbstractRecipe{

	@Override
	void getReady() {
		// TODO Auto-generated method stub
		System.out.println("get the raw materials");
		System.out.println("get the utensials");
	}

	@Override
	void doTheDish() {
		// TODO Auto-generated method stub
		System.out.println("do the dish");
	}

	@Override
	void cleanup() {
		// TODO Auto-generated method stub
		System.out.println("clanup the utensils");
	}

}
