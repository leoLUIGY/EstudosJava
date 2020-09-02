package generics;

import java.util.ArrayList;

public class MycustomList<T>{
	ArrayList<T> list = new ArrayList<>();
	
	public void addElement(T element) {
		list.add(element);
	}
	
	public void removeElement(T element) {
		list.remove(element);
	}
	
	public T get(int element) {
		return list.get(element);
	}
	
	public String toString() {
		return list.toString();
	}
}
