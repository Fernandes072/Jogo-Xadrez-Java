package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.Cor;
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

	public static PosicaoXadrez[] lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			PosicaoXadrez posicoes[] = new PosicaoXadrez[2];
			int i = 0;
			for(String pos : s.split(" ")) {
				char linha = pos.charAt(0);
				int coluna = Integer.parseInt(pos.substring(1));
				posicoes[i] = new PosicaoXadrez(linha, coluna);
				i++;
			}
			return posicoes;
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler posição de xadrez. Valores válidos de A1 até H8.");
		}
	}

	public static void exibirTabuleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((char)('H' - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				exibirPeca(pecas[i][j]);
			}
			System.out.println();
		}
		System.out.println("  1 2 3 4 5 6 7 8");
	}

	private static void exibirPeca(PecaXadrez peca) {
		if (peca == null) {
			System.out.print("-");
		} else {
			if (peca.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_RED + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_BLUE + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
}