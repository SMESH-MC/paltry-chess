/**
 * 
 */
package chessengine.moveevaluator;

import java.util.LinkedList;

import chessengine.figurbewertung.FigurBewertung;
import chessengine.movegenerator.MoveGenerator;
import chessengine.Board;
import chessengine.Manager;

/**
 * Klasse repraesentiert einen Zugbaum.
 * @author Dominik A. Erb
 */
public class MoveEvaluatorTree {

	private MoveEvaluatorTreeNode root;
	private LinkedList<MoveEvaluatorTreeNode> childList; 
	private MoveGenerator movegen;
	private LinkedList<String> moveList;
	private String ausgangsFen;
	private String moveFen;
	private FigurBewertung figurBewertung;
	
	
        /**
         * Konstruktor mit aktuellem Board als Parameter.
         * @param board 
         */
	public MoveEvaluatorTree(String fen, FigurBewertung figurBewertung, Manager manager) {
		this.figurBewertung = figurBewertung;
		this.ausgangsFen = fen;
		this.root = new MoveEvaluatorTreeNode( new Board(fen, figurBewertung));
		this.movegen = new MoveGenerator();
		generateTree();
	}
	
        /**
         * Getter-Methode fuer die Wurzel des Bauemes.
         * @return root
         */
	public MoveEvaluatorTreeNode getTree() {
		return root;
	}
	
	/**
	 * erzeugt den Baum bis Tiefe 3 
	 */
	private void generateTree() {
		MoveEvaluatorTreeNode currentNode;
		int countOfRootLeaf = 0;
		//Tiefe 1
		MoveGenerator mg1 = new MoveGenerator();
		mg1.setFEN(ausgangsFen);
		moveList=mg1.getZuege();
		root.setChildBoards( moveList, figurBewertung );
		countOfRootLeaf = root.hasChild();
		//Tiefe 2
		countOfRootLeaf = root.hasChild();
		for (int i = 0 ; i < countOfRootLeaf ; i++) {
			currentNode = root.getChildAtPos(i);
			MoveGenerator mg2 = new MoveGenerator();
			mg2.setFEN(currentNode.getBoardFen());
			currentNode.setChildBoards( moveList=mg2.getZuege() , figurBewertung );
		}
	}

        /**
         * Getter-Methode fuer die Liste der Kind-Knoten.
         * @return childList
         */
	public LinkedList<MoveEvaluatorTreeNode> getChildList() {
		return childList;
	}
	
        /**
         * Methode ermittelt den besten Zug und liefert diesen zurueck.
         * @return 
         */
	private int findBestMove() {
		MoveEvaluatorTreeNode parrentNode;
		MoveEvaluatorTreeNode currentNode;
		int max = -10000;
		int maxPosition =1;
		int minValue = 10000;
		int minPos = 1;
		for (int i = 0; i < root.hasChild(); i++) {
			parrentNode = root.getChildAtPos(i);
			for (int j = 0; j < parrentNode.hasChild(); j++) {
				currentNode = parrentNode.getChildAtPos(j);
				if (currentNode.getBoardValue() < currentNode.minChild) {
					currentNode.minChild = currentNode.getBoardValue();
					currentNode.posMinChild = j;
				}
			}
			if (parrentNode.getBoardValue() > max) {
				max = parrentNode.getBoardValue();
				maxPosition = i;
			}
		}
		for (int k = 0; k < root.hasChild(); k++) {
			parrentNode = root.getChildAtPos(k);
			if (parrentNode.minChild < minValue) {
				minValue = parrentNode.minChild;
				minPos = k;
			}
		}
		//Check nach bestem Zug
		if (minPos == maxPosition) {
			return maxPosition;	
		} else if ( (root.getChildAtPos(minPos).getBoardValue()) > (root.getChildAtPos(maxPosition).getBoardValue()+5)) {
			return minPos;		
		} else {
			return maxPosition;
		}
	}
	
