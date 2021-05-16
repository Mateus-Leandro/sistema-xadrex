package xadrex;

import tabuleiro.Peca;
import tabuleiro.Posicao;
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
	
	protected boolean pecaDoOponente(Posicao posicao) {
		PecaXadrex p = (PecaXadrex) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
