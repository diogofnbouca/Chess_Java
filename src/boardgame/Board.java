package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	public Board(int rows, int columns) {
		
		if(rows<1 || columns <1) {
			throw new BoardException("Error creating board: there must be at list 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
		
	}
	public int getColumns() {
		return columns;
	}
//	public void setColumns(int columns) {
//		this.columns = columns;
//	}
	public int getRows() {
		return rows;
	}
//	public void setRows(int rows) {
//		this.rows = rows;
//	}
//	
	//retorna matriz peça na linha x coluna x
	public Piece piece(int row, int column) {
		if(!positionExists(row, column))
			throw new BoardException("Position not on the board");
		return pieces[row][column];
	}
	
	//override do metodo anterior mas agr recebe Position e retorna Position
	public Piece piece(Position position) {
		if(!positionExists(position))
			throw new BoardException("Position not on the board");
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		//na matriz de peças na posicao dada vai atribuir na posicao da matriz a peça que vem como argumento
		
		if(thereIsAPiece(position))
			throw new BoardException ("There is already a piece on position" + position);
		pieces[position.getRow()][position.getColumn()]=piece;
		piece.position=position; //informar a posição!!1
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position))
			throw new BoardException("Position not on the board");
		if (piece(position)==null)
			return null;
		Piece aux = piece(position);
		aux.position=null;
		pieces[position.getRow()][position.getColumn()]=null;
		return aux;
	}
	
	private boolean positionExists(int row, int col) {
		//row <=00 e row<rows && coluna maior ou igual a zero e tmb tem k ser maior que a qtd de cols do tab
		
		return row>=0 && row<rows && col >=0 && col < columns;
	}
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position))
			throw new BoardException("Position not on the board");
		return piece(position)!= null;
	}
	
	
	
	
}
