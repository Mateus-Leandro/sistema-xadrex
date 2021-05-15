package xadrex;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrex.pecas.Rei;
import xadrex.pecas.Torre;

public class PartidaXadrex {

	private Tabuleiro tabuleiro;

	public PartidaXadrex() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}

	public PecaXadrex[][] getPecas() {
		PecaXadrex[][] mat = new PecaXadrex[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int l = 0; l < tabuleiro.getLinhas(); l++) {
			for (int c = 0; c < tabuleiro.getColunas(); c++) {
				mat[l][c] = (PecaXadrex) tabuleiro.peca(l, c);
			}
		}
		return mat;
	}
	
	private void configuracaoInicial() {
		tabuleiro.ColocarPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
		tabuleiro.ColocarPeca(new Rei(tabuleiro, Cor.PRETO), new Posicao(0, 4));
		tabuleiro.ColocarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
		
	}
}
