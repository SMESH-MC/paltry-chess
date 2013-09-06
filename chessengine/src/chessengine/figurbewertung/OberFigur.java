package chessengine.figurbewertung;
/*
 * Stand 04.08.2013
 * Author Philip
 * 
 */
import java.util.LinkedList;
import chessengine.tools.*;

public abstract class OberFigur{



	protected int bewertung;
	//boolean istWeis;
	//Position position;
	
	public OberFigur(int bewertung) {
			super();
			this.bewertung = bewertung;
	}
		
	public abstract LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) ;


	public int getBewertung() {
		return bewertung;
	}


	public void setBewertung(int bewertung) {
		this.bewertung = bewertung;
	}



	
}
