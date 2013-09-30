package chessengine.movegenerator;

import java.util.LinkedList;

/**
 *	@author Schuhmacher, Kaub
 *	@version 201309101638
 */
public class MoveGenerator
implements MoveGeneratorInterface, Definitions {
	//Die Liste aller moeglichen Zuege, die zurueckgeschickt wird
	private LinkedList<String> 	outgoingFEN;	

	/* Die aktuelle FEN als Byte-Array:
	 * schachbrett[0] bis schachbrett[119]: A1 bis H8 inkl. des "Geisterboards" rechts des normalen Boards
	 * d.h. 0-7 gültige Felder, 8-15 ungültige, 16-23 gültig, ... 112-119 gültig
	 * schachbrett[120] = 1 (Weiss am Zug) | 0 (Schwarz am Zug)
	 * schachbrett[121, 122, 123, 124] = Bitmarker ( 0 | 1) fuer Rochademoeglichkeiten: K Q k q 
	 * schachbrett[125] = En-Passant-Feld des letzten Zuges in 0x88-Darstellung (z.B.: 83 = schachbrett[83] = D6)
	 * schachbrett[126] = Anzahl der Halbzuege
	 * schachbrett[127] = Zugnummer 
	 */
	private byte[]				schachbrett;
	
	/**
     * Konstruktor, der die auszugebende Liste aller moeglichen Zuege initialisiert
     */
    public MoveGenerator() {
    	outgoingFEN = new LinkedList<String>();
    }
    
    /**
     * 
     * 
     * @param Die aktuelle Stellung des Schachbretts als FEN-String
     */
    public void setFEN(String aktuelleFEN) {
		//Erstellt ein Objekt der Klasse FenDecode
    	FenDecode fd = new FenDecode();
		//uebergibt FenDecode die aktuelle FEN
		fd.setFEN(aktuelleFEN);
		//nimmt sich die von FenDecode in ein Array umgewandelte aktuelle Stellung
		schachbrett  = fd.getSchachbrett();

		/* neue Version der Rochade 
		Rochade0x88 r1 = new Rochade0x88();
		r1.setSchachbrett(schachbrett);
		r1.getZuege();
													funzt noch nicht
		*/
		zuegeBerechnen(schachbrett);
    }
    
    
	/**
	 * 
	 * @param	board	Die aktuelle Stellung, aus der alle normalen Zuege (ohne Sonderzuege) berechnet werden sollen
	 */
	private void zuegeBerechnen(byte[] board) {
		if (board[120] == 1) { //weiss am Zug
			//Gehe die Felder des Board-Arrays durch, bis nur noch Felder des "Geisterboards" kommen
			for (byte startfeld = 0;	startfeld <= 119;	startfeld++) {
				//wenn das Feld ein gueltiges ist und eine weisse Figur darauf steht
				if ((startfeld & 136) == 0 &&	board[startfeld] > 0) {
						//Uebergib das Board und den aktuellen Index mit der aktiven Farbe (true = weiss) und
						//den erlaubten Zuegen der Figur an die Zugberechnung
						switch (board[startfeld]) {
						case pawn_w :	berechneZugBauer(	board, startfeld, true, pawn_moves);	break;
						case rook_w :	berechneSlidingZug(	board, startfeld, true, rook_moves);	break;
						case knight_w : berechneZug(		board, startfeld, true, knight_moves);	break;
						case bishop_w :	berechneSlidingZug(	board, startfeld, true, bishop_moves);	break;
						case king_w :	berechneZugKing	(	board, startfeld, true, king_moves);	break;
						case queen_w :	berechneSlidingZug(	board, startfeld, true, queen_moves);	break;
						}
				}
			}//endfor
		} else { //schwarz am Zug
			//Gehe die gueltigen Felder des Board-Arrays durch, bis nur noch Felder des "Geisterboards" kommen
			for (byte startfeld = 0;	startfeld <= 119;	startfeld++) {
				//wenn das Feld ein gueltiges ist und eine schwarze Figur darauf steht
				if ((startfeld & 136) == 0 &&	board[startfeld] < 0) {
					//Uebergib das board und den aktuellen Index mit der aktiven Farbe (false = schwarz) und
					//den erlaubten Zuegen der Figur an die Zugberechnung
					switch (board[startfeld]) {
					case pawn_b :	berechneZugBauer(	board, startfeld, false, pawn_moves);	break;
					case rook_b : 	berechneSlidingZug(	board, startfeld, false, rook_moves);	break;
					case knight_b : berechneZug(		board, startfeld, false, knight_moves);	break;
					case bishop_b : berechneSlidingZug(	board, startfeld, false, bishop_moves);	break;
					case king_b : 	berechneZugKing	(	board, startfeld, false, king_moves);	break;
					case queen_b : 	berechneSlidingZug(	board, startfeld, false, queen_moves);	break;
					}
				}
			}
		}
	}
	
	private void berechneZug(byte[] board, byte startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		for (byte b : erlaubteZuege) {
			int zielfeld = b + startfeld;
			//wenn das Zielfeld ein gueltiges Feld ist
			if (zielfeld <= 119 &&	(zielfeld & 136) == 0) {
				//wenn das Feld leer ist
				if (board[zielfeld] == 0) {
					//Figur auf Zielfeld ziehen
					board[zielfeld] = board[startfeld];
					//Startfeld leeren
					board[startfeld] = 0;
					//Zug hinzufuegen
					zugHinzufuegen(board);
				} else {
					//wenn weiss am Zug ist
					if (weissAmZug) {
						//wenn auf dem Zielfeld der Gegner schwarz steht
						if (board[zielfeld] < 0) {
							
						}
					} else { //schwarz ist am Zug
						//wenn auf dem Zielfeld der Gegner weiss steht
						if (board[zielfeld] > 0) {
							
						}
					}
				}
			}
		}
	}
   
	
    private void berechneZugKing(byte[] board, int i, boolean b,
			byte[] kingMoves) {
		// TODO Auto-generated method stub
		
	}

	private void berechneZugBauer(byte[] board, int startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		// TODO Auto-generated method stub
		
	}

	private void berechneSlidingZug(byte[] board, int startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * getter der Klasse, der die moeglichen Zuege ausgibt
	 * 
	 * @return	Liste aller moeglichen Zuege
	 */
	public LinkedList<String> getZuege() {
		return outgoingFEN;
	}
	
	/**
	 * Hilfsmethode, die einen Zug zu der Liste der Zuege hinzufuegt
	 * 
	 * @param 
	 */
	public void zugHinzufuegen(byte[] board) {
		FenEncode fe = new FenEncode();
		fe.setBoard(board);
		outgoingFEN.add(fe.getFEN());
	}


	/*	
	private void whiteTurn() {
	}
	
	private void blackTurn() {
		increaseFullmoveNumber();
	}

	
	private String increaseHalfmoveClock() {
		int newHalfmoveInt = Integer.parseInt(splittedFEN[11]) + 1;
		return String.valueOf(newHalfmoveInt);
		}
	
	private String increaseFullmoveNumber() {
		int newFullmoveInt = Integer.parseInt(splittedFEN[12]) + 1;
		return String.valueOf(newFullmoveInt);
		}
*/	
}