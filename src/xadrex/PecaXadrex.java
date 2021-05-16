package xadrex;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrex extends Peca{
	
	private Cor cor;

	public PecaXadrex(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

}
