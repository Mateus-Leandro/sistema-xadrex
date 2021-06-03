package aplicacao;

import java.awt.Checkbox;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrex.Cor;
import xadrex.PartidaXadrex;
import xadrex.PecaXadrex;
import xadrex.PosicaoXadrex;
import xadrex.pecas.Rei;

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

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrex lerPosicaoXadrex(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrex(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler posicao!" + " Valores validos sao de A1 ata H8");
		}
	}

	public static void printPartida(PartidaXadrex partida, List<PecaXadrex> capturadas) {
		printTabuleiro(partida.getPecas(), partida.getcheck());
		System.out.println();
		printPecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partida.getTurno());
		if (!partida.getCheckMate()) {
			System.out.println("Esperando jogador: " + partida.getJogadorAtual());
			if (partida.getcheck()) {
				System.out.println(ANSI_RED_BACKGROUND);
				System.out.print("CHECK!");
				System.out.println(ANSI_RESET);
			}
		} else {
			System.out.println(ANSI_RED_BACKGROUND);
			System.out.println("CHECKMATE!");
			System.out.println(ANSI_RESET);
			System.out.println("Ganhador: " + partida.getJogadorAtual());
		}

	}

	public static void printTabuleiro(PecaXadrex[][] pecas, boolean check) {
		for (int l = 0; l < pecas.length; l++) {
			System.out.print((8 - l) + " ");
			for (int c = 0; c < pecas.length; c++) {
				printPeca(pecas[l][c], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printTabuleiro(PecaXadrex[][] pecas, boolean[][] movimentosPossiveis) {
		for (int l = 0; l < pecas.length; l++) {
			System.out.print((8 - l) + " ");
			for (int c = 0; c < pecas.length; c++) {
				printPeca(pecas[l][c], movimentosPossiveis[l][c]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPeca(PecaXadrex peca, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}

		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
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

	private static void printPecasCapturadas(List<PecaXadrex> capturadas) {
		List<PecaXadrex> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCO)
				.collect(Collectors.toList());
		List<PecaXadrex> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());

		System.out.println("Pecas capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.print(Arrays.toString(brancas.toArray()));
		System.out.println(ANSI_RESET);

		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.print(Arrays.toString(pretas.toArray()));
		System.out.println(ANSI_RESET);
	}

}
