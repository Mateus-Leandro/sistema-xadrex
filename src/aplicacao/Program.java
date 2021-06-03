package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrex.ExcecoesXadrex;
import xadrex.PartidaXadrex;
import xadrex.PecaXadrex;
import xadrex.PosicaoXadrex;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrex partida = new PartidaXadrex();
		List<PecaXadrex> capturadas = new ArrayList<>();

		UI novaUI = new UI();

		while (!partida.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partida, capturadas);
				System.out.println();

				System.out.print("Selecione a peca: ");
				PosicaoXadrex origem = UI.lerPosicaoXadrex(sc);

				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.limparTela();
				UI.printTabuleiro(partida.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.print("Informe a posicao de destino: ");
				PosicaoXadrex destino = UI.lerPosicaoXadrex(sc);

				PecaXadrex pecaCapturada = partida.performMoverPeca(origem, destino);

				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}

				if (partida.getPromocao() != null) {

					System.out.println("Informe a peca que deseja " + novaUI.ANSI_YELLOW + "inserir no lugar do peao "
							+ novaUI.ANSI_RESET + "promovido: " + novaUI.ANSI_YELLOW + "(T/B/R/C)" + novaUI.ANSI_RESET);
					String tipo = sc.nextLine();
					partida.trocarPecaPromocao(tipo);
				}

			} catch (ExcecoesXadrex e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.printPartida(partida, capturadas);
	}
}
