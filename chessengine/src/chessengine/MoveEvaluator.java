package chessengine;


public class MoveEvaluator {
	//Variablendefinitionen
	private Board currentBoard;
	private Board predictionBoard;
	private int depth;
	private int time;
	private int timeRemaining;
	
	public String IncomingFEN;
	public String OutgoingFEN;
	
	
	
	void setCurrentBoard() {
		currentBoard = Board.getBoard();
		
	}
	
	void setPreditionBoard() {
		while (movementlist != null){
				predictionBoard = new Board();
				
		}
		
	}

}
