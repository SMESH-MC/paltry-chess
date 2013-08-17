
package chessengine;

/**
 * @author Christian Koenig & Dominik Erb
 *
 */
public class Board implements BoardInterface  {
	
	//Variablendeklaration
	
	public int[] boardArray;
	
	//true = weiss  ;  false = schwarz
	public boolean color;
	
	
	//Ende Variablendeklaration
	
	public Board() {
		boardArray = new int[128];
		color = true;
		
	}
	

}
