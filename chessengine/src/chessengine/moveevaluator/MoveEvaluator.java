package chessengine.moveevaluator;

import chessengine.movegenerator.MoveGenerator;
import chessengine.Board;

/**
 * ToDo Kommentar ergaenzen
 * @author 
 */
public class MoveEvaluator {
	//Variablendefinitionen
	private Board currentBoard;
	private Board predictionBoard;
	private int depth;
	private int time;
	private int timeRemaining;
	private int userCancel;
	
	private MoveEvaluatorTree moveTree;
	
	private MoveEvaluatorTreeNode root;
	
	public String IncomingFEN;
	public String OutgoingFEN;
	
        /**
         * Methode stoesst das aufbauen des Zugbaumes an.
         */
	public void getMove() {
		moveTree = new MoveEvaluatorTree(currentBoard);
		zugBewerten();
	}
	
        /**
         * ToDo Kommentar ergaenzen
         */
	private void zugBewerten() {
		int max;
		int min;
		
	}
	

	
}
