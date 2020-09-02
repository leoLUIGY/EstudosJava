package generics;

public class GenericsRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MycustomList<String> list = new MycustomList<>();
		list.addElement("element 1");
		list.addElement("element 2");
		String value = list.get(0);
		System.out.println(value);
		
		MycustomList<Integer> list2 = new MycustomList<>();
		list2.addElement(Integer.valueOf(5));
		list2.addElement(Integer.valueOf(6));
		Integer number = list2.get(0);
		System.out.println(number);
		
	}

}
