package chessengine.movegenerator;

import java.util.LinkedList;

/**
 *	@author Schuhmacher, Kaub
 *	@version 201309101638
 */
public class MoveGenerator
implements MoveGeneratorInterface, Definitions {
	
	private LinkedList<String> 	outgoingFEN;	//Die Liste aller moeglichen Zuege, die zurueckgeschickt wird
	private byte[]				schachbrett;	//Die aktuelle FEN als Byte-Array		
	
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
		//Erstellt ein Objekt der Klasse UnravelFEN
		UnravelFEN u1 = new UnravelFEN();
		//uebergibt UnravelFEN die aktuelle FEN
		u1.setFEN(aktuelleFEN);
		//nimmt sich die von UnravelFEN in ein Array umgewandelte aktuelle Stellung
		schachbrett  = u1.getSchachbrett();

/* alte Version der Rochade
		///wenn der Inhalt NICHT "-" ist, stosse Rochadenueberpruefung an, die dann eventuell moegliche Rochadenzuege in die Liste
        // der moeglichen Zuege schreibt
    	if (!splittedFEN[9].equals("-")) {
        	if (splittedFEN[8].equals("w")) {
        		Rochade r1 = new Rochade(splittedFEN, true);
        	} else {
        		Rochade r1 = new Rochade(splittedFEN, false);
        	}
        	r1.get
        }
*/
    	
		Rochade0x88 r1 = new Rochade0x88();
		r1.setSchachbrett(schachbrett);
		r1.getZuege();
		
		zuegeBerechnen(schachbrett);
    }
    
    
	/*
	 * 
	 * @param	board	Die aktuelle Stellung, aus der alle normalen Zuege (ohne Sonderzuege) berechnet werden sollen
	 */
	private void zuegeBerechnen(byte[] board) {
		//Gehe das gesamte Board-Array durch, bis nur noch Felder des "Geisterboards" kommen
		for (int i = 0; i < 120; i++) {
			//wenn das Feld mit Index i ein Feld des gueltigen Schachbretts ist und dies dann kein leeres Feld ist,
			if ((i & 136) == 0	&&	board[i] != 0) {
				//Wenn der Wert an diesem Index positiv ist, also Weiss am Zug ist
				if (board[i] > 0) {
					//Uebergib das Board un den aktuellen mit der aktiven Farbe (true = weiss) und
					//den erlaubten Zuegen der Fiugr an die Zugberechnung
					switch (board[i]) {
					case pawn_w :	berechneZugPawn(board, i, true, pawn_moves);
						break;
					case rook_w :	berechneZugRook(board, i, true, rook_moves);
						break;
					case knight_w : berechneZugKnight(board, i, true, knight_moves);
						break;
					case bishop_w :	berechneZugBishop(board, i, true, bishop_moves);
						break;
					case king_w :	berechneZugKing(board, i, true, king_moves);
						break;
					case queen_w :	berechneZugQueen(board, i, true, queen_moves);
						break;
					}
				} else {	//Wenn der Inhalt an diesem Index negativ ist, also Schwarz am Zug ist
					//Uebergib das board und den aktuellen Index mit der aktiven Farbe (false = schwarz) und
					//den erlaubten Zuegen der Figur an die Zugberechnung
					switch (board[i]) {
					case pawn_b :	berechneZugPawn(board, i, false, pawn_moves);
						break;
					case rook_b : 	berechneZugRook(board, i, false, rook_moves);
						break;
					case knight_b : berechneZugKnight(board, i, false, knight_moves);
						break;
					case bishop_b : berechneZugBishop(board, i, false, bishop_moves);
						break;
					case king_b : 	berechneZugKing(board, i, false, king_moves);
						break;
					case queen_b : 	berechneZugQueen(board, i, false, queen_moves);
						break;
					}
				}
			}
		}
	}
	
    
    /**
	 * getter der Klasse, der die moeglichen Zuege ausgibt
	 * 
	 * @return	Liste aller moeglichen Zuege
	 */
	public LinkedList<String> getZuege() {
		return outgoingFEN;
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