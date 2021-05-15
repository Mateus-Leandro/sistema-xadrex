package aplicacao;

import xadrex.PartidaXadrex;

import aplicacao.UI;

public class Program {

	public static void main(String[] args) {

		PartidaXadrex partida = new PartidaXadrex();
		
		UI.printTabuleiro(partida.getPecas());
	}

}
