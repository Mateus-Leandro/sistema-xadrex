package aplicacao;

import xadrex.Cor;
import xadrex.PecaXadrex;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
		}

		else {
			if (peca.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			}

			else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}

		System.out.print(" ");
	}

}
