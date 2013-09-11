package chessengine.movegenerator;
/**
 * Diese Klasse macht aus einem Board in 0x88-Darstellung (Arrays aus Zahlen vom Typ Byte) einen FEN-String
 * 
 * @author Schuhmacher, Kaub
 * @version 201309111409
 *
 */

public class FenEncoder {
	//Klassenvariable
	String outgoingFEN; //FEN-String, der die aktuelle Stellung aufnimmt
	
	/**
	 * Konstruktor der Klasse, initialisiert den FEN-String vorerst als leeren String
	 */
	public FenEncoder() {
		outgoingFEN = "";
	}


}
