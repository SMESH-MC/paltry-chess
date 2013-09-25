package tools;
/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 * speichert die schachposition von 0 - 7
 */
public class SchachPosition {

	int x;
	int y;
	
	public SchachPosition() {
		super();
		this.x = 0;
		this.y = 0;
	}
	public SchachPosition(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public  boolean bewege(int x, int y){
		int endX = this.x + x;
		int endY = this.y + y;
		if(endX<8 && endX >= 0  && endY<8 && endY >= 0){
			this.x = endX;
			this.y = endY;
			return true;
		}else{
			return false;
		}

	}
	public  boolean bewege(SchachPosition pos){
		return bewege(pos.getX(), pos.getY());
	}
	
	
	/**
	 * 
	 * @return 0-7
	 */
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setXY(SchachPosition pos) {
		this.x = pos.getX();
		this.y = pos.getY();
	}
	/**
	 * 
	 * @return 0-7
	 */
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Wandelt der asci character auf int um 0 == 48
	 * @param y
	 */
	public void setY(char y) {

			this.y = y - 48;
		
	}
/**
 * 
 * @return 0-7
 */
	public char getCharX() {
		int wert = x + 49 +48;// 49 von 0 auf a und 48 von int 0 auf char 0
		return (char)wert;
	}
	/**
	 * Wandelt den BUCHSTABEN in den int um wert 
	 * wandelt NICHT von 0-9 char auf 0 9 int !!!!!!!!
	 * @param x
	 */
	public void setX(char x) {
		int wert = x;
		if(x >= 'a'){ //wenn klein buchstabe
			wert = x - 32;
			
		}
		wert = wert - 17;  // von Buchstabe auf (char)Zahl
		
		wert = wert - 48; //von char  auf int wert
		
		this.x = wert;
	}	
	
	
	//
	public boolean equals(SchachPosition position){
		boolean istGleich = false;
		if(position.getX() == x){
			if(position.getY() == y){
				istGleich = true;
			}
		}
		return istGleich;
	}
	
	public String toString(){
		
		return "("+x+"|"+y+  ")";
	}
}
