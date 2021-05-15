package xadrex;

import tabuleiro.Tabuleiro;

public class PartidaXadrex {

	private Tabuleiro tabuleiro;

	public PartidaXadrex() {
		tabuleiro = new Tabuleiro(8, 8);
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
}
