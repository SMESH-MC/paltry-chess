package chessengine.movegenerator;
/**
 * Diese Klasse macht aus einem Board in 0x88-Darstellung (Arrays aus Zahlen vom Typ Byte) einen FEN-String
 * 
 * @author Schuhmacher, Kaub
 *
 */
public class FenEncode
implements Definitions {
	//Klassenvariable
	String outgoingFEN; //FEN-String, der die aktuelle Stellung aufnimmt
	
	/**
	 * Konstruktor der Klasse, initialisiert den FEN-String vorerst als leeren String
	 */
	public FenEncode() {
		outgoingFEN = "";
	}
	
	/**
	 *	Dieser Setter aendert die Klassenvariable (FEN-String) in die korrekte FEN 
	 *
	 * @param board	Das board der aktuellen Stellung in 0x88-Darstellung
	 */
	public void setBoard(byte[] board) {
		//Zaehle die gueltigen Felder des Boards in zwei for-Schleifen in der Reihenfolge, in der sie in den FEN-String geschrieben werden
		for (int i=112; i>=0; i-=16) {
			for (int j=0; j<=7; j++) {
				if (board[i+j] == leeresFeld) { //wenn das Feld an der Stelle board[i+j] ein leeres Feld ist
					outgoingFEN += "1"; //fuege der FEN ein leeres Feld hinzu
				} else { //wenn auf dem Feld an der Stelle i+j eine Figur steht
					outgoingFEN += figurNachFEN(board[i+j]); //wandle die Figur mit Hilfsmethode in FEN um
				}
			}
			outgoingFEN += "/";	//Die Reihe wurde komplett in den FEN-String geschrieben, setze daher ein Slash in den FEN-String
		}
		//letztes Slash entfernen
		outgoingFEN = outgoingFEN.substring(0, outgoingFEN.length()-1);

		//Hilfsmethode, die aus aufeinanderfolgenden Leerfeldern eine einzelne Ziffer aufeinanderfolgender Leerfelder macht
		outgoingFEN = summiereLeerfelder(outgoingFEN); 
		
		//restliche Infos der FEN hinzufuegen
		if (board[120] == 1) {outgoingFEN += " w ";} else {outgoingFEN += " b ";} //Farbe am Zug
		int keineRochade = 0; //Zaehler, der mit dem Wert 4 angibt, dass keine Rochade mehr moeglich ist
		if (board[121] == 1) {outgoingFEN += "K";} else {keineRochade++;}//Rochadenmoeglichkeit Weiss kurz
		if (board[122] == 1) {outgoingFEN += "Q";} else {keineRochade++;}//Rochadenmoeglichkeit Weiss lang
		if (board[123] == 1) {outgoingFEN += "k";} else {keineRochade++;}//Rochadenmoeglichkeit Schwarz kurz
		if (board[124] == 1) {outgoingFEN += "q";} else {keineRochade++;}//Rochadenmoeglichkeit Schwarz lang
		if (keineRochade == 4) {outgoingFEN += "-";} //wenn keine Rochade mehr moeglich ist
		outgoingFEN += " " + enPassant(board[125]);		//En-Passant-Feld
		outgoingFEN += " " + board[126];	//Halbzuege
		outgoingFEN += " " + board[127];	//Zugnummer
	}
	
	/**
	 * getter fuer die fertige FEN
	 * @return	Der fertig zusammengesetzte FEN-String
	 */
	public String getFEN() {
		return outgoingFEN;
	}
	
	/**
	 * Hilfsmethode zu setBoard, die eine Figur als byte in eine Figur als FEN-Figur umwandelt
	 * 
	 * @param figur	Die in FEN umzuschreibende Figur als byte-Zahl
	 * @return	Die in FEN umgewandelte eingegebene Figur
	 */
	private String figurNachFEN(byte figur) {
		String outgoingPiece = ""; 
		switch (figur) {
			case pawn_w : 	outgoingPiece = "P";	break;
			case pawn_b :	outgoingPiece = "p";	break;
			case rook_w :	outgoingPiece = "R";	break;
			case rook_b :	outgoingPiece = "r";	break;
			case knight_w : outgoingPiece = "N";	break;
			case knight_b : outgoingPiece = "n";	break;
			case bishop_w : outgoingPiece = "B";	break;
			case bishop_b : outgoingPiece = "b";	break;
			case king_w : 	outgoingPiece = "K";	break;
			case king_b : 	outgoingPiece = "k";	break;
			case queen_w : 	outgoingPiece = "Q";	break;
			case queen_b : 	outgoingPiece = "q";	break;
		}
		return outgoingPiece;
	}
		
	/**
	 * Hilfsmethode, die im FEN-String eine Reihe aufeinanderfolgender einzelner Felder (z.B: 11111) durch eine feste
	 * Anzahl aufeinanderfolgender Leerfelder ersetzt (11111 -> 5)
	 * 
	 * @param stellung	Der Stellungsteil der FEN, jedoch mit einzelnen aufeinanderfolgenden Leerfelder (1111...)
	 * @return	FEN-String nach Vorschrift (Anzahl aufeinanderfolgender Leerfelder als eine Ziffer) 
	 */
	private String summiereLeerfelder(String stellung) {
		stellung = stellung.replace("11111111","8");
		stellung = stellung.replace("1111111","7");
		stellung = stellung.replace("111111","6");
		stellung = stellung.replace("11111","5");
		stellung = stellung.replace("1111","4");
		stellung = stellung.replace("111","3");
		stellung = stellung.replace("11","2");
		return stellung;
	}
	
	/**
	 * Hilfsmethode, die aus dem enPassantFeld in 0x88-Darstellung die EnPassant-Info in FEN ausgibt
	 * 
	 * @param enPassantFeld	Das Feld in 0x88-Darstellung (z.B. 86), das im letzten Zug von einem Bauer uebersprungen wurde
	 * @return	En Passant-Feld in FEN
	 */
	private String enPassant(byte enPassant0x88) {
		String enPassantFEN = "";
		switch (enPassant0x88) {
		case -1 : enPassantFEN = "-";		break;
		case 32 : enPassantFEN = "a3";		break;
		case 33 : enPassantFEN = "b3";		break;
		case 34 : enPassantFEN = "c3";		break;
		case 35 : enPassantFEN = "d3";		break;
		case 36 : enPassantFEN = "e3";		break;
		case 37 : enPassantFEN = "f3";		break;
		case 38 : enPassantFEN = "g3";		break;
		case 39 : enPassantFEN = "h3";		break;
		case 80 : enPassantFEN = "a6";		break;
		case 81 : enPassantFEN = "b6";		break;
		case 82 : enPassantFEN = "c6";		break;
		case 83 : enPassantFEN = "d6";		break;
		case 84 : enPassantFEN = "e6";		break;
		case 85 : enPassantFEN = "f6";		break;
		case 86 : enPassantFEN = "g6";		break;
		case 87 : enPassantFEN = "h6";		break;
		}
		return enPassantFEN;
	}
}
