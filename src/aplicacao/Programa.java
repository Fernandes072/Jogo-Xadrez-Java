package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		while (!partida.getXequeMate()) {
			try {
			UI.limparTela();
			UI.exibirPartida(partida, capturadas);
			
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
			boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
			UI.limparTela();
			UI.exibirTabuleiro(partida.getPecas(), movimentosPossiveis);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

			PecaXadrez pecaCapturada = partida.executarJogada(origem, destino);
			
			if (pecaCapturada != null) {
				capturadas.add(pecaCapturada);
			}
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				System.out.print("Pressione Enter para continuar!");
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				System.out.print("Pressione Enter para continuar!");
				sc.nextLine();
			} finally {
				System.out.println();
			}
			
		}
		UI.limparTela();
		UI.exibirPartida(partida, capturadas);
	}
}
