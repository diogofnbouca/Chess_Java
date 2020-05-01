package chess;

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
	
	public ChessMatch() {
		board = new Board(8,8); //
		turn =1;
		currentPlayer=Color.WHITE;
		initialSetup();
	}
	

	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
	}




	public ChessPiece[][] getPieces(){
		//Distin�a� entre a camada chess e board. quero saber  a pe�a do chess e n�o bpard
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
		Piece capturedPiece = makeMove(source, target); //resp por realizar mov da pe�a
		nexTurn();
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); //remove pe�a da pos origem
		Piece capturedPiece = board.removePiece(target); //remove pe�a da posivel pos de origem
		board.placePiece(p, target);
		return capturedPiece;
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
	
	
	

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void nexTurn() {
		turn++;
		currentPlayer= (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void initialSetup() {
		//inciiar a partida do xadrez colocando as pe�as no tabuleiro
//		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
		//placeNewPiece('b',6,new Rook(board, Color.WHITE));
		//placeNewPiece('e',8,new King(board, Color.BLACK));
		
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
