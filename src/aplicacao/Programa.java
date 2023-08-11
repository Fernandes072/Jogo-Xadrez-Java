package aplicacao;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		
		while (true) {
			UI.exibirTabuleiro(partida.getPecas());
			
			System.out.println();
			System.out.print("Jogada(O D): ");
			PosicaoXadrez jogada[] = UI.lerPosicaoXadrez(sc);
			
			PecaXadrez pecaCapturada = partida.executarJogada(jogada[0], jogada[1]);
			System.out.println();
		}
		
	}
}
