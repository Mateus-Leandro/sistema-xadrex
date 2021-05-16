package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrex.ExcecoesXadrex;
import xadrex.PartidaXadrex;
import xadrex.PecaXadrex;
import xadrex.PosicaoXadrex;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrex partida = new PartidaXadrex();

		while (true) {
			try {
				UI.limparTela();
				UI.printTabuleiro(partida.getPecas());
				System.out.println();

				System.out.print("Selecione a peca: ");
				PosicaoXadrex origem = UI.lerPosicaoXadrex(sc);
				
				boolean [][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.limparTela();
				UI.printTabuleiro(partida.getPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Informe a posicao de destino: ");
				PosicaoXadrex destino = UI.lerPosicaoXadrex(sc);

				PecaXadrex pecaCapturada = partida.performMoverPeca(origem, destino);
			} catch (ExcecoesXadrex e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}
}
