package collections;

import java.util.HashMap;
import java.util.Map;

public class MapRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "this is an awesome occasion. "
				+ "this has never happened before";
		
		Map<Character, Integer> occurances = new HashMap<>();
		
		char[] characters = str.toCharArray();
		for(char character: characters) {
			Integer integer = occurances.get(character);
			if(integer == null) {
				occurances.put(character, 1);
			}else {
				occurances.put(character, integer++);
			}
		}
	}

}
