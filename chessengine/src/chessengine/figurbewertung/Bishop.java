package figurbewertung;
/**  
* @author Philip Hunsicker
* Stand : 25.09.2013
*/
import java.util.LinkedList;
import java.util.Stack;

import tools.SchachPosition;

public class Bishop extends OberFigur{

	private LinienLaeufer linienlaeufer;
	
	public Bishop(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {


		return linienlaeufer.ermittleZuege( position,  istWeis,    getMuster(),  8); //uebergebe das an ermittlezuege wo draus einen Zug baut
	}

	public Stack<SchachPosition> getMuster() {
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		bewegungsMuster.push( new SchachPosition(1 , 1) ) ; //Diagonal oben rechts
		bewegungsMuster.push( new SchachPosition(1 , -1) ) ; // Diagonal unten rechts
		bewegungsMuster.push( new SchachPosition(-1 , 1) ) ; //Diagonal oben links
		bewegungsMuster.push( new SchachPosition(-1 , -1) ) ; //Diagonal unten links
		return bewegungsMuster;
	}
}
