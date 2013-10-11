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
	private LinkedList<String> moveList;
	private String ausgangsFen;
	private String moveFen;
	private FigurBewertung figurBewertung;
	private String bestMove;
	
	
        /**
         * Konstruktor mit aktuellem Board als Parameter.
         * @param board 
         */
	public MoveEvaluatorTree(String fen, FigurBewertung figurBewertung, Manager manager) {
		this.figurBewertung = figurBewertung;
		this.ausgangsFen = fen;
		this.root = new MoveEvaluatorTreeNode( new Board(fen, figurBewertung));
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
		String ausgabe = "";
		String lineStart = "";
		String lineZiel = "";
		int posStart = 0;
		int posZiel = 0;
		String[] moveFenArray = null;
		String[] ausgangFenArray = null;
		
		bestMove = moveList.get(positionOfMove);
		
		moveFen = moveList.get(positionOfMove);
		String moveFen_a, moveFen_b, moveFen_c, moveFen_d, moveFen_e, moveFen_f, moveFen_g, moveFen_h;
		String ausgangsFen_a, ausgangsFen_b, ausgangsFen_c, ausgangsFen_d, ausgangsFen_e, ausgangsFen_f, ausgangsFen_g, ausgangsFen_h;
		
		//aufsplitten in lines
		moveFenArray = moveFen.split("/");
		moveFen_h = homologizeFen( moveFenArray[0] );
		moveFen_g = homologizeFen( moveFenArray[1] );
		moveFen_f = homologizeFen( moveFenArray[2] );
		moveFen_e = homologizeFen( moveFenArray[3] );
		moveFen_d = homologizeFen( moveFenArray[4] ); 
		moveFen_c = homologizeFen( moveFenArray[5] );
		moveFen_b = homologizeFen( moveFenArray[6] );
		moveFen_a = homologizeFen( moveFenArray[7] );
		
		// ausgangsFen in Lines
		ausgangFenArray = ausgangsFen.split("/");
		ausgangsFen_h = homologizeFen( ausgangFenArray[0] );
		ausgangsFen_g = homologizeFen( ausgangFenArray[1] );
		ausgangsFen_f = homologizeFen( ausgangFenArray[2] );
		ausgangsFen_e = homologizeFen( ausgangFenArray[3] );
		ausgangsFen_d = homologizeFen( ausgangFenArray[4] );
		ausgangsFen_c = homologizeFen( ausgangFenArray[5] );
		ausgangsFen_b = homologizeFen( ausgangFenArray[6] );
		ausgangsFen_a = homologizeFen( ausgangFenArray[7] );
		
		//reihe a
		if( !ausgangsFen_a.equals(moveFen_a) ) {
			for (int i = 0; i < 8; i++) {
				String ausgang = ausgangsFen_a.substring(i, i + 1);
				String move = moveFen_a.substring(i, i + 1);
				if ( !ausgang.equals(move) ) {
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 1;
					} else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 1;	
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
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 2;
					} else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 2;
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
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 3;
					} else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 3;
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
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 4;
					} else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 4;	
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
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 5;
					} else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 5;	
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
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 6;
					} else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 6;	
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
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 7;
					} else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 7;	
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
					if (!ausgang.equals("0")) {
						lineStart = getLine(i);
						posStart = 8;
					}  else if (ausgang.equals("0")) {
						lineZiel = getLine(i);
						posZiel = 8;
					}
				}
			}
		}

		//baue Ausgabe
		ausgabe = lineStart + (new Integer(posStart).toString()) + lineZiel + (new Integer(posZiel).toString());
		
		return ausgabe;
	}
	
	private String getLine(int i) {
		switch (i) {
		case 0:
			return "h";
		case 1:
			return "g";
		case 2:
			return "f";
		case 3:
			return "e";
		case 4:
			return "d";
		case 5:
			return "c";
		case 6:
			return "b";
		case 7: 
			return "a";
		default :
			return null;
		}
	}
	
	private String homologizeFen(String s) {
		String temp = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i, i+ 1).matches("[1-8]") ) {
				int leerFelder = Integer.parseInt(s.substring(i, i+ 1)); 
				for (int j = 0; j < leerFelder; j++) {
					temp= temp + "0";
				}
			} else {
				temp= temp + s.substring(i, i+ 1);
			}
		}
		return temp;
	}
	
	public String getBestMoveFen() {
		return bestMove;
	}
	
	
	public MoveEvaluatorTreeNode getRoot() {
		return root;
	}
	
	
}
