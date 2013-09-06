package chessengine.figurbewertung;

import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.SchachPosition;

public class Rook extends OberFigur{

	//SchachPosition zeiger;
	private LinienLaeufer linienlaeufer;
	
	public Rook(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
	
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		bewegungsMuster.push(  new SchachPosition(0 , 1) ) ; //nach oben
		bewegungsMuster.push(  new SchachPosition(0 , -1) ) ; // nach unten
		bewegungsMuster.push(  new SchachPosition(1 , 0) ) ; //nach rechts
		bewegungsMuster.push(  new SchachPosition(-1 , 0) ) ; //nach links

		return linienlaeufer.ermittleZuege( position,  istWeis,  bewegungsMuster,  8); //uebergebe das an ermittlezuege wo draus einen ZukkZukk baut
	}


}
