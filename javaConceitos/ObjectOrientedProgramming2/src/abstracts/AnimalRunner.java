package abstracts;

public class AnimalRunner {
	public static void main(String[] args) {
		Animal[] animals = {new Cat(), new Dog()};
		
		for(Animal anim:animals) {
			anim.bark();
		}
	}
}
