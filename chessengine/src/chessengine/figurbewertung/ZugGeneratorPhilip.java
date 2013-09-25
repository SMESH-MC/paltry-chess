/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
package figurbewertung;

import java.util.LinkedList;


public class ZugGeneratorPhilip implements ZugGeneratorPhilipInterface {
	FigurBewertungInterface bewertung ;
	
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
