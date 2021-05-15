package aplicacao;

import xadrex.PecaXadrex;

public class UI {

	public static void printTabuleiro(PecaXadrex[][] pecas) {
		for (int l = 0; l < pecas.length; l++) {
			System.out.print((8 - l) + " ");
			for (int c = 0; c < pecas.length; c++) {
				printPeca(pecas[l][c]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}

	private static void printPeca(PecaXadrex peca) {
		if (peca == null) {
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}

}
