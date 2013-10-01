/**
 * 
 */
package chessengine.moveevaluator;

import java.util.LinkedList;

import chessengine.movegenerator.MoveGenerator;
import chessengine.Board;

/**
 * @author Dominik A. Erb
 *
 */
public class MoveEvaluatorTree {

	private MoveEvaluatorTreeNode root;
	private LinkedList<MoveEvaluatorTreeNode> childList; 
	private MoveGenerator movegen;
	
	
	public MoveEvaluatorTree(Board board) {
		this.root = new MoveEvaluatorTreeNode(board);
		this.movegen = new MoveGenerator();
		generateTree();
	}
	
	public MoveEvaluatorTreeNode getTree() {
		return root;
	}
	
	/**
	 * erzeugt den Baum bis Tiefe 3
	 */
	private void generateTree() {
		MoveEvaluatorTreeNode currentNode;
		MoveEvaluatorTreeNode parentNode;
		int countOfRootLeaf = 0;
		
		//Tiefe 1
		root.setChildBoards( movegen.getZuege() );
		countOfRootLeaf = root.hasChild();
		//Tiefe 2
		countOfRootLeaf = root.hasChild();
		for (int i = 0 ; i < countOfRootLeaf ; i++) {
			currentNode = root.getChildAtPos(i);
			currentNode.setChildBoards( movegen.getZuege() );
		}
		//Tiefe 3
		for (int j = 0 ; j < countOfRootLeaf ; j++ ) {
			parentNode = root.getChildAtPos(j);
			for (int k = 0 ; j < parentNode.hasChild() ; k++) {
				currentNode = parentNode.getChildAtPos(k);
				currentNode.setChildBoards( movegen.getZuege() );
			}
		}
	}

	public LinkedList<MoveEvaluatorTreeNode> getChildList() {
		return childList;
	}
	
	
	
	
	
	
}