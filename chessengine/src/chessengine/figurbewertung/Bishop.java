package chessengine.figurbewertung;

import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.SchachPosition;

public class Bishop extends OberFigur{

	private LinienLaeufer linienlaeufer;
	
	public Bishop(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		bewegungsMuster.push( new SchachPosition(1 , 1) ) ; //Diagonal oben rechts
		bewegungsMuster.push( new SchachPosition(1 , -1) ) ; // Diagonal unten rechts
		bewegungsMuster.push( new SchachPosition(-1 , 1) ) ; //Diagonal oben links
		bewegungsMuster.push( new SchachPosition(-1 , -1) ) ; //Diagonal unten links

		return linienlaeufer.ermittleZuege( position,  istWeis,    bewegungsMuster,  8); //uebergebe das an ermittlezuege wo draus einen Zug baut
	}
}
