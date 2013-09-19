package chessengine.figurbewertung;

import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.SchachPosition;

public interface FigurBewertungInterface {

	/**
	 *Methoder die Alle Figuren mit einem Spielfeld versorgt auf grunde dessen es das Zuege berechnet
	 */
	public abstract void inizialisiereBrett(String fen);

	/**
	 * 
	 * @param position die Position von der Figure desen Zuegen ermittlet werden soll (x=0 unten | y = 0  links| Weis ist unten
	 * @param schachbrett ein Schachbrett das mit dem FenDecoder genereriert worden ist
	 * @param rochade Welche Rochaden möglich sind
	 * @param enPassant ob ein querschlagen des Bauers moeglich ist
	 * @return liefert Zuege CHO CHO CHO CHO CHO CHO CHO CHO als Stack 
	 */
	public abstract LinkedList<String> ermittleZuege(SchachPosition position); //ermittle

	/**
	 *  Ermittle alle zuege fuer die farbe die gerade am Zug ist
	 * @param fen der aktuellen ausgangstellung
	 * @return list Alle Zuege die von diese Stellung ausgefuehrt werden koennen
	 */
	public abstract LinkedList<String> ermittleAlleZuege(String fen); //ermittle Alle

	/**
	 * q b n r p 
	 */
	public abstract void setBewertung(int q, int b, int n, int r, int p);

	public abstract int getBewertung(char typ); // getBewertung

	public abstract Stack<SchachPosition> getMuster (char typ);
	
}