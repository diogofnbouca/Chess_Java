package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	//Classe chave. Vai ter as regras do xadrez
	
	private Board board;
	private int turn;
	private Color currentPlayer;
	
	private List<Piece> piecesOnTheBoard = new ArrayList();
	private List<Piece> capturedPieces = new ArrayList();
	
	private boolean check;
	private boolean checkMate;
	
	public ChessMatch() {
		board = new Board(8,8); //
		turn =1;
		currentPlayer=Color.WHITE;
		initialSetup();
	}
	

	public boolean getCheck() {
		return check;
	}
	public boolean getCheckMate() {
		return checkMate;
	}
	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
	}




	public ChessPiece[][] getPieces(){
		//Distinçaõ entre a camada chess e board. quero saber  a peça do chess e não bpard
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows();i++)
			for (int j=0; j<board.getColumns();j++)
				mat[i][j]=(ChessPiece) board.piece(i, j);
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validadeSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validadeSourcePosition(source); //resp por validar a posicao de origem
		validadeTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target); //resp por realizar mov da peça
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece); //desfazer o movimento se se meter em check
			throw new ChessException("You can't put yourself in check");
		}
		
		//testar se oponente ficou em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer)))
			checkMate=true;
		else nexTurn();
		
		return (ChessPiece)capturedPiece;
	}
		
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); //remove peça da pos origem
		Piece capturedPiece = board.removePiece(target); //remove peça da posivel pos de origem
		board.placePiece(p, target); //colocar no destino
		
		if(capturedPiece!=null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
			//piecesOnTheBoard.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target); //remove peça do destino
		board.placePiece(p, source);
		
		if(capturedPiece!=null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			
		}
	}
	
	private void validadeSourcePosition(Position position) {
		if(!board.thereIsAPiece(position))
			throw new ChessException("There is no piece on source position");
		
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor())
			throw new ChessException ("The chosen piece is not yours");
		
		if(!board.piece(position).isThereAnyPossibleMove())
			throw new ChessException("There is no possible moves for the chosen piece");
	}
	
	private void validadeTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target))
			throw new ChessException("The chosen piece can't move to target position");
	}
	
	private Color opponent (Color color) {
		 return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king (Color color) {
		List <Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p: list) {
			if (p instanceof King)
				return (ChessPiece)p;
		}
		throw new IllegalStateException("There is no "+ color+ " king on the board");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color))
			return false;
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for(int i=0; i<board.getRows();i++) {
				for (int j=0;j<board.getColumns();j++) {
					//VER SE EXISTE ALGUMA PEÇA QUE PERMITA TIRAR O CHECKMATE
					if(mat[i][j]) {
						//essa posicao tira do check
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						
						Piece capturedPiece = makeMove(source, target);
						
						//testar se ainda ta em check
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece); //NECESSARIO FAZER PQ SÓ COLOCAMOS A PEÇA PARA TESTAR!!!
						
						//testar
						if (!testCheck)
							return false;
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void nexTurn() {
		turn++;
		currentPlayer= (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void initialSetup() {
		//inciiar a partida do xadrez colocando as peças no tabuleiro
//		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
		//placeNewPiece('b',6,new Rook(board, Color.WHITE));
		//placeNewPiece('e',8,new King(board, Color.BLACK));
		
		placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
	}
}
