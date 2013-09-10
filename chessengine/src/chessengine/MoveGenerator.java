package chessengine;

import java.util.LinkedList;


/**
 *	@author Schuhmacher, Kaub
 *	@version 201309091010
 */
public class MoveGenerator implements MoveGeneratorInterface {
	//Klassenvariablen
    private String[] 	splittedFEN = new String[13];	//Initialisierungs-Array, das spaeter den geteilten FEN-String aufnimmt 
    private LinkedList<String> outgoingFEN = new LinkedList<String>();	//Die Liste aller moeglichen Zuege, die zurueckgeschickt wird
    //private int[]		board		= new int[128];		//Initialisierung fuer die aktuelle Stellung in 0x88-Darstellung
    
    //Konstante fuer leere Felder
    private static final int leeresFeld = 0;
    //Konstanten fur die weissen Figuren
    private static final int pawn_w		= 1;
	private static final int knight_w	= 2;
    private static final int queen_w	= 3;
    private static final int bishop_w	= 5;
    private static final int rook_w		= 6;
    private static final int king_w		= 7;
    //Konstanten fur die schwarzen Figuren
    private static final int pawn_b		= -1;
	private static final int knight_b	= -2;
    private static final int queen_b	= -3;
    private static final int bishop_b	= -5;
    private static final int rook_b		= -6;
    private static final int king_b		= -7;
    //KKonstanten fuer die Zuege
    private static final int[] pawn_moves	= {16, 32, 15, 17};
    private static final int[] bishop_moves	= {-17, -15, 15, 17};
    private static final int[] rook_moves	= {-16, -1, 1, 16};
    private static final int[] queen_moves	= {-17, -16, -15, -1, 1, 15, 16, 17} ;
    private static final int[] king_moves	= {-17, -16, -15, -1, 1, 15, 16, 17};
    private static final int[] knight_moves	= {-33, -31, -18, -14, 14, 18, 31, 33};
  
    												 
	/*
	 * Dies ist die einzige public-Methode dieser Klasse, die mit dem aktuellen FEN aufgerufen wird, diesen als Klassenvariable
	 * speichert und dann daraus moegliche Zuege ausgibt
	 * 
	 * @param	aktuelleFEN	 FEN-String der aktuellen Stellung
	 * @return	Liste aller daraus moeglichen Zuege
	 */
	public LinkedList<String> getZuege(String aktuelleFEN) {
		return zuegeBerechnen(aktuelleFEN);
	}
	
	/*
     * 	Diese Methode optimiert die interne Darstellung der aktuellen Stellung und generiert dann alle moeglichen Zuege
     * 
     *   @param		fen		FEN-String der aktuellen Stellung
     *   @return	Liste aller moeglichen Zuege
     */
    private LinkedList<String> zuegeBerechnen(String fen) {
    	//macht aus dem FEN-String ein int-Array in 0x88 Darstellung
    	int[] schachbrett = fenEntknoddeln(fen);
    	//1.: Moeglichkeiten der Rochaden berechnen
        /* wenn der Inhalt NICHT "-" ist, stosse Rochadenueberpruefung an, die dann eventuell moegliche Rochadenzuege in die Liste
         * der moeglichen Zuege schreibt */
    	if (!splittedFEN[9].equals("-")) {
        	if (splittedFEN[8].equals("w")) {
        		berechneWhiteRochade(splittedFEN[9]);
        	} else {
        		berechneBlackRochade(splittedFEN[9]);
        	}
        }
    	//2.: "normale" Zugmoeglichkeiten berechnen
    	berechneNormaleZuege(schachbrett);
    	
    	return outgoingFEN;
    }
    
