package xadrex;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrex extends Peca {

	private Cor cor;
	private int contadorDeMovimentos;

	public PecaXadrex(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContadorDeMovimentos() {
		return contadorDeMovimentos;
	}
	
	public void incrementaMovimento() {
		contadorDeMovimentos++;
	}
	
	public void decrementaMovimento() {
		contadorDeMovimentos--;
	}

	public PosicaoXadrex getPosicaoXadrex() {
		return PosicaoXadrex.fromPosicao(posicao);
	}

	protected boolean pecaDoOponente(Posicao posicao) {
		PecaXadrex p = (PecaXadrex) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
