package xadrex;

import tabuleiro.Peca;
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

	public PecaXadrex performMoverPeca(PosicaoXadrex posicaoOrigem, PosicaoXadrex posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		Peca capturaPeca = moverPeca(origem, destino);
		return (PecaXadrex)capturaPeca;
	}
	
	private Peca moverPeca(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca peca_capturada = tabuleiro.removerPeca(destino);
		tabuleiro.ColocarPeca(p, destino);
		return peca_capturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.possuiPecaPosicao(posicao)) {
			throw new ExcecoesXadrex("Nao existe peca nesta posicao!");
		}
	}
	
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrex peca) {
		tabuleiro.ColocarPeca(peca, new PosicaoXadrex(coluna, linha).toPosicao());
	}

	private void configuracaoInicial() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));

	}
}
