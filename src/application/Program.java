package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	
	public static void main(String[] args) {
		//Board board = new Board(8,8);
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		//UI.printBoard(chessMatch.getPieces());
		//movimentar pe�as;
		
		
		while(true) {
			try {
				UI.clearScreen();
				//UI.printBoard(chessMatch.getPieces());
				UI.printMatch(chessMatch);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves); //resp por imprimir o tabuleiro colorido
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		
	}
	
}
