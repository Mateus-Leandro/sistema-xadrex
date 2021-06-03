package xadrex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrex.pecas.Bispo;
import xadrex.pecas.Cavalo;
import xadrex.pecas.Peao;
import xadrex.pecas.Rainha;
import xadrex.pecas.Rei;
import xadrex.pecas.Torre;

public class PartidaXadrex {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrex vuneravelEnPassant;
	private PecaXadrex promocao;

	private List<Peca> pecasDoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrex() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		configuracaoInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getcheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrex getVuneravelEnPassant() {
		return vuneravelEnPassant;
	}

	public PecaXadrex getPromocao() {
		return promocao;
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

	public boolean[][] movimentosPossiveis(PosicaoXadrex origem) {
		Posicao posicao = origem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	public PecaXadrex performMoverPeca(PosicaoXadrex posicaoOrigem, PosicaoXadrex posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca capturaPeca = moverPeca(origem, destino);

		if (testeCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturaPeca);
			throw new ExcecoesXadrex("Voce nao pode se colocar em check!");
		}

		PecaXadrex pecaMovida = (PecaXadrex) tabuleiro.peca(destino);

		// #Specialmove promocao
		promocao = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0
					|| pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promocao = (PecaXadrex)tabuleiro.peca(destino);
				promocao = trocarPecaPromocao("R");
			}
		}

		check = (testeCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// #Specialmove en passant
		if (pecaMovida instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			vuneravelEnPassant = pecaMovida;
		} else {
			vuneravelEnPassant = null;
		}

		return (PecaXadrex) capturaPeca;
	}

	
	public PecaXadrex trocarPecaPromocao(String tipo) {
		if(promocao == null) {
			throw new IllegalStateException("Nao ha peca para ser promovida!");
		}
		if(!tipo.equals("R") && !tipo.equals("T") && !tipo.equals("C") && !tipo.equals("B")) {
			return promocao;
		}
		
		Posicao pos = promocao.getPosicaoXadrex().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasDoTabuleiro.remove(p);
		
		PecaXadrex novaPeca = novaPeca(tipo, promocao.getCor());	
		tabuleiro.ColocarPeca(novaPeca, pos);
		pecasDoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	
	private PecaXadrex novaPeca(String tipo, Cor cor) {
		if(tipo.equals("R")) return new Rainha(tabuleiro, cor);
		if(tipo.equals("T")) return new Torre(tabuleiro, cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Bispo(tabuleiro, cor);
	}
	
	
	private Peca moverPeca(Posicao origem, Posicao destino) {
		PecaXadrex p = (PecaXadrex) tabuleiro.removerPeca(origem);
		p.incrementaMovimento();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.ColocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasDoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// #Specialmove castling kingside rook
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrex torre = (PecaXadrex) tabuleiro.removerPeca(origemT);
			tabuleiro.ColocarPeca(torre, destinoT);
			torre.incrementaMovimento();
		}

		// #Specialmove castling queenside rook
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrex torre = (PecaXadrex) tabuleiro.removerPeca(origemT);
			tabuleiro.ColocarPeca(torre, destinoT);
			torre.incrementaMovimento();
		}

		// #Specialmove en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasDoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrex p = (PecaXadrex) tabuleiro.removerPeca(destino);
		p.decrementaMovimento();
		tabuleiro.ColocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.ColocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasDoTabuleiro.add(pecaCapturada);
		}

		// #Specialmove castling kingside rook
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrex torre = (PecaXadrex) tabuleiro.removerPeca(destinoT);
			tabuleiro.ColocarPeca(torre, origemT);
			torre.decrementaMovimento();
		}

		// #Specialmove castling queenside rook
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrex torre = (PecaXadrex) tabuleiro.removerPeca(destinoT);
			tabuleiro.ColocarPeca(torre, origemT);
			torre.decrementaMovimento();
		}

		// #Specialmove en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == vuneravelEnPassant) {
				PecaXadrex peao = (PecaXadrex) tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.ColocarPeca(peao, posicaoPeao);
			}
		}

	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.possuiPecaPosicao(posicao)) {
			throw new ExcecoesXadrex("Nao existe peca nesta posicao!");
		}
		if (jogadorAtual != ((PecaXadrex) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecoesXadrex("A peca escolhida nao e sua!");
		}
		if (!tabuleiro.peca(posicao).existePossibilidadeMovimento()) {
			throw new ExcecoesXadrex("Nao existe movimentos possiveis para a peca escolhida");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentosPossiveis(destino)) {
			throw new ExcecoesXadrex("A peca escolhida nao pode se mover para a posicao de destino!");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrex rei(Cor cor) {
		List<Peca> list = pecasDoTabuleiro.stream().filter(x -> ((PecaXadrex) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrex) p;
			}
		}
		throw new IllegalStateException("Nao existe o rei da cor " + cor + " no tabuleiro.");
	}

	private boolean testeCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrex().toPosicao();
		List<Peca> pecasOponente = pecasDoTabuleiro.stream().filter(x -> ((PecaXadrex) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasDoTabuleiro.stream().filter(x -> ((PecaXadrex) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrex) p).getPosicaoXadrex().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = moverPeca(origem, destino);
						boolean testeCheck = testeCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrex peca) {
		tabuleiro.ColocarPeca(peca, new PosicaoXadrex(coluna, linha).toPosicao());
		pecasDoTabuleiro.add(peca);
	}

	private void configuracaoInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}
}
