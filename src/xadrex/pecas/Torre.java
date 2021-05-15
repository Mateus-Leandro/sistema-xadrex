package xadrex.pecas;

import tabuleiro.Tabuleiro;
import xadrex.Cor;
import xadrex.PecaXadrex;

public class Torre extends PecaXadrex{

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "T";
	}

}
