package tabuleiro;

public abstract class Peca {
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	private boolean estadoCheck;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public void setEstadoCheck(boolean estadoCheck) {
		this.estadoCheck = estadoCheck;
	}
	
	public boolean getEstadoCheck() {
		return estadoCheck;
	}

	public abstract boolean[][] movimentosPossiveis();

	public boolean movimentosPossiveis(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean existePossibilidadeMovimento() {
		boolean[][] mat = movimentosPossiveis();
		for (int l = 0; l < mat.length; l++) {
			for (int c = 0; c < mat.length; c++) {
				if (mat[l][c]) {
					return true;
				}
			}
		}
		return false;
	}
}
