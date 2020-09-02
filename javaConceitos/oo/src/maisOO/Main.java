package maisOO;

public class Main extends Player{
	private String nome = "leo";
	private int[] numeros;
	public static final int VIDA_MAXIMA = 100;
	/*private static int exemplo() {
		return 10;
	}
	
	public void outroMetodo(int[] n1, String[] nome) {
		System.out.println(n1[0]);
		System.out.println(nome[0]);
	}
	*/
	public Main(int n1, int n2){
		super(n1, n2);
		this.vidaInicial =100;
		numeros =  new int[100];
		System.out.println(this.nome);
	}
	
	public String getNome() {
		return nome;
	}
	
	public static void main(String[] args) {
		/*new Player2().nascer();
		Player2 player2 = new Player2();
		
		player2.nascer();
		exemplo();
		int[] n1 = new int[10];
		n1[0] = 10;
		
		String[] nomes = new String[2];
		nomes[0] = "leo";
		System.out.println(exemplo());
		new Main().outroMetodo(n1, nomes);
	*/
		Main m =  new Main(10, 20);
		System.out.println(m.getNome());
		Player player = new Player(1000, 10);
		System.out.println(player.vidaInicial);
	}
	
	
}
