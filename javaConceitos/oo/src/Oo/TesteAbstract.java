package Oo;

import java.util.Scanner;

abstract class TesteAbstract {
	public void iniciarJogo() {
		System.out.println("precino A para iniciar ou B para sair");
		Scanner n =  new Scanner(System.in);
		String escolha = n.nextLine();
		
		if (escolha == "a") {
			sairJogo();
		} else {
			System.out.println("voce saiu");
		}
	}
	
	private void sairJogo() {
		System.out.println("o jogo deu erro!!!");
	}
}
