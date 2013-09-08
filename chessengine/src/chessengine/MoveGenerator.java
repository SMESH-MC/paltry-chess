package chessengine;

import java.util.LinkedList;


/**
 *	@author Schuhmacher, Kaub
 *	@version 201309082306
 */
public class MoveGenerator implements MoveGeneratorInterface {
	//Klassenvariablen
    private String[] 	splittedFEN = new String[13];	//Initialisierungs-Array, das spaeter den geteilten FEN-String aufnimmt 
    private LinkedList<String> outgoingFEN = new LinkedList<String>();	//Die Liste aller moeglichen Zuege, die zurueckgeschickt wird
    //private int[]		board		= new int[128];		//Initialisierung fuer die aktuelle Stellung in 0x88-Darstellung
    
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
    	//3.: moegliche en-passant-Schlaege berechnen 
    	
    	
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
			case 'x' : intReihe[i] = 136;
				break;
			case '1' : intReihe[i] = 0;
				break;
			case 'P' : intReihe[i] = 1;
				break;
			case 'p' : intReihe[i] = -1;
				break;
			case 'R' : intReihe[i] = 6;
				break;
			case 'r' : intReihe[i] = -6;
				break;
			case 'N' : intReihe[i] = 2;
				break;
			case 'n' : intReihe[i] = -2;
				break;
			case 'B' : intReihe[i] = 5;
				break;
			case 'b' : intReihe[i] = -5;
				break;
			case 'K' : intReihe[i] = 3;
				break;
			case 'k' : intReihe[i] = -3;
				break;
			case 'Q' : intReihe[i] = 7;
				break;
			case 'q' : intReihe[i] = -7;
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
		//TODO
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
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
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
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
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
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
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
	 * @param	splittedFEN		Die aktuelle Stellung, von der aus die Rochade durchgeführt werden soll
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