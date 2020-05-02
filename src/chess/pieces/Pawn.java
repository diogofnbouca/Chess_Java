package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		
		//logica dos possiveis posicoes
		Position p = new Position(0,0);
		
		
		//peao branco
		if(getColor()== Color.WHITE) {
			p.setValues(position.getRow()-1, position.getColumn()); //posicao acima = REGRA GERAL
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//1ª vez (onde pode jogar 2 casas)
			p.setValues(position.getRow()-2, position.getColumn()); //posicao acima;
			Position p2=new Position(position.getRow()-1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount()==0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//DIAGONAIS
			p.setValues(position.getRow()-1, position.getColumn()-1); 
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow()-1, position.getColumn()+1); 
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		else {
			p.setValues(position.getRow()+1, position.getColumn()); //posicao acima = REGRA GERAL
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//1ª vez (onde pode jogar 2 casas)
			p.setValues(position.getRow()+2, position.getColumn()); //posicao acima;
			Position p2=new Position(position.getRow()+1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount()==0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//DIAGONAIS
			p.setValues(position.getRow()+1, position.getColumn()-1); 
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow()+1, position.getColumn()+1); 
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		return mat;
				
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
