package figurbewertung;

import java.util.LinkedList;
import java.util.Stack;

import tools.Figur;
import tools.SchachPosition;

public class Knight extends OberFigur{

	private LinienLaeufer linienlaeufer;
	
	public Knight(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		bewegungsMuster.push(  new SchachPosition(2 , 1) ); //2 rechts oben
		bewegungsMuster.push(  new SchachPosition(2 , -1) ); //   unten
		bewegungsMuster.push(  new SchachPosition(-2 , 1) ); //2 links oben
		bewegungsMuster.push(  new SchachPosition(-2 , -1) ); //unten
		bewegungsMuster.push(  new SchachPosition(1 , 2) ); // //2 Oben rechts
		bewegungsMuster.push(  new SchachPosition(-1 , 2) ); // links
		bewegungsMuster.push(  new SchachPosition(1 , -2) ); //2 unten recht
		bewegungsMuster.push(  new SchachPosition(-1 , -2) ); //links
		
		return linienlaeufer.ermittleZuege( position,  istWeis,   bewegungsMuster,  1); //uebergebe das an ermittlezuege wo draus einen Bus baut
	}

	public void inizialisiere(Figur[][] schachBrett) {
		// TODO Auto-generated method stub
		
	}


}
