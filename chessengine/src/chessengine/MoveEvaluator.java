package chessengine;

import chessengine.movegenerator.MoveGenerator;

public class MoveEvaluator {
	//Variablendefinitionen
	private Board currentBoard;
	private Board predictionBoard;
	private int depth;
	private int time;
	private int timeRemaining;
	private int userCancel;
	ZugThread zthread;
	
	private MoveEvaluatorTree moveTree;
	
	private MoveEvaluatorTreeNode root;
	
	public String IncomingFEN;
	public String OutgoingFEN;
	
	public void getMove() {
		moveTree = new MoveEvaluatorTree(currentBoard);
		zugBewerten();
	}
	
	private void zugBewerten() {
		int max;
		int min;
		
	}
	

	
}