	public String getBestMove(){
		int positionOfMove = findBestMove();
		String temp;
		String ausgabe = "";
		int posOfBreak;
		String lineStart = "";
		String lineZiel = "";
		int posStart = 0;
		int posZiel = 0;
		
		moveFen = moveList.get(positionOfMove);
		String moveFen_a, moveFen_b, moveFen_c, moveFen_d, moveFen_e, moveFen_f, moveFen_g, moveFen_h;
		String ausgangsFen_a, ausgangsFen_b, ausgangsFen_c, ausgangsFen_d, ausgangsFen_e, ausgangsFen_f, ausgangsFen_g, ausgangsFen_h;
		
		//aufsplitten in lines
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_h =  homologizeFen( moveFen.substring(0, posOfBreak) );
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_g =  homologizeFen( moveFen.substring(0, posOfBreak) );
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_f =  homologizeFen( moveFen.substring(0, posOfBreak) );
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_e =  homologizeFen( moveFen.substring(0, posOfBreak) );
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_d =  homologizeFen( moveFen.substring(0, posOfBreak) );
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_c =  homologizeFen( moveFen.substring(0, posOfBreak) );
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_b =  homologizeFen( moveFen.substring(0, posOfBreak) );
		moveFen = temp;
		posOfBreak = moveFen.indexOf(' ');
		temp = moveFen.substring(posOfBreak);
		moveFen_a =  homologizeFen( moveFen.substring(0, posOfBreak) );
		
		// ausgangsFen in Lines
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_h =  homologizeFen(ausgangsFen.substring(0, posOfBreak) );
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_g =  homologizeFen( ausgangsFen.substring(0, posOfBreak) );
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_f =  homologizeFen( ausgangsFen.substring(0, posOfBreak) );
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_e =  homologizeFen( ausgangsFen.substring(0, posOfBreak) );
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_d =  homologizeFen( ausgangsFen.substring(0, posOfBreak) );
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_c =  homologizeFen( ausgangsFen.substring(0, posOfBreak) );
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_b =  homologizeFen( ausgangsFen.substring(0, posOfBreak) );
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf(' ');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_a =  homologizeFen( ausgangsFen.substring(0, posOfBreak) );
		
		//reihe a
		if( !ausgangsFen_a.equals(moveFen_a) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_a.substring(i, i + 1);
				String move = moveFen_a.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "a";
						posStart = i + 1;
					} else if (ausgang.equals("1")) {
						lineZiel = "a";
						posZiel = i + 1;	
					}
				}
			}
		}
		//reihe b
		if( !ausgangsFen_b.equals(moveFen_b) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_b.substring(i, i + 1);
				String move = moveFen_b.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "b";
						posStart = i + 1;
					} else if (ausgang.equals("1")) {
						lineZiel = "b";
						posZiel = i + 1;
					}
				}
			}
		}
		//reihe c
		if( !ausgangsFen_c.equals(moveFen_c) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_c.substring(i, i + 1);
				String move = moveFen_c.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "c";
						posStart = i + 1;
					} else if (ausgang.equals("1")) {
						lineZiel = "c";
						posZiel = i + 1;
					}
				}
			}
		}	
		//reihe d
		if( !ausgangsFen_d.equals(moveFen_d) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_d.substring(i, i + 1);
				String move = moveFen_d.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "d";
						posStart = i + 1;
					} else if (ausgang.equals("1")) {
						lineZiel = "d";
						posZiel = i + 1;	
					}
				}
			}
		}
		//reihe e
		if( !ausgangsFen_e.equals(moveFen_e) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_e.substring(i, i + 1);
				String move = moveFen_e.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "e";
						posStart = i + 1;
					} else if (ausgang.equals("1")) {
						lineZiel = "e";
						posZiel = i + 1;	
					}
				}
			}
		}
		//reihe f
		if( !ausgangsFen_f.equals(moveFen_f) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_f.substring(i, i + 1);
				String move = moveFen_f.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "f";
						posStart = i + 1;
					} else if (ausgang.equals("1")) {
						lineZiel = "f";
						posZiel = i + 1;	
					}
				}
			}
		}
		//reihe g
		if( !ausgangsFen_g.equals(moveFen_g) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_g.substring(i, i + 1);
				String move = moveFen_g.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "g";
						posStart = i + 1;
					} else if (ausgang.equals("1")) {
						lineZiel = "g";
						posZiel = i + 1;	
					}
				}
			}
		}
		//reihe h
		if( !ausgangsFen_h.equals(moveFen_h) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_h.substring(i, i + 1);
				String move = moveFen_h.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("1")) {
						lineStart = "h";
						posStart = i + 1;
					}  else if (ausgang.equals("1")) {
						lineZiel = "h";
						posZiel = i + 1;
					}
				}
			}
		}

		//baue Ausgabe
		ausgabe = lineStart + (new Integer(posStart).toString()) + lineZiel + (new Integer(posZiel).toString());
		
		return ausgabe;
	}
	
	private String homologizeFen(String s) {
		String temp = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i, i+ 1).matches("[1-8]") ) {
				int leerFelder = Integer.parseInt(s.substring(i, i+ 1)); 
				for (int j = 0; j < leerFelder; j++) {
					temp= temp + "1";
				}
			} else {
				temp= temp + s.substring(i, i+ 1);
			}
		}
		return temp;
	}
	
	
	
	
	public MoveEvaluatorTreeNode getRoot() {
		return root;
	}
	
	
}
