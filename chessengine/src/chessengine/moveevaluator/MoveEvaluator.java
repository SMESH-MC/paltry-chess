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
	private LinkedList fenList;
	
	public String currentFen;
	public String ausgabeFEN;
	
        /**
         * Methode stoesst das aufbauen des Zugbaumes an.
         * @return 
         */
	public MoveEvaluatorTreeNode getMove() {
		root = null;
		//moveTree = MoveEvaluatorTree.getTree();
		return root;
	}
	
	
	private void Movelist() {
		MoveGenerator mg1 = new MoveGenerator();
		mg1.setFEN(currentFen);
		fenList=mg1.getZuege();
	}
	
	
	private String fakeBestMove(String fen) {
		MoveGenerator fakeMoveGen= new MoveGenerator();
		fakeMoveGen.setFEN(fen);
		LinkedList<String> fenList = fakeMoveGen.getZuege();
		
		int randomWert = (int)Math.floor(Math.random() * fenList.size()); 
		ausgabeFEN = fenList.get(randomWert);
		return ausgabeFEN; 
	}
	
	public void setRoot(MoveEvaluatorTreeNode root) {
		this.root = root;
	}


	public String getAusgabeFEN() {
		Movelist();
		//fakeBestMove(fenList);
		return ausgabeFEN;
	}
	
}
