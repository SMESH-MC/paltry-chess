package chessengine.moveevaluator;

import java.util.LinkedList;


import chessengine.movegenerator.MoveGenerator;
import chessengine.Board;
/**
 * TODO Kommentar ergaenzen
 * @author 
 */
public class MoveEvaluator {
	//Variablendefinitionen
	private Board currentBoard;

	private MoveEvaluatorTree moveTree;
	
	private MoveEvaluatorTreeNode root;
	private LinkedList fenlist;
	
	public String currentFen;
	
        /**
         * Methode stoesst das aufbauen des Zugbaumes an.
         * @return 
         */
	public MoveEvaluatorTreeNode getMove() {
		root = null;
		moveTree = new MoveEvaluatorTree(currentBoard);
		return root;
	}
	
	
	private void Movelist() {
		MoveGenerator mg1 = new MoveGenerator();
		mg1.setFEN(currentFen);
		fenlist=mg1.getZuege();
	}
	
	
	private String fakeBestMove(LinkedList<String> fenList) { 
		int randomWert = (int)Math.floor(Math.random() * fenList.size()); 
		String ausgabeFEN = fenList.get(randomWert);
		String currentFen = ausgabeFEN;
		return ausgabeFEN; 
	}
	
	public void setRoot(MoveEvaluatorTreeNode root) {
		this.root = root;
	}
	
}
