package interfaces;

public class Project {
	interface Test{
		void nothing();
		default void nothing1() {}
	}
	
	class Class1 implements Test{
		public void nothing() {
			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComplexAlgorithm algorithm = new RealAlgorithm();
		System.out.println(algorithm.complexAlgorithm(10, 20));
	}

}
