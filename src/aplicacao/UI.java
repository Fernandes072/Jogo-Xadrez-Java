package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void limparTela() {
		// System.out.print("\033\143");
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler posição de xadrez. Valores válidos de A1 até H8.");
		}
	}

	public static void exibirPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturadas) {
		exibirTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		exibirPecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		if (!partidaXadrez.getXequeMate()) {
			System.out.println("Aguardando jogador: " + partidaXadrez.getJogadorAtual());
			if (partidaXadrez.getXeque()) {
				System.out.println("XEQUE!");
			}
		} else {
			System.out.println("XEQUE-MATE!");
			System.out.println("Vencedor: " + partidaXadrez.getJogadorAtual());
		}

	}

	public static void exibirTabuleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((char) ('H' - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				exibirPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  1 2 3 4 5 6 7 8");
	}

	public static void exibirTabuleiro(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((char) ('H' - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				exibirPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  1 2 3 4 5 6 7 8");
	}

	private static void exibirPeca(PecaXadrez peca, boolean fundo) {
		if (fundo) {
			System.out.print(ANSI_YELLOW_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor.VERMELHO) {
				System.out.print(ANSI_RED + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_BLUE + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	private static void exibirPecasCapturadas(List<PecaXadrez> capturadas) {
		List<PecaXadrez> vermelho = capturadas.stream().filter(x -> x.getCor() == Cor.VERMELHO)
				.collect(Collectors.toList());
		List<PecaXadrez> azul = capturadas.stream().filter(x -> x.getCor() == Cor.AZUL).collect(Collectors.toList());
		System.out.println("Peças capturadas: ");
		System.out.print("Vermelho: ");
		System.out.print(ANSI_RED);
		System.out.println(Arrays.toString(vermelho.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Azul: ");
		System.out.print(ANSI_BLUE);
		System.out.println(Arrays.toString(azul.toArray()));
		System.out.print(ANSI_RESET);

	}
}