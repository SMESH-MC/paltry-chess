package chessengine.moveevaluator;

import java.util.LinkedList;
import chessengine.Board;

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
	private int lastVisitedChild = 0;
	public int minChild = 10000;
	public int posMinChild;

	
	public MoveEvaluatorTreeNode(Board board){
		this.Board = board;
		this.BoardValue = board.getBoardValue();
		this.hasChild = 0;
	}
	
	public MoveEvaluatorTreeNode(Board board, LinkedList<String> BoardFens){
		this.Board = board;
		this.BoardValue = board.getBoardValue();
		setChildBoards(BoardFens);
		}
	
	public void setChildBoards(LinkedList<String> BoardFens) {
		for (int i = 0; i < BoardFens.size(); i++){
			this.ChildBoards.addLast(new MoveEvaluatorTreeNode(new Board (BoardFens.get(i))));
		} 
		this.hasChild = BoardFens.size();
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
	
	public int getLastVisitedChild() {
		return lastVisitedChild;
	}
	
	public void setLastVisitedChild() {
		this.lastVisitedChild = lastVisitedChild++;
	}
	
	public int getBoardValue() {
		return this.BoardValue;
	}
}

