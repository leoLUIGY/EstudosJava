package objects;

public class CustomerRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Address homeAddress = new Address("line 1", "cajamar", "07792060");
		Address workAddress = new Address("line 2", "Alphavile", "970790909");
		Customer customer = new Customer("Leonidas", homeAddress);
		customer.setWorkAddress(workAddress);
		
		System.out.println(customer);

	}

}
