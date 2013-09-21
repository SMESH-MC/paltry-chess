package chessengine.moveevaluator;

public class EvaluationTreeNode implements EvaluationTreeNodeInterface {
	
	
	public EvaluationTreeNodeInterface besterZug; //enthaelt den zu diesem Zeitpunkt besten Zug
	private int alpha; // alpha Wert fuer Pruning
	private int beta; // beta Wert fuer Pruning
	private EvaluationTreeNodeInterface wurzel; // enthaelt die Wurzel (Ausgangsstellung)
	
	
	
	public EvaluationTreeNode() {
		String fen;
		int spieler;
		int bewertung;
	    Object listFollowingBoards;
	    Object wurzel;
		
	}
	
	
	

}
