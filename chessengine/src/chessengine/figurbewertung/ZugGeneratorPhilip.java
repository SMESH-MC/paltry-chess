package chessengine.figurbewertung;

import java.util.LinkedList;


public class ZugGeneratorPhilip implements ZugGeneratorPhilipInterface {
	FigurBewertung bewertung ;
	
	public ZugGeneratorPhilip(){
		bewertung = new  FigurBewertung();
	}
	
	/* (non-Javadoc)
	 * @see figurbewertung.ZugGeneratorPhilipInterface#ermittleAlleZuege(java.lang.String)
	 */
	public LinkedList<String> ermittleAlleZuege(String fen){
		return bewertung.ermittleAlleZuege(fen) ;
	}
}
