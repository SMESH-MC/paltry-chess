package chessengine.movegenerator;

import java.util.LinkedList;

/**
 * 
 * @author Schuhmacher, Kaub
 *
 */
public class Rochade0x88
implements Definitions {
	//Die Liste aller moeglichen Rochaden, die zurueckgeschickt wird
	private LinkedList<String> 	moeglicheRochaden;
	
	/**
     * Konstruktor, der die auszugebende Liste aller moeglichen Zuege initialisiert
     */
    public Rochade0x88() {
    	moeglicheRochaden = new LinkedList<String>();
    }
    
	/**
	 * Dieser Setter initialisiert die aktuelle Stellung und prueft dann, welche Rochaden noch durchgefuehrt
	 * werden duerfen mit anschliessender Uebergabe an die jeweilige Methode zur Durchfuehrung
	 * 
	 * @param	board	Die aktuelle Stellung als 0x88-Board:
	 * 
	 * board[0] bis board[119]: A1 bis H8 inkl. des "Geisterboards" rechts des normalen Boards
	 * d.h. 0-7 gültige Felder, 8-15 ungültige, 16-23 gültig, ... 112-119 gültig
	 * board[120] = 1 (Weiss am Zug) | 0 (Schwarz am Zug)
	 * board[121, 122, 123, 124] = Bitmarker ( 0 | 1) fuer Rochademoeglichkeiten: K Q k q 
	 * board[125] =  En-Passant-Feld des letzten Zuges in 0x88-Darstellung (z.B.: 83 = board[83] = D6)
	 * board[126] = Anzahl der Halbzuege
	 * board[127] = Zugnummer
	 */
	public void setSchachbrett(byte[] board) {
		/* Wenn die jeweiligen Rochade noch moeglich ist, rufe die der Rochade zugehoerige
		 * Methode auf uebergib das zurueckgegebene rochierte Board an den FEN-Encoder. Schreibe die ermittelte FEN
		 * abschliessend in die Liste der moeglichen Rochaden.
		 */
		//Wenn Rochade noch moeglich && der Koenig auf seinem EroeffnungsFeld steht && der jeweilige Turm ebenfalls
		if (board[120] == 1) { //wenn weiss am Zug ist
			if (board[121] == 1 && board[4] == king_w && board[7] == rook_w) {rochiereKurzWeiss(board);}
			if (board[122] == 1 && board[4] == king_w && board[0] == rook_w) {rochiereLangWeiss(board);}
		} else {
			if (board[123] == 1 && board[116] == king_b && board[119] == rook_b) {rochiereKurzSchwarz(board);}
			if (board[124] == 1 && board[116] == king_b && board[112] == rook_b) {rochiereLangSchwarz(board);}
		}
	}


	/**
	 * Diese Methode fuehrt eine kurze Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
	 */
	private void rochiereKurzWeiss(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (F1 und G1) frei ist
				(boardZuRochieren[5] == 0 && boardZuRochieren[6] == 0) &&
				/* ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				 * ueberpruefen, ob das Zielfeld des Turms (= das Feld, ueber das der Koenig ziehen muss) nicht bedroht wird 
				 * ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird 
				 */
				//pruefen, ob ein Rochaden-Zug nach den obigen 3 Punkten erlaubt ist
				!istRochadeIllegal(boardZuRochieren, true, true) //board, kurze Rochade, weiss am Zug
				) {
			//kopiere das aktuelle Board
			byte[] boardNachRochade =  boardZuRochieren.clone();
			//Setze Koenig auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[6] = king_w;
			boardNachRochade[4] = 0;
			//Setze Turm auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[5] = rook_w;
			boardNachRochade[7] = 0;
			//Moeglichkeiten der weissen Rochade entfernen
			boardNachRochade[121] = 0;
			boardNachRochade[122] = 0;
			//fuege mit allgemeinr Hilfsmethode den Rochaden-Zug zur Liste der moeglichen Rochaden
			rochadeHinzufuegen(boardNachRochade);
		}
	}	
	
	/**
	 * Diese Methode fuehrt eine lange Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
	 */
	private void rochiereLangWeiss(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (B1, C1, D1) frei ist
				(boardZuRochieren[1] == 0 && boardZuRochieren[2] == 0 && boardZuRochieren[3] == 0) &&
				/* ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				 * ueberpruefen, ob das Zielfeld des Turms (= das Feld, ueber das der Koenig ziehen muss) nicht bedroht wird 
				 * ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird 
				 */
				//pruefen, ob ein Rochaden-Zug nach den obigen 3 Punkten erlaubt ist
				!istRochadeIllegal(boardZuRochieren, false, true) //board, lange Rochade, weiss am Zug
			) {
			//kopiere das aktuelle Board
			byte[] boardNachRochade =  boardZuRochieren.clone();
			//Setze Koenig auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[2] = king_w;
			boardNachRochade[4] = 0;
			//Setze Turm auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[3] = rook_w;
			boardNachRochade[0] = 0;
			//Moeglichkeiten der weissen Rochade entfernen
			boardNachRochade[121] = 0;
			boardNachRochade[122] = 0;
			//fuege mit allgemeinr Hilfsmethode den Rochaden-Zug zur Liste der moeglichen Rochaden
			rochadeHinzufuegen(boardNachRochade);
		}
	}

	/**
	 * Diese Methode fuehrt eine kurze Rochade von Schwarz durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
	 */

	private void rochiereKurzSchwarz(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (F8 und G8) frei ist
				(boardZuRochieren[117] == 0 && boardZuRochieren[118] == 0) &&
				/* ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				 * ueberpruefen, ob das Zielfeld des Turms (= das Feld, ueber das der Koenig ziehen muss) nicht bedroht wird 
				 * ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird 
				 */
				//pruefen, ob ein Rochaden-Zug nach den obigen 3 Punkten erlaubt ist
				!istRochadeIllegal(boardZuRochieren, true, false) //board, kurze Rochade, schwarz am Zug
				) {
			//kopiere das aktuelle Board
			byte[] boardNachRochade =  boardZuRochieren.clone();
			//Setze Koenig auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[118] = king_b;
			boardNachRochade[116] = 0;
			//Setze Turm auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[117] = rook_b;
			boardNachRochade[119] = 0;
			//Moeglichkeiten der schwarzen Rochade entfernen
			boardNachRochade[123] = 0;
			boardNachRochade[124] = 0;
			//fuege mit allgemeinr Hilfsmethode den Rochaden-Zug zur Liste der moeglichen Rochaden
			rochadeHinzufuegen(boardNachRochade);
		}
	}

	/**
	 * Diese Methode fuehrt eine lange Rochade von Schwarz durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
	 */

	private void rochiereLangSchwarz(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (B8, C8, D8) frei ist
				(boardZuRochieren[113] == 0 && boardZuRochieren[114] == 0 && boardZuRochieren[115] == 0) &&
				/* ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				 * ueberpruefen, ob das Zielfeld des Turms (= das Feld, ueber das der Koenig ziehen muss) nicht bedroht wird 
				 * ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird 
				 */
				//pruefen, ob ein Rochaden-Zug nach den obigen 3 Punkten erlaubt ist
				!istRochadeIllegal(boardZuRochieren, false, false) //board, lange Rochade, schwarz am Zug
			) {
			//kopiere das aktuelle Board
			byte[] boardNachRochade =  boardZuRochieren.clone();
			//Setze Koenig auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[114] = king_b;
			boardNachRochade[116] = 0;
			//Setze Turm auf sein Zielfeld und leere sein Startfeld
			boardNachRochade[115] = rook_b;
			boardNachRochade[112] = 0;
			//Moeglichkeiten der schwarzen Rochade entfernen
			boardNachRochade[123] = 0;
			boardNachRochade[124] = 0;
			//fuege mit allgemeinr Hilfsmethode den Rochaden-Zug zur Liste der moeglichen Rochaden
			rochadeHinzufuegen(boardNachRochade);
		}	}

	/**
	 * Diese Methode prueft, ob der Koenig im Schach steht, ins Schach rochieren wuerde oder
	 * ueber ein bedrohtes Feld rochieren muesste
	 * 
	 * @param	board			Die Stellung, von der aus die Rochade ueberprueft werden soll
	 * @param	kurzeRochade	true = kurze Rochade, false = lange Rochade
	 * @param	weissAmZug		true = weiss am Zug, false = schwarz am Zug
	 * @return true = eines der drei Felder, die bei einer Rochade mit em Koenig zu tun haben, wird bedroht
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
				rochadeIstIllegal = (wirdBedroht(board, e1, true) || wirdBedroht(board, d1, true) || wirdBedroht(board, c1, true));
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
					//addiere das Feld, auf dem ein schwarzer Bauer steht, mit seinen moegliche Schlagzuegen und
					//gib true aus, wenn er auf das Zielfeld schlagen kann
					case pawn_b  :	for (byte b : pawn_attack_moves_b) {if (i - b == zielFeld){return true;} }
						break;
					//addiere das Feld, auf dem ein schwarzer Springer steht, mit seinen moegliche Schlagzuegen und
					//gib true aus, wenn er auf das Zielfeld schlagen kann
					case knight_b: 	for (byte b : knight_moves) {if (i - b == zielFeld){return true;} }
						break;
					//berechne mit Hilfsmethode, ob schwarze Dame das Zielfeld schlagen koennte und gib, falls ja, true aus  
					case queen_b : 	if (slidingZug(queen_moves, (byte)i, board, zielFeld)) {return true;};
						break;
					//berechne mit Hilfsmethode, ob schwarzer Laeufer das Zielfeld schlagen koennte und gib, falls ja, true aus
					case bishop_b: 	if (slidingZug(bishop_moves, (byte)i, board, zielFeld)) {return true;};
						break;
					//berechne mit Hilfsmethode, ob schwarzer Turm das Zielfeld schlagen koennte und gib, falls ja, true aus						
					case rook_b : 	if (slidingZug(rook_moves, (byte)i, board, zielFeld)) {return true;};
						break;
					//addiere das Feld, auf dem der schwarze Koenig steht, mit seinen moegliche Schlagzuegen und
					//gib true aus, wenn er auf das Zielfeld schlagen kann
					case king_b : 	for (byte b : king_moves) {if (i - b == zielFeld){return true;} }
						break;
					default : break; //mache in allen anderen Faellen nichts
					}//endswitch
				}//endif
			}//endfor
		} else { //Schwarz ist am Zug
			//fuer alle gueltigen Felder des Boards
			for (int i=0; i<=119; i++) {
				if  ((i & 136) == 0) {
				/*
				 * Hinweis: fuer i+b kann der Wert über Byte.MAX steigen und wird daher negativ (Ueberlauf).
				 * Da jedoch mit dem stets positiven zielFeld verglichen wird, ist dies unerheblich,
				 * da das Vergleichsergebnis auch im negativen Fall dann ungleich zielFeld ist.
				 */
					switch (board[i]) {
					//addiere das Feld, auf dem ein weisser Bauer steht, mit seinen moegliche Schlagzuegen und
					//gib true aus, wenn er auf das Zielfeld schlagen kann
					case pawn_w  :	for (byte b : pawn_attack_moves_w) {if (i + b == zielFeld){return true;} }
						break;
					//addiere das Feld, auf dem ein weisser Springer steht, mit seinen moegliche Schlagzuegen und
					//gib true aus, wenn er auf das Zielfeld schlagen kann
					case knight_w: 	for (byte b : knight_moves) {if (i + b == zielFeld){return true;} }
						break;
					//berechne mit Hilfsmethode, ob weisse Dame das Zielfeld schlagen koennte und gib, falls ja, true aus  
					case queen_w : 	if (slidingZug(queen_moves, (byte)i, board, zielFeld)) {return true;};
						break;
					//berechne mit Hilfsmethode, ob weisser Laeufer das Zielfeld schlagen koennte und gib, falls ja, true aus
					case bishop_w: 	if (slidingZug(bishop_moves, (byte)i, board, zielFeld)) {return true;};
						break;
					//berechne mit Hilfsmethode, ob weisser Turm das Zielfeld schlagen koennte und gib, falls ja, true aus						
					case rook_w : 	if (slidingZug(rook_moves, (byte)i, board, zielFeld)) {return true;};
						break;
					//addiere das Feld, auf dem der weisse Koenig steht, mit seinen moegliche Schlagzuegen und
					//gib true aus, wenn er auf das Zielfeld schlagen kann
					case king_w : 	for (byte b : king_moves) {if (i + b == zielFeld){return true;} }
						break;
					default : break; //mache in allen anderen Faellen nichts
					}
				}
				
			}
			
		}
		//wenn keine Bedrohung gefunden wurde, gibt false zurueck
		return false;
	}

		
	/**
	 * Hilfsmethode zur Schleifenberechnung von Figuren, die beliebig viele Felder ziehen duerfen
	 * 
	 * @param	erlaubteZuege	Die moeglichen Schritte der Figur
	 * @param	startfeld	Das Startfeld der Figur
	 * @param	board	Die aktuelle Stellung als 0x88-Board
	 * @param	zielfeld	Das zu ueberpruefende Zielfeld
	 * @return true = Figur vom Startfeld bedroht das Zielfeld
	 */
	private boolean slidingZug(byte[] erlaubteZuege, byte startfeld, byte[] board, byte zielfeld) {
		//Initialisierung der return-Variable (true = zielfeld kann mit erlaubteZuege von startfeld aus erreicht werden) 
		boolean zielfeldSchlagbar = false;
		
		//fuer alle Schrittweiten der uebergebenen erlaubten Zuege
		for (byte b : erlaubteZuege) {
			//berechne das naechste moegliche Zielfeld von startfeld ausgehend
			int nextFeld = startfeld + b; 
			//wenn dieses naechste moegliche Zielfeld ein gueltiges Feld ist
			if ((nextFeld & 136) == 0) {
				if (nextFeld == zielfeld) { //wenn dieses naechste moegliche Zielfeld das uebergebene, zu ueberpruefende zielfeld ist 
					return true;	//gib zurueck, dass zielfeld erreicht werden kann
				} else { //wenn das naechste moegliche Zielfeld nicht das uebergebene, zu ueberpruefende zielfeld ist
					//wenn dieses naechste moegliche Zielfeld frei ist
					if (board[zielfeld] == 0) {
						//setze die durchgefuehrte Schrittrichtung als einzigen erlaubten Zug in ein Array
						byte[] zugrichtung = {b};																
						//uebergib diese Zugrichtung nochmals der Methode mit dem neuen Startfeld, das jetzt eine Schrittweite weiter liegt
						zielfeldSchlagbar = slidingZug(zugrichtung, (byte)nextFeld, board, (byte)zielfeld);											
					}
				}
			}
		}
		return zielfeldSchlagbar;
	}
	
	/**
	 * Hilfsmethode, die einen Rochaden-Zug zu der Liste der moeglichen Rochaden-Zuege hinzufuegt
	 * 
	 * @param boardNachRochade	Das uebergebene Board nach der Rochade in 0x88-Darstellung
	 */
	private void rochadeHinzufuegen(byte[] boardNachRochade) {
		//setze en passant zurueck
		boardNachRochade[125] = -1;
		//erstelle einen neuen Board-nach-FEN-Encoder und uebergib ihm das Board, wie es nach erfolgter Rochade aussieht 
		FenEncode fr = new FenEncode();
		fr.setBoard(boardNachRochade);
		//Nimm das zu einem FEN-String encodierte Board und fuege es der Liste der moeglichen Rochaden hinzu
		moeglicheRochaden.add(fr.getFEN());
	}

	
	/**
	 * Dieser getter gibt die Liste der moeglichen Zuege zurueck
	 * 
	 * @return Die Liste aller moeglichen Zuege
	 */
	public LinkedList<String> getZuege() {
		return moeglicheRochaden;
	}

	
}
