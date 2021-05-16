package aplicacao;

import java.util.Scanner;

import xadrex.PartidaXadrex;
import xadrex.PecaXadrex;
import xadrex.PosicaoXadrex;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrex partida = new PartidaXadrex();
		
		while(true) {
			UI.printTabuleiro(partida.getPecas());
			System.out.println();
			
			System.out.print("Selecione a peca: ");
			PosicaoXadrex origem = UI.lerPosicaoXadrex(sc);
			
			System.out.print("Informe a posicao de destino: ");
			PosicaoXadrex destino = UI.lerPosicaoXadrex(sc);
			
			PecaXadrex pecaCapturada = partida.performMoverPeca(origem, destino);
		}
	}
}
