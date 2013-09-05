package figurbewertung;

import java.util.LinkedList;

import tools.SchachPosition;

public class Queen extends OberFigur{

	private Rook rook;
	private Bishop  bishop;
	
	public Queen(int bewertung ,Bishop bishop, Rook rook) {
		super(bewertung);
		this.rook = rook;
		this.bishop = bishop;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		LinkedList<String> moeglichkeiten;
		moeglichkeiten = bishop.ermittleZuege(position, istWeis);
		
		moeglichkeiten.addAll( rook.ermittleZuege(position, istWeis));
				
		return 	moeglichkeiten;
	}
}
