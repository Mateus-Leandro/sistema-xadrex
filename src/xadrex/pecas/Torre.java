package xadrex.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrex.Cor;
import xadrex.PecaXadrex;

public class Torre extends PecaXadrex {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Acima
		p.setPosicao(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda
		p.setPosicao(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Direita
		p.setPosicao(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Abaixo
		p.setPosicao(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
