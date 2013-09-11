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
	 * d.h. 0-7 gültige Felder, 8-15 ungültige, 16-23 gültig, ... 112-119 gültig
	 * board[120] = 1 (Weiss am Zug) | 0 (Schwarz am Zug)
	 * board[121, 122, 123, 124] = Bitmarker ( 0 | 1) fuer Rochademoeglichkeiten: K Q k q 
	 * board[125] =  En-Passant-Feld des letzten Zuges in 0x88-Darstellung (z.B.: 83 = board[83] = D6)
	 * board[126] = Anzahl der Halbzuege
	 * board[127] = Zugnummer 
	 */
	
	public void setSchachbrett(byte[] board) {
		for (byte i=121; i<=124; i++ ) {
			if (board[i] == 1) {
				byte[] boardNachRochade = new byte[128];
				switch (i) {
				case 121 : boardNachRochade = rochiereKurzW(board);
					break;
				case 122 : boardNachRochade = rochiereLangW(board);
					break;
				case 123 : boardNachRochade = rochiereKurzB(board);
					break;
				case 124 : boardNachRochade = rochiereLangB(board);
					break; 
				}
				fenEncode(boardNachRochade);
			}
			 
		}
	}

	/**
	 * Diese Methode prueft, ob der Koenig im Schach steht, ins Schach rochieren wuerde oder
	 * ueber bedrohte Felder rochieren muesste
	 * 
	 * @param	board			Die Stellung, von der aus die Rochade ueberprueft werden soll
	 * @param	kurzeRochade	true = kurze Rochade, false = lange Rochade
	 * @return	weissAmZug		true = weiss am Zug, false = schwarz am Zug
	 */
	private boolean istRochadeLegal(byte[] board, boolean kurzeRochade, boolean weissAmZug) {
		if
	}
		
	}
	
	/**
	 * Diese Methode fuehrt eine kurze Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
	 */
	private byte[] rochiereKurzW(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (F1 und G1) frei ist
				((boardZuRochieren[5] | boardZuRochieren[6]) == 0) &&
				//ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
				//ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
				
				//ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
			
				//legaler Rochaden-Zug:
				istRochadeLegal(boardZuRochieren, true, true)
				) {
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
		}
	}	
	
	/**
	 * Diese Methode fuehrt eine lange Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	boardZuRochieren	Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
	 */
	private byte[] rochiereLangW(byte[] boardZuRochieren) {
		if ( //uberpruefen, ob Zwischenraum (F1 und G1) frei ist
				((boardZuRochieren[1] | boardZuRochieren[2] | boardZuRochieren[3]) == 0)
//	ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
				//ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
				
				//ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
			
				) {
			//rochieren Q
		}
	public LinkedList<String> getZuege() {
		return moeglicheRochaden;
	}

	
}
