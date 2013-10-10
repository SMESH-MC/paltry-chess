package chessengine.figurbewertung;
/**  
* @author Philip Hunsicker
* Stand : 25.09.2013
*/
import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.SchachPosition;

public class Knight extends OberFigur{

	private LinienLaeufer linienlaeufer;
	
	public Knight(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		

		
		return linienlaeufer.ermittleZuege( position,  istWeis,   getMuster(),  1); //uebergebe das an ermittlezuege wo draus einen Bus baut
	}



	public Stack<SchachPosition> getMuster() {
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		bewegungsMuster.push(  new SchachPosition(2 , 1) ); //2 rechts oben
		bewegungsMuster.push(  new SchachPosition(2 , -1) ); //   unten
		bewegungsMuster.push(  new SchachPosition(-2 , 1) ); //2 links oben
		bewegungsMuster.push(  new SchachPosition(-2 , -1) ); //unten
		bewegungsMuster.push(  new SchachPosition(1 , 2) ); // //2 Oben rechts
		bewegungsMuster.push(  new SchachPosition(-1 , 2) ); // links
		bewegungsMuster.push(  new SchachPosition(1 , -2) ); //2 unten recht
		bewegungsMuster.push(  new SchachPosition(-1 , -2) ); //links
		return bewegungsMuster;
	}


}
