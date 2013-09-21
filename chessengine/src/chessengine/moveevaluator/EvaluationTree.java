package chessengine.moveevaluator;

// LinkedList bis wir unsere doppelt verkettete haben
import java.util.LinkedList;

import chessengine.Board;
import chessengine.BoardInterface;


public class EvaluationTree implements EvaluationTreeInterface {
	
	public String bestZug; //Bester, derzeitiger Zug, enthaellt FEN-String
	private Board currentBoard; //Aktuelles Board aus der Boardklasse
	private String fen; // FEN-String des Boards
	private boolean weissAmZug; // ich fand doch bool besser, true = weiss, false = schwarz
	private int bewertung; // Bewertung des Boards
	private LinkedList<EvaluationTreeNode> kinder; // Liste der Kinder
	private EvaluationTreeNodeInterface parent; // Parent fuer die rekursion
	
	/* (non-Javadoc)
	 * @see chessengine.moveevaluator.EvaluationTreeInterface#MinMax()
	 */
	@Override
	public void MinMax(){
		
	}
	
	/* (non-Javadoc)
	 * @see chessengine.moveevaluator.EvaluationTreeInterface#bewertung()
	 */
	@Override
	public void bewertung(){
		
	}
	
	
	/* (non-Javadoc)
	 * @see chessengine.moveevaluator.EvaluationTreeInterface#getCurrentBoard()
	 */
	@Override
	public void getCurrentBoard() {
		
		this.currentBoard;
		
	}
	
	/* (non-Javadoc)
	 * @see chessengine.moveevaluator.EvaluationTreeInterface#setCurrentBoard()
	 */
	@Override
	public void setCurrentBoard() {
		
	}
	
	/* (non-Javadoc)
	 * @see chessengine.moveevaluator.EvaluationTreeInterface#macheZug()
	 */
	@Override
	public void macheZug() {
		
	}
	
	/* (non-Javadoc)
	 * @see chessengine.moveevaluator.EvaluationTreeInterface#macheFakeZug()
	 */
	@Override
	public void macheFakeZug () {
		
	}
	
	/* (non-Javadoc)
	 * @see chessengine.moveevaluator.EvaluationTreeInterface#macheStartZug()
	 */
	@Override
	public void macheStartZug() {
		
	}
	
}
