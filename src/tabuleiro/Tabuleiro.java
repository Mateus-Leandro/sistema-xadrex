package tabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExcecoesTabuleiro(
					"Erro ao criar tabuleiro: � necessario que exista" + " pelo menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {
		if (!existePosicao(linha, coluna)) {
			throw new ExcecoesTabuleiro("Posicao inexistente no tabuleiro! ");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new ExcecoesTabuleiro("Posicao inexistente no tabuleiro! ");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public void ColocarPeca(Peca peca, Posicao posicao) {
		if (possuiPecaPosicao(posicao)) {
			throw new ExcecoesTabuleiro("Ja existe uma pe�a na posicao: " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if(!existePosicao(posicao)) {
			throw new ExcecoesTabuleiro("Essa posicao nao existe no tabuleiro! ");
		}
		if(peca(posicao) == null) {
			return null;
		}
		
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}

	private boolean existePosicao(int linha, int coluna) {
		return linha >= 0 && linha <= linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean existePosicao(Posicao posicao) {
		return existePosicao(posicao.getLinha(), posicao.getColuna());
	}

	public boolean possuiPecaPosicao(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new ExcecoesTabuleiro("Posicao inexistente no tabuleiro! ");
		}
		return peca(posicao) != null;
	}
}
