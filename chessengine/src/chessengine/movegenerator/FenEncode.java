package chessengine.movegenerator;
/**
 * Diese Klasse macht aus einem Board in 0x88-Darstellung (Arrays aus Zahlen vom Typ Byte) einen FEN-String
 * 
 * @author Schuhmacher, Kaub
 * @version 201309111409
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
		for (int i=0; i<=119 ; i++) { //fuer alle Stellungsfelder des aktuellen 0x88-boards
			if ((i & 136) == 0) { //Wenn das Feld an der Stelle board[i] ein gueltiges Feld des Schachbretts ist
				if (board[i] == leeresFeld) { //wenn das Feld an der Stelle board[i] ein leeres Feld ist
					outgoingFEN += "1"; //fuege der FEN ein leeres Feld hinzu
				} else { //wenn auf dem Feld an der Stelle i eine Figur steht
					outgoingFEN += figurNachFEN(board[i]); //wandle die Figur mit Hilfsmethode in FEN um
				}
			} else { //Wenn das Feld an der Stelle board[i] ein Feld des 0x88-"Geisterbretts" ist
				outgoingFEN += "/";	//Setze ein Slash in den FEN-String
				i += 7; //erhoehe den Zaehler bis zum naechsten gueltigen Feld
			}
		}
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
	 * @param figur Die in FEN zumzuschreibende Figur als byte-Zahl
	 * @return	Die in FEN umgewandelte eingegebene Figur
	 */
	private String figurNachFEN(byte figur) {
		String outgoingPiece = ""; 
		switch (figur) {
			case pawn_w : 	outgoingPiece = "P";
			case pawn_b :	outgoingPiece = "p";
			case rook_w :	outgoingPiece = "R";
			case rook_b :	outgoingPiece = "r"; 
			case knight_w : outgoingPiece = "N";
			case knight_b : outgoingPiece = "n";
			case bishop_w : outgoingPiece = "B";
			case bishop_b : outgoingPiece = "b";
			case king_w : 	outgoingPiece = "K";
			case king_b : 	outgoingPiece = "k";
			case queen_w : 	outgoingPiece = "Q";
			case queen_b : 	outgoingPiece = "q";
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
