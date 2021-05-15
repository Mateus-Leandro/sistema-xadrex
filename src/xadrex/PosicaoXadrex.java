package xadrex;

import tabuleiro.Posicao;

public class PosicaoXadrex {

	private char coluna;
	private int linha;

	public PosicaoXadrex(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExcecoesXadrex("Erro ao instanciar a posição:" + " os valores válidos são de a1 até h8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}

	protected static PosicaoXadrex fromPosicao(Posicao posicao) {
		return new PosicaoXadrex((char) ('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}

}
