package chessengine.figurbewertung;

/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 * unschoener coder
 */
import java.util.LinkedList;

import chessengine.tools.*;

public class Pawn extends OberFigur {

	private Brett schachBrett;
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
		boolean promotion = false;//gibt an ob die figur beim zug befoerdert wird(also ein ein zeile vorm ende)
		
		if (istWeis) { // Fuer weise Baueren
			if(y==6){
				promotion = true ; 
			}
			if (istAufStartPosition(position, istWeis)
					&& schachBrett.getIsEmpty(x, 3) && schachBrett.getIsEmpty(x,2) ) { // wenn auf Start Position
													// und 2 vor ist Frei
				moeglichkeiten.push( linienlaeufer.generiereEnPassantFenZiehe( x, 3, position, new SchachPosition( x, 2) ) ) ;
			}

			if (y < 7) { // ist eins vorm Bauer ueberhaupt ein Feld
				zielY = y+1;
				if (schachBrett.getIsEmpty(x, zielY) ) {// eins vor ist frei
					moeglichkeiten.addAll( linienlaeufer.generiereFenPromotion(x, zielY , position, null, promotion));
				}
				if(x < 7){//rechts ein feld
				
					zielX = x+1;
					if(schachBrett.getIsEmpty(zielX,zielY) == false ){ // vorne rechts eine Figur
						if (schachBrett.getInhalt(zielX,zielY).istWeis() != istWeis) { // vorne rechts ein gegner _> schlage
							moeglichkeiten.addAll( linienlaeufer.generiereFenPromotion(zielX, zielY, position, null , promotion) );
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
					if(schachBrett.getIsEmpty(zielX,zielY) == false){ // vorne rechts eine Figur
						if (schachBrett.getInhalt(zielX, zielY).istWeis() != istWeis) {// vorne Links ein gegner _> schlage
							moeglichkeiten.addAll( linienlaeufer.generiereFenPromotion(zielX, zielY, position , null, promotion));
						}
					}
					if( zielX == enPassant.getX() && zielY == enPassant.getY() && enPassant.getY()== 6)   { // vorne rechts schlag per enPassant
						moeglichkeiten.push( linienlaeufer.generiereEnPassantFenSchlage(zielX, zielY, position, new SchachPosition(zielX,y) ) );
					}
				}
			}// ist eins vorm Bauer ueberhaupt ein Feld

		} else {// sonst schwarz
			if(y==1){
				promotion = true ; 
			}
			if (istAufStartPosition(position, istWeis)//2 felder regel
					&& schachBrett.getIsEmpty(x,4) && schachBrett.getIsEmpty(x,5)) { // wenn auf Start Position
													// und 2 vor ist Frei
				moeglichkeiten.push( linienlaeufer.generiereEnPassantFenZiehe(x, 4, position ,new SchachPosition( x, 5)));
			}

			if (y > 0) { // ist eins vorm Bauer ueberhaupt ein Feld
				zielY = y-1;
				if (schachBrett.getIsEmpty(x,zielY)) {// eins vor ist frei
					moeglichkeiten.addAll( linienlaeufer.generiereFenPromotion(x, zielY, position, null, promotion));	
				}
				if(x < 7){//rechts ein feld
					zielX = x+1;
					if(schachBrett.getIsEmpty(zielX,zielY) == false){ // vorne rechts eine Figur
						if (schachBrett.getInhalt(zielX, zielY).istWeis() != istWeis) { // vorne rechts ein gegner _> schlage
							moeglichkeiten.addAll( linienlaeufer.generiereFenPromotion(zielX, zielY,  position , null, promotion));
						}
					}
					if( zielX == enPassant.getX() && zielY == enPassant.getY() && enPassant.getY()== 2 )   { // vorne rechts schlag per enPassant
						moeglichkeiten.push( linienlaeufer.generiereEnPassantFenSchlage(zielX , zielY,  position, new SchachPosition(zielX,y) ) );
					}
				}
				if(x>0){//links ein feld
					zielX = x-1;
					if(schachBrett.getIsEmpty(zielX,zielY) == false){ // vorne rechts eine Figur
						if (schachBrett.getInhalt(zielX, zielY).istWeis() != istWeis) {// vorne Links ein gegner _> schlage
							moeglichkeiten.addAll( linienlaeufer.generiereFenPromotion(zielX, zielY,  position , null, promotion));
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
	 * Ermittlet ob sich der Bauer auf der Startposition befindet
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
	/**
	 * inizialisiert den Bauer mit allen benoetigt information fuer di zug ermittlung
	 * @param schachBrett2
	 * @param enPassant
	 */
	public void inizialisiere(Brett schachBrett2, SchachPosition enPassant) {
		this.schachBrett = schachBrett2;
		this.enPassant = enPassant;
	}



}// class
