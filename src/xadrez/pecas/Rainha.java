package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		// Para cima
		p.setPosicao(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Para esquerda
		p.setPosicao(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Para direita
		p.setPosicao(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Para baixo
		p.setPosicao(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Para nordeste
				p.setPosicao(posicao.getLinha() - 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setPosicao(p.getLinha() - 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Para noroeste
				p.setPosicao(posicao.getLinha() - 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setPosicao(p.getLinha() - 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Para sudeste
				p.setPosicao(posicao.getLinha() + 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setPosicao(p.getLinha() + 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Para sudoeste
				p.setPosicao(posicao.getLinha() + 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setPosicao(p.getLinha() + 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haUmaPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				return mat;
	}

}
