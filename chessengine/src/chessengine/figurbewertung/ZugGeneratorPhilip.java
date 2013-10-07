/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
package chessengine.figurbewertung;

import java.util.LinkedList;
import chessengine.movegenerator.MoveGeneratorInterface;

public class ZugGeneratorPhilip implements MoveGeneratorInterface {
	FigurBewertungInterface bewertung ;
	
	private String fen;
	/**
	 * langsam da die Object erstellung einen Zeit in anspruch  nimmt
	 */
	public ZugGeneratorPhilip(){
		bewertung = new  FigurBewertung();
	}
	public ZugGeneratorPhilip(FigurBewertungInterface uebergabeBewertung ){
		bewertung = uebergabeBewertung;
	}
	
	/**
	 * ermittlet all Zuege und gibt diese mit FEN in einere Linked list zurueckt
	 * @param andereFen
	 * @return
	 */
	public LinkedList<String> ermittleAlleZuege(String andereFen){
		return bewertung.ermittleAlleZuege(andereFen) ;
	}
	/**
	 * liefert alle Zuege per FEN aufgrund des vorher mit setFEN gesetzten ausgangstellung
	 */
	public LinkedList<String> getZuege(){
		return bewertung.ermittleAlleZuege(fen);
		
	}
	/**
	 * setzt den Fen fuer die Methode getZuege
	 */
	public void setFEN(String neuerFen){
			fen = neuerFen;
	}

}
