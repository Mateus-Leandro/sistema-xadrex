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
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrex peca) {
		tabuleiro.ColocarPeca(peca,new PosicaoXadrex(coluna, linha).toPosicao());
	}
	
	private void configuracaoInicial() {
		colocarNovaPeca('b',6, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		
	}
}
