package chessengine.movegenerator;

import java.util.LinkedList;

/**
 * 
 * @author Schuhmacher, Kaub
 * @version 201309111303
 *
 */
public class Rochade0x88
implements Definitions {
	//Die Liste aller moeglichen Rochaden, die zurueckgeschickt wird
	private LinkedList<String> 	moeglicheRochaden;
	
	/**
	 * Dieser Setter initialisiert die aktuelle Stellung und prueft dann, welche Rochaden noch durchgefuehrt
	 * werden duerfen mit anschliessender Uebergabe an die jeweilige Methode zur Durchfuehrung
	 * 
	 * @param	board	Die aktuelle Stellung als 0x88-Board:
	 * 
	 * board[0] bis board[119]: A1 bis H8 inkl. des "Geisterboards" rechts des normalen Boards
	 * d.h. 0-7 g�ltige Felder, 8-15 ung�ltige, 16-23 g�ltig, ... 112-119 g�ltig
	 * board[120] = 1 (Weiss am Zug) | 0 (Schwarz am Zug)
	 * board[121, 122, 123, 124] = Bitmarker ( 0 | 1) fuer Rochademoeglichkeiten: K Q k q 
	 * board[125] =  En-Passant-Feld des letzten Zuges in 0x88-Darstellung (z.B.: 83 = board[83] = D6)
	 * board[126] = Anzahl der Halbzuege
	 * board[127] = Zugnummer 
	 */
	public void setSchachbrett(byte[] board) {
		//Erstelle ein Objekt der Klasse FenEncode
		FenEncode f1 = new FenEncode();
		/* Wenn die jeweiligen Rochaden moeglich sind, rufe die dazugehoerige Methode auf und
		 * uebergib das zurueckgegebene rochierte Board an den FEN-Encoder. Schreibe die ermittelte FEN
		 * anhschliessend in die Liste der moeglichen Rochaden.
		 */
		if (board[121] == 1) {f1.setBoard(rochiereKurzWeiss(board)); moeglicheRochaden.addLast(f1.getFEN());}
		if (board[122] == 1) {f1.setBoard(rochiereLangWeiss(board)); moeglicheRochaden.addLast(f1.getFEN());}
		if (board[123] == 1) {f1.setBoard(rochiereKurzSchwarz(board)); moeglicheRochaden.addLast(f1.getFEN());}
		if (board[124] == 1) {f1.setBoard(rochiereLangSchwarz(board)); moeglicheRochaden.addLast(f1.getFEN());}
	}

	/**
	 * Diese Methode fuehrt eine kurze Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgef�hrt werden soll
	 */
	private byte[] rochiereKurzWeiss(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (F1 und G1) frei ist
				((boardZuRochieren[5] | boardZuRochieren[6]) == 0) &&
				//ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
				//ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
				
				//ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
			
				//legaler Rochaden-Zug (die letzten 3 Punkte zusammengefasst):
				!istRochadeIllegal(boardZuRochieren, true, true) //board, kurze Rochade, weiss am Zug
				) {
			byte[] boardNachRochade 
			
			
			/* alte FEN_Rochade
			
					String[] newSplittedFEN = splittedFEN.clone();
					newSplittedFEN[7].replace("K2R", "1RK1");
					newSplittedFEN[7].replace("11", "2");
					newSplittedFEN[7].replace("21", "3");
					newSplittedFEN[7].replace("31", "4");
					newSplittedFEN[7].replace("41", "5");
					//Zug ist erledigt
					//Rochademoeglichkeit entfernen
					newSplittedFEN[9].replace("K", "");
					//pruefen ob alle Rochademoeglichkeiten nicht mehr vorhanden sind
					if (newSplittedFEN[9].equals("")) {
						newSplittedFEN[9] = "-";
					}
					//Uebergabe, um aus fertigem Zug den FEN-String zu basteln
					String newFEN = setFEN(newSplittedFEN);
					//fuegt diese berechnete Rochade der Liste der gueltigen Zuege hinzu 
					outgoingFEN.addLast(newFEN);
			*/
		}
	}	
	
	/**
	 * Diese Methode fuehrt eine lange Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgef�hrt werden soll
	 */
	private byte[] rochiereLangWeiss(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (F1 und G1) frei ist
				((boardZuRochieren[1] | boardZuRochieren[2] | boardZuRochieren[3]) == 0)
				//ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
				//ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
				
				//ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
			
				) {
			//rochieren Q
			
		}
		
	/**
	 * Diese Methode prueft, ob der Koenig im Schach steht, ins Schach rochieren wuerde oder
	 * ueber ein bedrohtes Feld rochieren muesste
	 * 
	 * @param	board			Die Stellung, von der aus die Rochade ueberprueft werden soll
	 * @param	kurzeRochade	true = kurze Rochade, false = lange Rochade
	 * @return	weissAmZug		true = weiss am Zug, false = schwarz am Zug
	 */
	private boolean istRochadeIllegal(byte[] board, boolean kurzeRochade, boolean weissAmZug) {
		boolean rochadeIstIllegal = false;
		if (kurzeRochade) {
			if (weissAmZug) {
				//prueft, ob eines der weissen Rochadenfelder bedroht wird
				rochadeIstIllegal = (wirdBedroht(board, e1, true) || wirdBedroht(board, f1, true) || wirdBedroht(board, g1, true));
			} else { //Schwarz ist am Zug
				//prueft, ob eines der schwarzen Rochadenfelder bedroht wird
				rochadeIstIllegal = (wirdBedroht(board, e8, false) || wirdBedroht(board, f8, false) || wirdBedroht(board, g8, false));
			}
		} else { //bei langer Rochade
			if (weissAmZug) {
				//prueft, ob eines der weissen Rochadenfelder bedroht wird
				rochadeIstIllegal = (wirdBedroht(board, e1, true) || wirdBedroht(board, d2, true) || wirdBedroht(board, c1, true));
			} else { //Schwarz ist am Zug
				//prueft, ob eines der schwarzen Rochadenfelder bedroht wird
				rochadeIstIllegal = (wirdBedroht(board, e8, false) || wirdBedroht(board, d8, false) || wirdBedroht(board, c8, false));
			}
		}
		return rochadeIstIllegal;
	}
			


	/** Diese Methode prueft, ob ein angegebenes Zielfeld angegriffen wird.
	 * Zur Ueberpruefung von "Schach" und Rochade
	 *
	 * @author Marc Pierce (Original C#), Sascha Schuhmacher (Umsetzung nach Java passend zur Projekarbeit)
	 * @see <a href="http://stackoverflow.com/questions/7197369/chess-programming-no-ai-moves-validation?answertab=votes#tab-top">Original-Code<a>
	 * @param	board		Die aktuelle Stellung als 0x88-Board
	 * @param	zielFeld	Die Felder, die ueberprueft werden sollen
	 * @param	weissAmZug	Wer ist am Zug? true = weiss, false = schwarz
	 * @return	true = Zielfeld wird bedroht
	 */ 
	private boolean wirdBedroht(byte[] board, byte zielFeld, boolean weissAmZug) {
			if (weissAmZug) {
				//fuer alle gueltigen Felder des Boards
				for (int i=0; i<=119 ; i++) {
					if ((i & 136) == 0) {
						switch (board[i]) {
						/*
						 * Hinweis: fuer i-b kann der Wert negativ werden. Da jedoch mit dem stets positiven 
						 * zielFeld verglichen wird, ist dies unerheblich, da das Vergleichsergebnis auch im 
						 * negativen Fall dann ungleich zielFeld ist.
						 */
						//addiere das Feld, auf dem der Bauer steht, mit seinen moegliche Schlagzuegen und
						//gib true aus, wenn er auf das Zielfeld schlagen kann
						case pawn_b  :	for (byte b : pawn_attack_moves) {if (i - b == zielFeld){return true;} }
							break;
						//addiere das Feld, auf dem der Springer steht, mit seinen moegliche Schlagzuegen und
						//gib true aus, wenn er auf das Zielfeld schlagen kann
						case knight_b: 	for (byte b : knight_moves) {if (i - b == zielFeld){return true;} }
							break;
						case queen_b : 	slidingZug(queen_moves, (byte)i, board, zielFeld, true);
							break;
						case bishop_b: 	slidingZug(bishop_moves, (byte)i, board, zielFeld, true);
							break;
						case rook_b : 	slidingZug(rook_moves, (byte)i, board, zielFeld, true);
							break;
						//addiere das Feld, auf dem der Koenig steht, mit seinen moegliche Schlagzuegen und
						//gib true aus, wenn er auf das Zielfeld schlagen kann
						case king_b : 	for (byte b : king_moves) {if (i - b == zielFeld){return true;} }
							break;
						}//endswitch
					}//endif
				}//endfor
				//wenn keine Bedrohung gefunden wurde, gibt false zurueck
				return false;
			} else { //Schwarz ist am Zug
				//fuer alle gueltigen Felder des Boards
				for (int i=0; i<=119; i++) {
					/*
					 * Hinweis: fuer i+b kann der Wert �ber Byte.MAX steigen und wird daher negativ.
					 * Da jedoch mit dem stets positiven zielFeld verglichen wird, ist dies unerheblich,
					 * da das Vergleichsergebnis auch im negativen Fall dann ungleich zielFeld ist.
					 */
					if  ((i & 136) == 0) {
						switch (board[i]) {
						case pawn_w 	: 
							break;
						case knight_w 	: 
							break;
						case queen_w 	: 
							break;
						case bishop_w 	: 
							break;
						case rook_w 	: 
							break;
						case king_w 	: 
							break;
						}
					}
				}
				wirdBedroht = true;
			}
			return wirdBedroht;
		}

		
	/**
	 * Hilfsmethode zur Schleifenberechnung von Figuren, die beliebig viele Felder ziehen duerfen
	 * 
	 * @return
	 */
	private boolean slidingZug(byte[] erlaubteZuege, byte startfeld, byte[] board, byte zielfeld, boolean weissAmZug) {
		if (weissAmZug) {
			for (byte b : erlaubteZuege) {
				//gehe eine 1 Feld in die Schrittrichtung
				int feldNachSchritt = startfeld + b;
				do {//gebe true aus, wenn das erreichte Feld das zu ueberpruefende Zielfeld ist
					if (feldNachSchritt == zielfeld) {return true;}
					//gehe ansonsten 1 Feld in Schrittrichtung weiter
					feldNachSchritt -= b;
				} while (board[feldNachSchritt] == 0);	//solang das erreichte Schrittfeld leer ist,
													//d.h. die Figur weiterziehen kann
			}
		} else { //schwarz am Zug 
			
		
		}
		return false;
	}
		
	public LinkedList<String> getZuege() {
		return moeglicheRochaden;
	}

	
}