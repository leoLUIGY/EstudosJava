package introduction;

public class Book {
	private int noOfCopies;
	
	Book(int noOfCopies){
		this.noOfCopies = noOfCopies;
	}
	
	public void setNoOfCopies(int noOfCopies)
	{
		if(noOfCopies>= 0)
			this.noOfCopies = noOfCopies;
	}
	
	public int getNoOfCopies()
	{
		return this.noOfCopies;
	}
	
	public void increaseNoOfCopies(int howMuch) {
		setNoOfCopies(this.noOfCopies+howMuch);
	}
	public void decreaseNoOfCopies(int howMuch) {
		setNoOfCopies(this.noOfCopies-howMuch);
	}

}
