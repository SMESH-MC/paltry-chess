package chessengine.movegenerator;

import java.util.LinkedList;

/**
 * 
 * @author SMESH
 *
 */
public class Rochade {
//	////Die Liste aller moeglichen Rochaden-Zuege, die zurueckgegeben wird
//	private LinkedList<String> moeglicheRochadenZuege = new LinkedList<String>();
//	
//	/**
//	 * Konstruktor
//	 */
//	public Rochade (String[] splittedFEN, boolean weiss) {
//		if (weiss) {
//			berechneWhiteRochade(splittedFEN[9], splittedFEN);
//		} else {
//			berechneBlackRochade(splittedFEN[9], splittedFEN);
//		}
//		 
//	/**
//	 * Diese Methode prueft, welche Rochaden Weiss noch durchfuehren darf und berechnet diese dann
//	 * 
//	 * @param	rochadeMoeglichkeiten		Die Rochaden, die Weiss noch durchfuehren koennte
//	 *   
//	 */
//	private void berechneWhiteRochade(String rochadeMoeglichkeiten, String[] splittedFEN) {
//		for (int i=0; i < rochadeMoeglichkeiten.length(); i++) {
//			switch (rochadeMoeglichkeiten.charAt(i)) {
//			case 'K' :	rochiereKurzW(splittedFEN);
//			case 'Q' :	rochiereLangW(splittedFEN);
//				break;
//			}
//		}
//	}
	/**
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
	/**
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

//	/**
//	 * Diese Methode prueft, welche Rochaden Schwarz noch durchfuehren darf und berechnet diese dann
//	 * 
//	 * @param	rochadeMoeglichkeiten		Die Rochaden, die Schwarz noch durchfuehren koennte
//	 */
//	private void berechneBlackRochade(String rochadeMoeglichkeiten, String[] splittedFEN) {
//		for (int i=0; i < rochadeMoeglichkeiten.length(); i++) {
//			switch (rochadeMoeglichkeiten.charAt(i)) {
//			case 'k' : rochiereKurzB(splittedFEN);
//			case 'q' : rochiereLangB(splittedFEN);
//				break;
//			}
//		}
//	}
	/**
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
	/**
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
	
	/**
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

	/**
	 * getter
	 */
	public LinkedList<String >getZuege () {
		return moeglicheRochadenZuege;
	}
}