    /*
     * Diese Methode splittet den FEN-String an allen Leerzeichen und Slashes, packt die einzelnen Bestandteile in ein Array,
     * berechnet dann mit Hilfsmethoden die 0x88-Darstellung und gibt diese als int-Array aus
     * 
     * @param	fen		Der FEN-String, der in das int-Array umgewandelt werden soll		
     * @return	Die aktuelle Stellung in 0x88-Darstellung als int-Array
     */
	private int[] fenEntknoddeln(String fen) {
        splittedFEN = fen.split("[ /]");
        /* =>
         * //Stellungen:
         * splittedFEN[0] = Reihe 8
         * splittedFEN[1] = Reihe 7
         * splittedFEN[2] = Reihe 6
         * splittedFEN[3] = Reihe 5
         * splittedFEN[4] = Reihe 4
         * splittedFEN[5] = Reihe 3
         * splittedFEN[6] = Reihe 2
         * splittedFEN[7] = Reihe 1
         * jede Reihe besteht von A bis H entweder aus Figuren oder aus einer Anzahl aufeinanderfolgender Leerfelder
         * z.B. 8 (leere Reihe), rnbqkbnr (Anfangsstellung schwarz), nB1p3N (im Spiel)
         * //sonstiges:
         * splittedFEN[8] = activeColor		w|b
         * splittedFEN[9] = castlingRight	- | [K][Q][k][q]
         * splittedFEN[10] = enPassant		- | ( a|b|c|d|e|f|g|h 3|6   )  //ohne Leerzeichen 
         * splittedFEN[11] = halbzuege		0 | positive Ganzzahl
         * splittedFEN[12] = zugnummer		positive Ganzzahl
         */
        
        //macht aus dem aktuellen FEN ein int-Array in 0x88-Darstellung
        int[] schachbrett = stringTo0x88Array(appendStellungsStrings());
        //gibt das schachbrett (also die aktuelle Stellung) als int-Array in 0x88-Darstellung zurueck
        return schachbrett; //(nur das nich gelb untersrich) //Komentar iknorisieren
	}//end fenEntknoddel fen ent fente ente ente ente!
	
	/*
	 * Diese Hilfsmethode nimmt die Stellungs-Teile des bereits geteilten FEN-Strings (Klassenvariable) und
	 * fuegt diese vorbereitend auf die Umwandlung in 0x88-Darstellung zusammen
	 * 
	 *  @return		aktuelle Stellung als String in 0x88-Darstellung
	 */
	private String appendStellungsStrings() {
		String kompletter0x88String =
				leerfelderTrennen(splittedFEN[7]) + "xxxxxxxx" +
				leerfelderTrennen(splittedFEN[6]) + "xxxxxxxx" +
				leerfelderTrennen(splittedFEN[5]) + "xxxxxxxx" +
				leerfelderTrennen(splittedFEN[4]) + "xxxxxxxx" +
				leerfelderTrennen(splittedFEN[3]) + "xxxxxxxx" +
				leerfelderTrennen(splittedFEN[2]) + "xxxxxxxx" +
				leerfelderTrennen(splittedFEN[1]) + "xxxxxxxx" +
				leerfelderTrennen(splittedFEN[0]) + "xxxxxxxx";
		return kompletter0x88String ;
	}
	/*
	 * Hilfsmethode, die im FEN-String eine Reihe aufeinanderfolgender leere Felder in aufeinanderfolge einzelne leere Felder aendert
	 * 
	 * @param fenReihe	Eine einzelne Reihe der aktuellen Stellung in FEN
	 * @return	String der Laenge 8: Die einzelne Reihe der aktuellen Stellung mit einzelner Bezeichnung der Leerfelder
	 */
	private String leerfelderTrennen(String fenReihe) {
		fenReihe.replace("2","11");
		fenReihe.replace("3","111");
		fenReihe.replace("4","1111");
		fenReihe.replace("5","11111");
		fenReihe.replace("6","111111");
		fenReihe.replace("7","1111111");
		fenReihe.replace("8","11111111");
		return fenReihe;
	}
	
