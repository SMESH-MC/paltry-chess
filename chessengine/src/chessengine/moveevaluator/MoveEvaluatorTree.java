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
		MoveEvaluatorTreeNode parentNode;
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
		//Tiefe 3
		for (int j = 0 ; j < countOfRootLeaf ; j++ ) {
			parentNode = root.getChildAtPos(j);
			for (int k = 0 ; j < parentNode.hasChild() ; k++) {
				currentNode = parentNode.getChildAtPos(k);
				MoveGenerator mg3 = new MoveGenerator();
				mg3.setFEN(currentNode.getBoardFen());
				currentNode.setChildBoards( moveList=mg3.getZuege() , figurBewertung );
			}
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
		moveFen_h = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_g = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_f = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_e = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_d = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_c = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		posOfBreak = moveFen.indexOf('/');
		temp = moveFen.substring(posOfBreak);
		moveFen_b = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		posOfBreak = moveFen.indexOf(' ');
		temp = moveFen.substring(posOfBreak);
		moveFen_a = moveFen.substring(0, posOfBreak);
		moveFen = temp;
		
		// ausgangsFen in Lines
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_h = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_g = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_f = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_e = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_d = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_c = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf('/');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_b = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		posOfBreak = ausgangsFen.indexOf(' ');
		temp = ausgangsFen.substring(posOfBreak);
		ausgangsFen_a = ausgangsFen.substring(0, posOfBreak);
		ausgangsFen = temp;
		
		
		//reihe a
		if( !ausgangsFen_a.equals(moveFen_a) ) {
			if ( ausgangsFen_a.length() > moveFen_a.length() ) {
				lineStart = "a";
				String ausgang = homologizeFen(ausgangsFen_a);
				String nachZug = homologizeFen(moveFen_a);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else if ( ausgangsFen_a.length() < moveFen_a.length() ){
				lineZiel = "a";
				String ausgang = homologizeFen(ausgangsFen_a);
				String nachZug = homologizeFen(moveFen_a);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		//rheie b
		if( !ausgangsFen_b.equals(moveFen_b) ) {
			if ( ausgangsFen_b.length() > moveFen_b.length() ) {
				lineStart = "b";
				String ausgang = homologizeFen(ausgangsFen_b);
				String nachZug = homologizeFen(moveFen_b);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else if ( ausgangsFen_b.length() < moveFen_b.length() ) {
				lineZiel = "b";
				String ausgang = homologizeFen(ausgangsFen_b);
				String nachZug = homologizeFen(moveFen_b);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		//rheie c
		if( !ausgangsFen_c.equals(moveFen_c) ) {
			if ( ausgangsFen_c.length() > moveFen_c.length() ) {
				lineStart = "c";
				String ausgang = homologizeFen(ausgangsFen_c);
				String nachZug = homologizeFen(moveFen_c);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else if ( ausgangsFen_c.length() < moveFen_c.length() ) {
				lineZiel = "c";
				String ausgang = homologizeFen(ausgangsFen_c);
				String nachZug = homologizeFen(moveFen_c);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		//rheie d
		if( !ausgangsFen_d.equals(moveFen_d) ) {
			if ( ausgangsFen_d.length() > moveFen_d.length() ) {
				lineStart = "d";
				String ausgang = homologizeFen(ausgangsFen_d);
				String nachZug = homologizeFen(moveFen_d);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else if ( ausgangsFen_d.length() < moveFen_d.length() ) {
				lineZiel = "d";
				String ausgang = homologizeFen(ausgangsFen_d);
				String nachZug = homologizeFen(moveFen_d);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		//rheie e
		if( !ausgangsFen_e.equals(moveFen_e) ) {
			if ( ausgangsFen_e.length() > moveFen_e.length() ) {
				lineStart = "e";
				String ausgang = homologizeFen(ausgangsFen_e);
				String nachZug = homologizeFen(moveFen_e);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else if ( ausgangsFen_e.length() < moveFen_e.length() ) {
				lineZiel = "e";
				String ausgang = homologizeFen(ausgangsFen_e);
				String nachZug = homologizeFen(moveFen_e);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		//rheie f
		if( !ausgangsFen_f.equals(moveFen_f) ) {
			if ( ausgangsFen_f.length() > moveFen_f.length() ) {
				lineStart = "f";
				String ausgang = homologizeFen(ausgangsFen_f);
				String nachZug = homologizeFen(moveFen_f);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else if ( ausgangsFen_f.length() < moveFen_f.length() ) {
				lineZiel = "f";
				String ausgang = homologizeFen(ausgangsFen_f);
				String nachZug = homologizeFen(moveFen_f);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		//rheie g
		if( !ausgangsFen_g.equals(moveFen_g) ) {
			if ( ausgangsFen_g.length() > moveFen_g.length() ) {
				lineStart = "g";
				String ausgang = homologizeFen(ausgangsFen_g);
				String nachZug = homologizeFen(moveFen_g);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else  if ( ausgangsFen_g.length() < moveFen_g.length() ) {
				lineZiel = "g";
				String ausgang = homologizeFen(ausgangsFen_g);
				String nachZug = homologizeFen(moveFen_g);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		//rheie h
		if( !ausgangsFen_h.equals(moveFen_h) ) {
			if ( ausgangsFen_h.length() > moveFen_h.length() ) {
				lineStart = "h";
				String ausgang = homologizeFen(ausgangsFen_h);
				String nachZug = homologizeFen(moveFen_h);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posStart = i + 1;
					}
				}
			} else if ( ausgangsFen_h.length() < moveFen_h.length() ) {
				lineZiel = "h";
				String ausgang = homologizeFen(ausgangsFen_h);
				String nachZug = homologizeFen(moveFen_h);  
				for (int i = 0; i < 8; i++) {
					if ( !ausgang.equals(nachZug) ) {
						posZiel = i + 1;
					}
				}
			}
		}
		
		//schauen ob rochade getan ist
		if (lineStart.equals("")) {
			//lange rochade
			if ( moveFen_a.charAt(2) == 'K' || moveFen_h.charAt(2) == 'k' ) {
				return "0-0-0";
			}
			//kurze rochade
			if ( moveFen_a.charAt(6) == 'K' || moveFen_h.charAt(6) == 'k' ) {
				return "0-0";
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
					temp= temp + "0";
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
