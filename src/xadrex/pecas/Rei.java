package xadrex.pecas;

import tabuleiro.Tabuleiro;
import xadrex.Cor;
import xadrex.PecaXadrex;

public class Rei extends PecaXadrex {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "R";
	}

}