	/*
	 *  Diese Methode berechnet aus einem String in 0x88-Darstellung ein int-Array (0x88-Darstellung) mit zugehoeriger
	 *  Repraesentation der Figuren durch int
	 *  
	 *  @param	string0x88komplett	Ein String in 0x88-Darstellung, der in ein int-Array in 0x88-Darstellung ungewandelt werden soll
	 *  @return Die aktuelle Stellung als int-Array in 0x88-Darstellung 
	 */
	private int[] stringTo0x88Array(String string0x88komplett) {
		int[] intReihe = new int[128];
		for (int i=0; i<128; i++) {
			switch (string0x88komplett.charAt(i)) {
			/*
			 * x = Feld des 0x88-"Geisterboards"
			 * empty=	0
			 * pawn=	1
			 * knight=	2
			 * king=	3
			 * bishop=	5
			 * rook=	6
			 * queen=	7
			 * white +, black -
			 */
			case 'x' :	break; //
			case '1' : intReihe[i] = leeresFeld;
				break;
			case 'P' : intReihe[i] = pawn_w;
				break;
			case 'p' : intReihe[i] = pawn_b;
				break;
			case 'R' : intReihe[i] = rook_w;
				break;
			case 'r' : intReihe[i] = rook_b;
				break;
			case 'N' : intReihe[i] = knight_w;
				break;
			case 'n' : intReihe[i] = knight_b;
				break;
			case 'B' : intReihe[i] = bishop_w;
				break;
			case 'b' : intReihe[i] = bishop_b;
				break;
			case 'K' : intReihe[i] = king_w;
				break;
			case 'k' : intReihe[i] = king_b;
				break;
			case 'Q' : intReihe[i] = queen_w;
				break;
			case 'q' : intReihe[i] = queen_b;
				break;
			}
		}
		return intReihe;
	}
	
	/*
	 * 
	 * @param	board	Die aktuelle Stellung, aus der alle normalen Zuege (ohne Sonderzuege) berechnet werden sollen
	 */
	private void berechneNormaleZuege(int[] board) {
		//Gehe das gesamte Board-Array durch, bis nur noch Felder des "Geisterboards" kommen
		for (int i = 0; i < 120; i++) {
			//wenn das Feld mit Index i ein Feld des gueltigen Schachbretts ist und dies dann kein leeres Feld ist,
			if ((i & 136) == 0	&&	board[i] != 0) {
				//Wenn der Wert an diesem Index positiv ist, also Weiss am Zug ist
				if (board[i] > 0) {
					//Uebergib das Board un den aktuellen mit der aktiven Farbe (true = weiss) und
					//den erlaubten Zuegen der Fiugr an die Zugberechnung
					switch (board[i]) {
					case pawn_w :	berechneZuegeEinerFigur(board, i, true, pawn_moves);
						break;
					case rook_w :	berechneZuegeEinerFigur(board, i, true, rook_moves);
						break;
					case knight_w : berechneZuegeEinerFigur(board, i, true, knight_moves);
						break;
					case bishop_w :	berechneZuegeEinerFigur(board, i, true, bishop_moves);
						break;
					case king_w :	berechneZuegeEinerFigur(board, i, true, king_moves);
						break;
					case queen_w :	berechneZuegeEinerFigur(board, i, true, queen_moves);
						break;
					}
				} else {	//Wenn der Inhalt an diesem Index negativ ist, also Schwarz am Zug ist
					//Uebergib das board und den aktuellen Index mit der aktiven Farbe (false = schwarz) und
					//den erlaubten Zuegen der Figur an die Zugberechnung
					switch (board[i]) {
					case pawn_b :	berechneZuegeEinerFigur(board, i, false, pawn_moves);
						break;
					case rook_b : 	berechneZuegeEinerFigur(board, i, false, rook_moves);
						break;
					case knight_b : berechneZuegeEinerFigur(board, i, false, knight_moves);
						break;
					case bishop_b : berechneZuegeEinerFigur(board, i, false, bishop_moves);
						break;
					case king_b : 	berechneZuegeEinerFigur(board, i, false, king_moves);
						break;
					case queen_b : 	berechneZuegeEinerFigur(board, i, false, queen_moves);
						break;
					}
				}
			}
		}
	}
	
