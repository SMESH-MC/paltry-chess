/**
 * 
 */
package chessengine;

import java.util.LinkedList;

import chessengine.movegenerator.MoveGenerator;


/**
 * @author Dominik A. Erb
 *
 */
public class MoveEvaluatorTree {

	private MoveEvaluatorTreeNode root;
	private LinkedList<MoveEvaluatorTreeNode> childList; 
	
	
	public MoveEvaluatorTreeNode MoveEvaluatorTee(Board board, int tiefe) {
		this.root = new MoveEvaluatorTreeNode(board);
		generateTree();
		return root;
	}
	
	/**
	 * erzeugt den Baum bis Tiefe 3
	 * @param tiefe = maxTiefe des Baumes
	 */
	private void generateTree() {
		MoveEvaluatorTreeNode currentNode;
		MoveEvaluatorTreeNode parentNode;
		int countOfRootLeaf = 0;
		
		//Tiefe 1
		root.setChildBoards( MoveGenerator.getZuege() );
		//Tiefe 2
		countOfRootLeaf = root.hasChild();
		for (int i = 0 ; i < countOfRootLeaf ; i++) {
			currentNode = root.getChildAtPos(i);
			currentNode.setChildBoards( MoveGenerator.getZuege() );
		}
		//Tiefe 3
		for (int j = 0 ; j < countOfRootLeaf ; j++ ) {
			parentNode = root.getChildAtPos(j);
			for (int k = 0 ; j < parentNode.hasChild() ; k++) {
				currentNode = parentNode.getChildAtPos(k);
				currentNode.setChildBoards( MoveGenerator.getZuege() );
			}
		}
	}
	
	
	
	
	
	
}
