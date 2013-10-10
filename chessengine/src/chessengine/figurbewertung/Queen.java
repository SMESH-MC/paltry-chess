/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
package chessengine.figurbewertung;

import java.util.LinkedList;

import chessengine.tools.SchachPosition;

public class Queen extends OberFigur{

	private Rook rook;
	private Bishop  bishop;
	
	public Queen(int bewertung ,Bishop bishop, Rook rook) {
		super(bewertung);
		this.rook = rook;
		this.bishop = bishop;
		
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		LinkedList<String> moeglichkeiten;
		moeglichkeiten = bishop.ermittleZuege(position, istWeis);
		
		moeglichkeiten.addAll( rook.ermittleZuege(position, istWeis));
				
		return 	moeglichkeiten;
	}

}
