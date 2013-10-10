package chessengine.moveevaluator;

import java.util.LinkedList;

import chessengine.Board;
import chessengine.figurbewertung.FigurBewertung;

/**
 * Klasse beschreibt einen Knoten im Zugbaum.
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

	/**
         * Konstruktor mit aktuellem Board als Parameter
         * @param board 
         */
	public MoveEvaluatorTreeNode(Board board){
		this.Board = board;
		this.BoardValue = board.getBoardValue();
		this.hasChild = 0;
	}
	
        /**
         * Konstruktor mit aktuellem Board und einer Liste von BoardFens 
         * als Parameter.
         * @param board
         * @param BoardFens 
         */
	public MoveEvaluatorTreeNode(Board board, LinkedList<String> BoardFens, FigurBewertung figurBewertung){
		this.Board = board;
		this.BoardValue = board.getBoardValue();
		setChildBoards(BoardFens, figurBewertung);
		}
	
        /**
         * Setter-Methode fuer Childboards
         * @param BoardFens 
         * @param figurBewertung 
         */
	public void setChildBoards(LinkedList<String> BoardFens, FigurBewertung figurBewertung) {
		for (int i = 0; i < BoardFens.size(); i++){
			this.ChildBoards.addLast(new MoveEvaluatorTreeNode(new Board (BoardFens.get(i), figurBewertung)));
		} 
		this.hasChild = BoardFens.size();
	}
	
        /**
         * Getter-Methode fuer ein Kindknoten einer bestimmten Stelle zu erhalten.
         * @param pos Index der Kindes
         * @return child
         */
	public MoveEvaluatorTreeNode getChildAtPos(int pos) {
		if (pos < hasChild) {
			return this.ChildBoards.get(pos);
		}
		return null;
	}
	
        /**
         * Methode prueft ob Knoten Kinder hat.
         * @return int
         */
	public int hasChild() {
		return hasChild;
	}
	
        /**
         * Methode liefert den Index des zuletzt besuchten Kindes zurueck.
         * @return 
         */
	public int getLastVisitedChild() {
		return lastVisitedChild;
	}
	
        /**
         * Methode setzt den Index des zuletzt besuchten Kindes.
         */
	public void setLastVisitedChild() {
		this.lastVisitedChild = lastVisitedChild++;
	}
	
        /**
         * Getter-Methode fuer den BoardValue
         * @return BoardValue
         */
	public int getBoardValue() {
		return this.BoardValue;
	}
}

