package PrimitiveData;

public class MyChar {
	private char ch;
	public MyChar(char ch) {
		this.ch = ch;
	}
	
	public Boolean isVowel()
	{
		return (ch == 'a' || ch=='e' || ch == 'i'
				|| ch == 'o' || ch =='u') || (ch == 'A' || ch=='e' || ch == 'I'
				|| ch == 'O' || ch =='U') ;
	}
	public Boolean isConsonant()
	{
		return (!isVowel()&& isAlphabet());
	}
	public Boolean isNumber()
	{
		return ch >= 48 && ch<=57;
	}
	public Boolean isAlphabet()
	{
		return Character.isAlphabetic(ch);
	}
	
	public void printLowerCaseAlphabets()
	{
		System.out.println(Character.toLowerCase(ch));
	}
	public void printUpperCaseAlphabets()
	{
		System.out.println(Character.toUpperCase(ch));
	}
}
