package chessengine.tools;
/**
 * 
 * @author Philip Hunsicker
 * datum 30.09.2013
 */
public abstract class Brett {

	//SchachPosition position = new SchachPosition();
	public  Figur getInhalt(SchachPosition position){
		return getInhalt(position.getX(), position.getY());
	}
	public abstract Figur getInhalt(int x, int y);
	
	public  void setInhalt(SchachPosition position,  Figur inhalt){
		setInhalt(position.getX(), position.getY(),   inhalt);
	}
	public abstract void setInhalt(int x, int y,  Figur inhalt);
	
	public  void setEmpty(SchachPosition position){
		setEmpty(position.getX(), position.getY());
	}
	public abstract void setEmpty(int x, int y);
	
	public  boolean getIsEmpty(SchachPosition position){
		return getIsEmpty(position.getX(), position.getY());
	}
	public abstract boolean getIsEmpty(int x, int y);
	
	public abstract Brett copy();
	
	
	/**
	 * Erstellt einen neuese Brett und bewegt die ensprechende Figur auf die gewuenschte position 
	 * @param x Zielposition
	 * @param y Zielposition
	 * @param position  Start position
	 * @return 
	 */
	public  Brett bewegeFigur(int x, int y, SchachPosition position){
		return bewegeFigur( x,  y,  position.getX() , position.getY());
	}
	public  Brett bewegeFigurOhneKopie(int x, int y, SchachPosition position){
		return bewegeFigurOhneKopie( x,  y,  position.getX() , position.getY());
	}
	
	public Brett bewegeFigur(int x, int y, int StartX, int StartY) {
		Brett neuesBrett = copy();
		neuesBrett = neuesBrett.bewegeFigurOhneKopie( x,  y,  StartX, StartY); 
		return neuesBrett;
	}
	public Brett bewegeFigurOhneKopie(int x, int y, int StartX, int StartY) {
		Figur figur = this.getInhalt(StartX, StartY);
		this.setEmpty(StartX, StartY); // setzt die alte position auf null
		this.setInhalt(x, y, figur); // setzt die figur auf das neue Feld
		return this;
	}
	public abstract String toString();
	
	public void promotionRook(int x, int y){
		getInhalt(x, y).promotionRook();
	}
	public void promotionKnight(int x, int y){
		getInhalt(x, y).promotionKnight();
	}
	public void promotionQueen(int x, int y){
		getInhalt(x, y).promotionQueen();
	}
	public void promotionBishop(int x, int y){
		getInhalt(x, y).promotionBishop();
	}
	
}
