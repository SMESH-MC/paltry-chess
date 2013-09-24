package chessengine;

import java.util.LinkedList;

/**
 * 
 * @author Dominik A. Erb
 *
 */
public class MoveEvaluatorTreeNode {
	private Board Board;
	private int BoardValue;
	private LinkedList<MoveEvaluatorTreeNode> ChildBoards;
	private int hasChild; 

	
	public MoveEvaluatorTreeNode(Board board){
		this.Board = board;
		this.BoardValue = board.getBordValue();
		this.hasChild = 0;
	}
	
	public MoveEvaluatorTreeNode(Board board, LinkedList<String> BoardFens){
		this.Board = board;
		this.BoardValue = board.getBordValue();
		setChildBoards(BoardFens);
		this.hasChild = BoardFens.size();
		}
	
	public void setChildBoards(LinkedList<String> BoardFens) {
		for (int i = 0; i < BoardFens.size(); i++){
			this.ChildBoards.addLast(new MoveEvaluatorTreeNode(new Board (BoardFens.get(i))));
		} 
	}
	
	public MoveEvaluatorTreeNode getChildAtPos(int pos) {
		if (pos < hasChild) {
			return this.ChildBoards.get(pos);
		}
		return null;
	}
	
	public int hasChild() {
		return hasChild;
	}
}