	/*
	 * Diese Methode berechnet die moeglichen Zuege einer Figur und schreibt die daraus resultierendne
	 * FEN-Strings in die Liste der moeglichen Zuege
	 * 
	 * @param board		Die aktuelle Stellung als int-Array
	 * @param startfeld	Das Feld, auf dem zu ziehende Fgur steht
	 * @param weiss		wenn true, ist weiﬂ am Zug, ansonsten schwarz
	 * @param zuege		Die erlaubten Zuege einer Figur (in Schrittweiten im int-Array)
	 */
	private void berechneZuegeEinerFigur(int[] board, int startfeld, boolean weiss, int[] zuege) {
		//Initialisieren des Startfeldes
		int zielfeld = startfeld;
			//Fuer alle angegebenen Schrittweiten = Zugmoeglichkeiten einer Figur
			for (int i : zuege) {
				if (weiss) {	//ist weiss am Zug, muss im Array hochgezaehlt werden,
					zielfeld = startfeld + i;
				} else {		// bei schwarz runtergezaehlt
					zielfeld = startfeld - i;
				}
			//Initialisierung der Zielstellung
			int[] neueStellung = board.clone();
			
			if (neueStellung[zielfeld] == 0) { //Wenn das Zielfeld
				neueStellung[zielfeld] = neueStellung[startfeld];
				neueStellung[startfeld] = leeresFeld;
			}
			}
	}
	
