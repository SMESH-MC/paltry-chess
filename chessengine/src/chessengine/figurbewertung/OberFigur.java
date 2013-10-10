package chessengine.figurbewertung;
/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
import java.util.LinkedList;
import chessengine.tools.*;

public abstract class OberFigur{



	protected static int bewertung;
	//boolean istWeis;
	//Position position;
	
	public OberFigur(int bewertung) {
			super();
			//this.bewertung = bewertung;
	}
		
	public abstract LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) ;


	public static int getBewertung() {
		return bewertung;
	}


	/*public static void setBewertung(int bewertung) {
		this.bewertung = bewertung;
	}*/



	
}
