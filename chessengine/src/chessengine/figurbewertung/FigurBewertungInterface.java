package chessengine.figurbewertung;
/**  
* @author Philip Hunsicker
* Stand : 25.09.2013
*/
import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.SchachPosition;

public interface FigurBewertungInterface {

	/**
	 *Methoder die Alle Figuren mit einem Spielfeld versorgt auf grunde dessen es das Zuege berechnet
	 */
	public abstract void inizialisiereBrett(String fen);

	/**
	 * @param position die Position von der Figure desen Zuegen ermittlet werden soll (x=0 unten | y = 0  links| Weis ist unten
	 * @return liefert Zuege CHO CHO CHO CHO CHO CHO CHO CHO als Linked List
	 */
	public abstract LinkedList<String> ermittleZuege(SchachPosition position); //ermittle

	/**
	 *  Ermittle alle zuege fuer die farbe die gerade am Zug ist
	 * @param fen der aktuellen ausgangstellung
	 * @return list Alle Zuege die von diese Stellung ausgefuehrt werden koennen
	 */
	public abstract LinkedList<String> ermittleAlleZuege(String fen); //ermittle Alle



	/* (non-Javadoc)
	 * @see figurbewertung.FigurBewewertung#getBewertung(char)
	 */
	public  abstract  int  getBewertung(char typ); // getBewertung

	/**
	 * liefert die Bewegungsmuster  fuer einfache Bewegungen
	 * KEINE SPEZIAL FAELLER
	 * KEIE BAUER
	 * KEINE DAME (da dame = Turm + laeufer)
	 * @param typ figur : qbrnpk
	 * @return Stack mit Bewegungsmuster
	 */
	public abstract Stack<SchachPosition> getMuster(char typ); // getBewertung

	public abstract int getKingBewertung();

	public abstract int getQueenBewertung();

	public abstract int getBishopBewertung();

	public abstract int getKnightBewertung();

	public abstract int getRookBewertung();

	public abstract int getPawnBewertung();

	public  abstract void getQueenBewertung(int bewertung) ;

	public  abstract void setBishopBewertung(int bewertung) ;

	public abstract  void setKnightBewertung(int bewertung);

	public  abstract void setRookBewertung(int bewertung) ;
	
}