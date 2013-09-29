package chessengine.figurbewertung;

/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 * unschoener coder
 */
import java.util.LinkedList;

import chessengine.tools.Figur;
import chessengine.tools.SchachPosition;

public class Pawn extends OberFigur {

	private Figur[][] schachBrett;
	private SchachPosition enPassant;
	private LinienLaeufer linienlaeufer;
	
	public Pawn(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		LinkedList<String> moeglichkeiten = new LinkedList<String>();
		int x = position.getX();
		int y = position.getY();
		int zielX = 0; 
		int zielY = 0;
		
		if (istWeis) { // Fuer weise Baueren

			if (istAufStartPosition(position, istWeis)
					&& schachBrett[x][3] == null && schachBrett[x][2] == null) { // wenn auf Start Position
													// und 2 vor ist Frei
				moeglichkeiten.push( linienlaeufer.generiereEnPassantFenZiehe( x, 3, position, new SchachPosition( x, 2) ) ) ;
			}

			if (y < 7) { // ist eins vorm Bauer ueberhaupt ein Feld
				zielY = y+1;
				if (schachBrett[x][zielY] == null) {// eins vor ist frei
					moeglichkeiten.push( linienlaeufer.generiereFen(x, zielY , position, null));
				}
				if(x < 7){//rechts ein feld
				
					zielX = x+1;
					if(schachBrett[zielX][zielY] !=  null){ // vorne rechts eine Figur
						if (schachBrett[zielX][zielY].getIstWeis() != istWeis) { // vorne rechts ein gegner _> schlage
							moeglichkeiten.push( linienlaeufer.generiereFen(zielX, zielY, position, null ) );
						}
					}
					if( zielX == enPassant.getX() && zielY == enPassant.getY() && enPassant.getY()== 6 ){ // vorne rechts schlag per enPassant#
						moeglichkeiten.push( linienlaeufer.generiereEnPassantFenSchlage(zielX, zielY, position, new SchachPosition(zielX,y) ) );
						//SchachPosition(zielX,y) da bauer sich auf der gleichen eben befinden
						// neuer gehts nicht
					}
				}

				if(x>0){//links ein feld
					zielX = x-1;
					if(schachBrett[zielX][zielY] !=  null){ // vorne rechts eine Figur
						if (schachBrett[zielX][zielY].getIstWeis() != istWeis) {// vorne Links ein gegner _> schlage
							moeglichkeiten.push( linienlaeufer.generiereFen(zielX, zielY, position , null));
						}
					}
					if( zielX == enPassant.getX() && zielY == enPassant.getY() && enPassant.getY()== 6)   { // vorne rechts schlag per enPassant
						moeglichkeiten.push( linienlaeufer.generiereEnPassantFenSchlage(zielX, zielY, position, new SchachPosition(zielX,y) ) );
					}
				}
			}// ist eins vorm Bauer ueberhaupt ein Feld

		} else {
			if (istAufStartPosition(position, istWeis)
					&& schachBrett[x][4] == null && schachBrett[x][5] == null) { // wenn auf Start Position
													// und 2 vor ist Frei

				moeglichkeiten.push( linienlaeufer.generiereEnPassantFenZiehe(x, 4, position ,new SchachPosition( x, 5)));
			}

			if (y > 0) { // ist eins vorm Bauer ueberhaupt ein Feld
				zielY = y-1;
				if (schachBrett[x][zielY] == null) {// eins vor ist frei
					moeglichkeiten.push( linienlaeufer.generiereFen(x, zielY, position, null));
				}
				if(x < 7){//rechts ein feld
					zielX = x+1;
					if(schachBrett[zielX][zielY] !=  null){ // vorne rechts eine Figur
						if (schachBrett[zielX][zielY].getIstWeis() != istWeis) { // vorne rechts ein gegner _> schlage
							moeglichkeiten.push( linienlaeufer.generiereFen(zielX, zielY,  position , null));
						}
					}
					if( zielX == enPassant.getX() && zielY == enPassant.getY() && enPassant.getY()== 2 )   { // vorne rechts schlag per enPassant
						moeglichkeiten.push( linienlaeufer.generiereEnPassantFenSchlage(zielX , zielY,  position, new SchachPosition(zielX,y) ) );
					}
				}
				if(x>0){//links ein feld
					zielX = x-1;
					if(schachBrett[zielX][zielY] !=  null){ // vorne rechts eine Figur
						if (schachBrett[zielX][zielY].getIstWeis() != istWeis) {// vorne Links ein gegner _> schlage
							moeglichkeiten.push( linienlaeufer.generiereFen(zielX, zielY,  position , null));
						}
					}
					if( zielX == enPassant.getX() && zielY == enPassant.getY() && enPassant.getY()== 2 )  { // vorne rechts schlag per enPassant
						moeglichkeiten.push( linienlaeufer.generiereEnPassantFenSchlage(zielX, zielY, position, new SchachPosition(zielX,y) ) );
					}
				}
			}// ist eins vorm Bauer ueberhaupt ein Feld

		}

		return moeglichkeiten;
	}

	/**
	 * Ermittler ob sich der Bauer auf der Startposition befindet
	 * 
	 * @param istWeis
	 *            ob es sich um eine Weise Figur handelt Position die position
	 *            auf die sich die Figur befindet
	 * @return true wenn sich die figur auf einer Startposition befindet
	 */
	private boolean istAufStartPosition(SchachPosition position, boolean istWeis) {
		boolean istAufStartPos = false;
		if (istWeis) {
			if (position.getY() == 1) {
				istAufStartPos = true;
			}

		} else {// else schwarz
			if (position.getY() == 6) {
				istAufStartPos = true;
			}
		}
		return istAufStartPos;
	}

	public void inizialisiere(Figur[][] schachBrett, SchachPosition enPassant) {
		this.schachBrett = schachBrett;
		this.enPassant = enPassant;
	}



}// class
