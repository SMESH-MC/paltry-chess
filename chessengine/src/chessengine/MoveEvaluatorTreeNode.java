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
	private LinkedList<Board> ChildBoards;

	
	public MoveEvaluatorTreeNode(Board board){
		this.Board = board;
		this.BoardValue = board.getBordValue();
	}
	
	public MoveEvaluatorTreeNode(Board board, LinkedList<String> BoardFens){
		this.Board = board;
		this.BoardValue = board.getBordValue();
		setChildBoards(this , BoardFens);
		}
	
	public MoveEvaluatorTreeNode setChildBoards(MoveEvaluatorTreeNode Node, LinkedList<String> BoardFens) {
		for (int i = 0; i < BoardFens.size(); i++){
			this.ChildBoards.add(new Board(BoardFens.get(i)));
		} 
		return Node;
		
	}
}
