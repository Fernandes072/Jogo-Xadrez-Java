package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {
	
	private int coluna;
	private char linha;
	
	public PosicaoXadrez(char linha, int coluna) {
		if (linha < 'A' || linha > 'H' || coluna < 1 || coluna > 8) {
			throw new XadrezException("Erro ao instanciar posição. Valores válidos são de A1 até H8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}
	
	public int getColuna() {
		return coluna;
	}

	public char getLinha() {
		return linha;
	}
	
	protected Posicao toPosicao() {
		return new Posicao('H' - linha, coluna - 1);
	}
	
	protected static PosicaoXadrez fromPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('H' - posicao.getLinha()), posicao.getColuna() - 1);
	}
	
	@Override
	public String toString() {
		return "" + linha + coluna;
	}
}