	/*
	 * Diese Methode prueft, welche Rochaden Weiss noch durchfuehren darf und berechnet diese dann
	 * 
	 * @param	rochadeMoeglichkeiten		Die Rochaden, die Weiss noch durchfuehren koennte
	 *   
	 */
	private void berechneWhiteRochade(String rochadeMoeglichkeiten) {
		for (int i=0; i < rochadeMoeglichkeiten.length(); i++) {
			switch (rochadeMoeglichkeiten.charAt(i)) {
			case 'K' :	rochiereKurzW(splittedFEN);
			case 'Q' :	rochiereLangW(splittedFEN);
				break;
			}
		}
	}
	/*
	 * Diese Methode fuehrt eine kurze Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgef¸hrt werden soll
	 */
	private void rochiereKurzW(String[] splittedFEN) {
		if ( //ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
				//&& //uberpruefen, ob Zwischenraum frei ist
				splittedFEN[7].charAt( (splittedFEN[7].length()) - 1 ) == '2'
				//&& //ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
				
				//&& //ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
				
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
	/*
	 * Diese Methode fuehrt eine lange Rochade von Weiss durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgef¸hrt werden soll
	 */
	private void rochiereLangW(String[] splittedFEN) {
		if ( //ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
			//&& //uberpruefen, ob Zwischenraum frei ist
			splittedFEN[7].charAt(1) == '3' 	
			//&& //ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
			
			//&& //ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
			
			) {
				String[] newSplittedFEN = splittedFEN.clone();
				newSplittedFEN[7].replace("R3K", "2KR1");
				//R3K2R -> 2KR12R -> 2KR3R
				if ( newSplittedFEN[7].charAt(4) == '1' ) {
					newSplittedFEN[7].replace("11", "2");
				} else if (newSplittedFEN[7].charAt(4) == '2') {
					newSplittedFEN[7].replace("12", "3");
				} else if (newSplittedFEN[7].charAt(4) == '3') {
					newSplittedFEN[7].replace("13", "4");
				}//Zug ist erledigt
				//Rochademoeglichkeit entfernen
				newSplittedFEN[9].replace("Q", "");
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

	/*
	 * Diese Methode prueft, welche Rochaden Schwarz noch durchfuehren darf und berechnet diese dann
	 * 
	 * @param	rochadeMoeglichkeiten		Die Rochaden, die Schwarz noch durchfuehren koennte
	 */
	private void berechneBlackRochade(String rochadeMoeglichkeiten) {
		for (int i=0; i < rochadeMoeglichkeiten.length(); i++) {
			switch (rochadeMoeglichkeiten.charAt(i)) {
			case 'k' : rochiereKurzB(splittedFEN);
			case 'q' : rochiereLangB(splittedFEN);
				break;
			}
		}
	}
	/*
	 * Diese Methode fuehrt eine kurze Rochade von Schwarz durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgef¸hrt werden soll
	 */
	private void rochiereKurzB(String[] splittedFEN) {
		if ( //ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
				//&& //uberpruefen, ob Zwischenraum frei ist
				splittedFEN[0].charAt( (splittedFEN[0].length()) - 1 ) == '2'
				//&& //ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
				
				//&& //ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
				
				) {
				String[] newSplittedFEN = splittedFEN.clone();
				newSplittedFEN[0].replace("k2r", "1rk1");
				newSplittedFEN[0].replace("11", "2");
				newSplittedFEN[0].replace("21", "3");
				newSplittedFEN[0].replace("31", "4");
				newSplittedFEN[0].replace("41", "5");
				//Zug ist erledigt
				//Rochademoeglichkeit entfernen
				newSplittedFEN[9].replace("k", "");
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
	/*
	 * Diese Methode fuehrt eine lange Rochade von Schwarz durch und schreibt diesen Zug in die Liste der moeglichen Zuege
	 * 
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgef¸hrt werden soll
	 */
	private void rochiereLangB(String[] splittedFEN) {
		if ( //ueberpruefen, ob das Startfeld des Koenigs nicht bedroht wird ("im Schach steht")
				
				//&& //uberpruefen, ob Zwischenraum frei ist
				splittedFEN[0].charAt(1) == '3'
				//&& //ueberpruefen, ob das Zielfeld des Turms nicht bedroht wird
				
				//&& //ueberpruefen, ob das Zielfeld des Koenigs nicht bedroht wird
				
				) {
					String[] newSplittedFEN = splittedFEN.clone();
					newSplittedFEN[0].replace("r3k", "2kr1");
					//r3K2r -> 2kr12r -> 2kr3r
					if ( newSplittedFEN[0].charAt(4) == '1' ) {
						newSplittedFEN[0].replace("11", "2");
					} else if (newSplittedFEN[0].charAt(4) == '2') {
						newSplittedFEN[0].replace("12", "3");
					} else if (newSplittedFEN[0].charAt(4) == '3') {
						newSplittedFEN[0].replace("13", "4");
					}//Zug ist erledigt
					//Rochademoeglichkeit entfernen
					newSplittedFEN[9].replace("q", "");
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
	
	/*
	 * Diese Hilfsmethode setzt einene in ein String-Arrys gesplittete FEN wieder zu einer FEN zusammen
	 * 
	 * @param	entknoddelterFEN	String-Array mit allen Teilen der FEN, die wieder in einen FEN-String umgewandelt werden soll
	 * @return	Ein neu zusammengesetzte FEN-String
	 */
	private String setFEN(String[] entknoddelterFEN) {
		String newFEN =	splittedFEN[0] + "/" + splittedFEN[1] + "/" + splittedFEN[2] +"/" +  splittedFEN[3] + "/" + 
						splittedFEN[4] + "/" + splittedFEN[5] + "/" + splittedFEN[6] + "/" + splittedFEN[7] + " " + 
						splittedFEN[8] + " " + splittedFEN[9] + " " + splittedFEN[10] + " " + splittedFEN[11] + " " +
						splittedFEN[12];
		return newFEN;
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

	
}//end class