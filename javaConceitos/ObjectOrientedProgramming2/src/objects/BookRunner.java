package objects;

public class BookRunner {
	public static void main(String[] args) {
		Book book  = new Book(1, "O Corvo", "poe");
		book.addReview(new Reviews(10, "great", 5));
		book.addReview(new Reviews(5, "medium",3));
		
		System.out.println(book);
	}
}
