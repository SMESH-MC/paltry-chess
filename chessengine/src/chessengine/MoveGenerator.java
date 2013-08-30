/**
 * 
 */
package chessengine;

/**
 *	@author Schuhmacher, Kaub
 *	@version 201308301025
 */
public class MoveGenerator {
	private String incomingFEN;
	private String newFEN;
    private String[] splittedFEN;
    
	private void unravelFEN () {
		//get FEN-String and put into variable
        incomingFEN = Manager.getFEN();
        //split FEN-String at blanks
        splittedFEN = incomingFEN.split("[ /]+");
        /* =>
         * splitFEN[0] = piece placement    rank1/rank2/rank3/rank4/rank5/rank6/rank7/rank8
         * splitFEN[1] = activeColor        w|b
         * splitFEN[2] = castlingOption     K[Q][k][q] | Q[k][q] | k[q] | q | -
         * splitFEN[3] = enPassant          - | ( a|b|c|d|e|f|g|h 3|6   )
         * splitFEN[4] = halfmoveClock      0 | positive integer
         * splitFEN[5] = fullmoveNumber     positive integer
         */
        
        //Split piece placement at slashes
        String[] splitPiecePlacement = splitFEN[0].split("/");
        /*
         * splitPlacement[0] = rank1
         * splitPlacement[1] = rank2
         * splitPlacement[2] = rank3
         * splitPlacement[3] = rank4
         * splitPlacement[4] = rank5
         * splitPlacement[5] = rank6
         * splitPlacement[6] = rank7
         * splitPlacement[7] = rank8
         * each rank consists of either a piece or a number of sequential blank squares
         */
    }
	
	//getter + setter
	private void setFEN(String newToSet) { newFEN = newToSet; }
	public String getFEN() { return newFEN; }

}//end class
