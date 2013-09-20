package chessengine.moveevaluator;

// LinkedList bis wir unsere doppelt verkettete haben
import java.util.LinkedList;

public class EvaluationTree implements EvaluationTreeInterface {
	
	private String fen; // FEN-String des Boards
	private boolean weissAmZug; // ich fand doch bool besser, true = weiss, false = schwarz
	private int bewertung; // Bewertung des Boards
	private LinkedList<EvaluationTreeNode> kinder; // Liste der Kinder
	private EvaluationTreeNodeInterface parent; // Parent fuer die rekursion
	
}
