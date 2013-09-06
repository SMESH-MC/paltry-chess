package chessengine.tools;
/**
 * 
 * @author Philip
 * Stand : 05.08.2013
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
	
	public  void bewege(int x, int y){
		this.x = this.x + x;
		this.y = this.y + y;
	}
	public  void bewege(SchachPosition pos){
		this.x = this.x + pos.getX();
		this.y = this.y + pos.getY();
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
	
	
	
	
	
	public String toString(){
		
		return "("+x+"|"+y+  ")";
	}
}
