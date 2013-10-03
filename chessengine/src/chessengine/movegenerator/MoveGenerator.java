package chessengine.movegenerator;

import java.util.LinkedList;

/**
 *	@author Schuhmacher, Kaub
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
						case pawn_w :	berechneZugBauer(	board, startfeld, true, pawn_moves_w);
										berechne2ZugBauer(	board, startfeld, true, pawn_moves_w2);
										berechneSchlagBauer(board, startfeld, true, pawn_attack_moves_w);	break;
						case rook_w :	berechneSlidingZug(	keineRochadeMehr(board, startfeld), startfeld, true, rook_moves);	break;
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
					case pawn_b :	berechneZugBauer(	board, startfeld, false, pawn_moves_b);
									berechne2ZugBauer(	board, startfeld, false, pawn_moves_b2);
									berechneSchlagBauer(board, startfeld, false, pawn_attack_moves_b);	break;
					case rook_b :	berechneSlidingZug(	keineRochadeMehr(board, startfeld), startfeld, false, rook_moves);			break;
					case knight_b : berechneZug(		board, startfeld, false, knight_moves);	break;
					case bishop_b : berechneSlidingZug(	board, startfeld, false, bishop_moves);	break;
					case king_b : 	berechneZugKing	(	board, startfeld, false, king_moves);	break;
					case queen_b : 	berechneSlidingZug(	board, startfeld, false, queen_moves);	break;
					}
				}
			}
		}
	}
	

	/** 
	 * Diese Hilfsmethode setzt die Rochadenmoeglichkeit vor dem Zug eines Turms zurueck
	 * 
	 * @param startfeld
	 * @param weissAmZug
	 */

	private byte[] keineRochadeMehr(byte[] board, byte startfeld) {
		byte[] neuesBoard = board.clone();
		switch (startfeld) {
		case 0	: neuesBoard[122] = 0;	break;
		case 7	: neuesBoard[121] = 0;	break;
		case 112: neuesBoard[124] = 0;	break;
		case 119: neuesBoard[123] = 0;	break;
		}
		return neuesBoard;
	}
	/**
	 * Diese Methode berechnet Zuege fuer Sliding Pieces (Figuren, die eine beliebige Anzahl an Feldern ziehen duerfen)
	 * 
	 * @param board
	 * @param startfeld
	 * @param weissAmZug
	 * @param erlaubteZuege
	 */
	private void berechneSlidingZug(byte[] board, byte startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		//setze en passant zurueck
		board[125] = -1;

		//fuer alle Schrittweiten der uebergebenen erlaubten Zuege
		for (byte b : erlaubteZuege) {
			byte[] neuesBoard = board.clone();
				
			//Merker, ob das Zielfeld vor dem Zug frei ist, und das Sliding Piece die Schleife nochmals durchlaufen kann 	//<-sliding-Zusatz
			boolean zielfeldFrei = false; //Initialisierung																	//<-sliding-Zusatz
			
			//berechne moegliches Zielfeld
			int zielfeld = b + startfeld;  
			//wenn das Zielfeld ein gueltiges Feld ist
			if ((zielfeld & 136) == 0) {
				//wenn das Zielfeld leer ist
				if (neuesBoard[zielfeld] == 0) {
					
					//Merker fur Sliding Piece																				//<-sliding-Zusatz
					zielfeldFrei = true;																					//<-sliding-Zusatz
					
					//Figur auf Zielfeld ziehen
					neuesBoard[zielfeld] = neuesBoard[startfeld];
					//Startfeld leeren
					neuesBoard[startfeld] = 0;
					//Zug hinzufuegen
					zugHinzufuegen(neuesBoard);
				} else {//wenn Zielfeld besetzt
					//wenn weiss am Zug ist
					if (weissAmZug) {
						//wenn auf dem Zielfeld der Gegner schwarz steht
						if (neuesBoard[zielfeld] < 0) {
							//Figur auf Zielfeld ziehen und damit den Gegner auf dem Zielfeld schlagen
							neuesBoard[zielfeld] = neuesBoard[startfeld];
							//Startfeld leeren
							neuesBoard[startfeld] = 0;
							//Zug hinzufuegen
							zugHinzufuegen(neuesBoard);
						}//wenn weiss am Zug und weiss auf Zielfeld, mache nichts
					} else { //schwarz ist am Zug
						//wenn auf dem Zielfeld der Gegner weiss steht
						if (neuesBoard[zielfeld] > 0) {
							//Figur auf Zielfeld ziehen und damit den Gegner auf dem Zielfeld schlagen
							neuesBoard[zielfeld] = neuesBoard[startfeld];
							//Startfeld leeren
							neuesBoard[startfeld] = 0;
							//Zug hinzufuegen
							zugHinzufuegen(neuesBoard);
						}//wenn schwarz am Zug und schwarz auf Zielfeld, mache nichts
					}//endifelse weiss/schwarz am Zug
				}//endifelse Feld leer/besetzt
																								//ab hier weiterer Zusatz fuer Sliding Pieces -->
				//wenn das Zielfeld vor dem Zug frei war
				if (zielfeldFrei) {						
					//setze die durchgefuehrte Schrittrichtung als einzigen erlaubten Zug in ein Array
					byte[] zugrichtung = {b};																
					//uebergib diese Zugrichtung nochmals der Methode mit dem neuen Startfeld, das jetzt eine Schrittweite weiter liegt
					berechneSlidingZug(neuesBoard, (byte)zielfeld, weissAmZug, zugrichtung);											
				}																												
				

			}//endif "gueltiges Feld?" (wenn Feld nicht gueltig, mache nichts)
			
			
		}//endfor b:erlaubteZuege
	}

	/**
	 * Bauernzug mit Schrittlaenge 1
	 * 
	 * @param board
	 * @param startfeld
	 * @param weissAmZug
	 * @param erlaubteZuege	
	 */
	private void berechneZugBauer(byte[] board, byte startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		byte[] neuesBoard = board.clone();
		
		int zielfeld = startfeld + erlaubteZuege[0];
		//wenn der Zug noch auf dem Brett enden wuerde
		if ((zielfeld & 136) == 0) {
			//Wenn Zielfeld vor dem Bauern leer ist, fuehre Schritt durch 
			if (neuesBoard[zielfeld] == 0) {
				//ruecke Figur
				neuesBoard[zielfeld] = neuesBoard[startfeld];
				//Startfeld leeren
				neuesBoard[startfeld] = 0;
				//setze en passant zurueck
				neuesBoard[125] = -1;
				//Zug hinzufuegen
				zugHinzufuegen(neuesBoard);
			}
		}
	}


	/**
	 * Doppelzug Bauern hardcoded
	 * 
	 * @param board
	 * @param startfeld
	 * @param weissAmZug
	 * @param erlaubteZuege	wird nicht benutzt
	 */
	private void berechne2ZugBauer(byte[] board, byte startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		byte[] neuesBoard = board.clone();
		
		//Uberpruefen, ob 2 Schritte moeglich sind (von Ausgangsposition des Bauern)
		if (weissAmZug) { //wenn weiss am Zug ist
			//wenn das Startfeld auf der Startreihe der weissen Bauern ist
			if (16 <= startfeld && startfeld <= 23) {
				//wenn die Felder 1 und 2 Schritte vor dem Bauern frei sind
				if (neuesBoard[startfeld + 16] == 0 && neuesBoard[startfeld + 32] == 0 ) {
					//mache Zweischrittzug
					neuesBoard[startfeld + 32] = neuesBoard[startfeld];
					neuesBoard[startfeld] = 0;
					//setze en passant Feld
					neuesBoard[125] = (byte)(startfeld + 16);
					//Zug hinzufuegen
					zugHinzufuegen(neuesBoard);
				}
			}
		} else { //wenn schwarz am Zug ist
			//wenn das Startfeld auf der Startreihe der schwarzen Bauern ist
			if (96 <= startfeld && startfeld <= 103) {
				//wenn die Felder 1 und 2 Schritte vor dem Bauern frei sind
				if (neuesBoard[startfeld - 16] == 0 && neuesBoard[startfeld - 32] == 0 ) {
					//mache Zweischrittzug
					neuesBoard[startfeld - 32] = neuesBoard[startfeld];
					neuesBoard[startfeld] = 0;
					//setze en passant Feld
					neuesBoard[125] = (byte)(startfeld - 16);
					//Zug hinzufuegen
					zugHinzufuegen(neuesBoard);
				}
			}
		}//endifelse Ueberpruefung Zweischrittzug
	}
	
	/**
	 * Schlagzug eines Bauern
	 * 
	 * @param board
	 * @param startfeld
	 * @param weissAmZug
	 * @param erlaubteZuege
	 */
	private void berechneSchlagBauer(byte[] board, byte startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		
		//fuer die erlaubten Schrittweiten bei einem Bauernschlag
		for (byte b : erlaubteZuege) {
			byte[] neuesBoard = board.clone();
			//berechne das Zielfeld
			int zielfeld = startfeld + b;
			//Wenn die gegnerische Farbe auf dem Zielfeld steht 
			if ((weissAmZug && neuesBoard[zielfeld] < 0) || (!weissAmZug && neuesBoard[zielfeld] > 0)) {
				//ziehe eigene figur = schlage Gegner
				neuesBoard[zielfeld] = neuesBoard[startfeld];
				//Startfeld leeren
				neuesBoard[startfeld] = 0;
				//setze en passant zurueck
				neuesBoard[125] = -1;
				//Zug hinzufuegen
				zugHinzufuegen(neuesBoard);
			} 
		}
	}

	/**
	 * Diese Methode berechnet einen einfachen Zug (Springer)
	 * 
	 * @param board			aktuelle Stellung als 0x88-Board
	 * @param startfeld		Das Feld, von dem aus gezogen werden soll
	 * @param weissAmZug	true = weiss am Zug, false = schwarz am Zug
	 * @param erlaubteZuege	erlaubte Schrittweiten der Figur
	 */
	private void berechneZug(byte[] board, byte startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		//setze en passant zurueck
		board[125] = -1;
		//fuer alle Schrittweiten der uebergebenen erlaubten Zuege
		for (byte b : erlaubteZuege) {
			byte[] neuesBoard = board.clone();
			//berechne moegliche Zielfelder
			int zielfeld = b + startfeld;
			//wenn das Zielfeld ein gueltiges Feld ist
			if ((zielfeld & 136) == 0) {
				//wenn das Zielfeld leer ist
				if (board[zielfeld] == 0) {
					//Figur auf Zielfeld ziehen
					neuesBoard[zielfeld] = board[startfeld];
					//Startfeld leeren
					neuesBoard[startfeld] = 0;
					//Zug hinzufuegen
					zugHinzufuegen(neuesBoard);
				} else {//wenn Zielfeld besetzt
					//wenn weiss am Zug ist
					if (weissAmZug) {
						//wenn auf dem Zielfeld der Gegner schwarz steht
						if (board[zielfeld] < 0) {
							//Figur auf Zielfeld ziehen und damit den Gegner auf dem Zielfeld schlagen
							neuesBoard[zielfeld] = board[startfeld];
							//Startfeld leeren
							neuesBoard[startfeld] = 0;
							//Zug hinzufuegen
							zugHinzufuegen(neuesBoard);
						}//wenn weiss am Zug und weiss auf Zielfeld, mache nichts
					} else { //schwarz ist am Zug
						//wenn auf dem Zielfeld der Gegner weiss steht
						if (board[zielfeld] > 0) {
							//Figur auf Zielfeld ziehen und damit den Gegner auf dem Zielfeld schlagen
							neuesBoard[zielfeld] = board[startfeld];
							//Startfeld leeren
							neuesBoard[startfeld] = 0;
							//Zug hinzufuegen
							zugHinzufuegen(neuesBoard);
						}//wenn schwarz am Zug und schwarz auf Zielfeld, mache nichts
					}//endifelse weiss/schwarz am Zug
				}//endifelse Feld leer/besetzt
			}//endif "gueltiges Feld?" (wenn Feld nicht gueltig, mache nichts)
		}//endfor b:erlaubteZuege
	}
 
	/**
	 * Diese Methode berechnet den Zug eines Koenigs
	 * 
	 * @param board
	 * @param startfeld
	 * @param weissAmZug
	 * @param erlaubteZuege
	 */
	private void berechneZugKing(byte[] board, byte startfeld, boolean weissAmZug, byte[] erlaubteZuege) {
		byte[] neuesBoard = board.clone();
		
		//setze Rochaden-Marker, dass der Koenig zieht (d.h. keine Rochade mehr fuer die Farbe am Zug moeglich ist)
		if (weissAmZug) {
			neuesBoard[121] = 0;
			neuesBoard[122] = 0;
		} else {
			neuesBoard[123] = 0;
			neuesBoard[124] = 0;
		}
		//uebergebe an die normale Zug-Methode
		berechneZug(neuesBoard, startfeld, weissAmZug, erlaubteZuege);
		
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
	private void zugHinzufuegen(byte[] board) {
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