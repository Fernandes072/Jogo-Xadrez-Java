package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	private boolean xeque;
	private boolean xequeMate;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.VERMELHO;
		configuracaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez executarJogada(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = moverPeca(origem, destino);
		if (testeXeque(jogadorAtual)) {
			desfazerJogada(origem, destino, pecaCapturada);
			throw new XadrezException("Você não pode se colocar em xeque.");
		}
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		if(testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca moverPeca(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
		p.incrementarContadorMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);
		if (pecaCapturada != null) {
			pecasCapturadas.add(pecaCapturada);
			pecasNoTabuleiro.remove(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void desfazerJogada(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
		p.decrementarContadorMovimentos();
		tabuleiro.lugarPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem.");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua.");
		}
		if (!tabuleiro.peca(posicao).haMovimentoPossivel()) {
			throw new XadrezException("Não existe movimentos possíveis para essa peça.");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode ser movida para a posição de destino.");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.VERMELHO) ? Cor.AZUL : Cor.VERMELHO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.VERMELHO) ? Cor.AZUL : Cor.VERMELHO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei da cor " + cor + " no tabuleiro");
	}
	
	private boolean testeXeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeXequeMate(Cor cor) {
		if (!testeXeque(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i,j);
						Peca pecaCapturada = moverPeca(origem, destino);
						boolean testeXeque = testeXeque(cor);
						desfazerJogada(origem, destino, pecaCapturada);
						if (!testeXeque) {
							return false;
						}
						
					}
				}
			}
		}
		
		return true;
	}
	
	private void colocarNovaPeca(char linha, int coluna, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(linha, coluna).toPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void configuracaoInicial() {
		colocarNovaPeca('A', 5, new Rei(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('A', 1, new Torre(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('A', 8, new Torre(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('A', 2, new Cavalo(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('A', 7, new Cavalo(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('A', 3, new Bispo(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('A', 6, new Bispo(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 1, new Peao(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 2, new Peao(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 3, new Peao(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 4, new Peao(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 5, new Peao(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 6, new Peao(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 7, new Peao(tabuleiro, Cor.VERMELHO));
		colocarNovaPeca('B', 8, new Peao(tabuleiro, Cor.VERMELHO));
		
		colocarNovaPeca('H', 5, new Rei(tabuleiro, Cor.AZUL));
		colocarNovaPeca('H', 1, new Torre(tabuleiro, Cor.AZUL));
		colocarNovaPeca('H', 8, new Torre(tabuleiro, Cor.AZUL));
		colocarNovaPeca('H', 2, new Cavalo(tabuleiro, Cor.AZUL));
		colocarNovaPeca('H', 7, new Cavalo(tabuleiro, Cor.AZUL));
		colocarNovaPeca('H', 3, new Bispo(tabuleiro, Cor.AZUL));
		colocarNovaPeca('H', 6, new Bispo(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 1, new Peao(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 2, new Peao(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 3, new Peao(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 4, new Peao(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 5, new Peao(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 6, new Peao(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 7, new Peao(tabuleiro, Cor.AZUL));
		colocarNovaPeca('G', 8, new Peao(tabuleiro, Cor.AZUL));
		
	}
}