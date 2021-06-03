package xadrex.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrex.Cor;
import xadrex.PecaXadrex;

public class Peao extends PecaXadrex {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setPosicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setPosicao(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)
					&& getTabuleiro().existePosicao(p2) && !getTabuleiro().possuiPecaPosicao(p2)
					&& getContadorDeMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setPosicao(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setPosicao(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}

		else {
			p.setPosicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setPosicao(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().possuiPecaPosicao(p)
					&& getTabuleiro().existePosicao(p2) && !getTabuleiro().possuiPecaPosicao(p2)
					&& getContadorDeMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setPosicao(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setPosicao(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && pecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
