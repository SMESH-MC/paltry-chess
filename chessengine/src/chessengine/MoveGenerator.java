package chessengine;

/**
 *	@author Schuhmacher, Kaub
 *	@version 201308301556
 */
public class MoveGenerator implements MoveGeneratorInterface {
	private String newFEN = "";
    private String[] splittedFEN = new String[13];
    
	private void unravelFEN() {
		//FEN-String vom Manager holen und an Leerzeichen und Slashes trennen
        /* =>
         * //Stellungen:
         * splittedFEN[0] = Reihe 8
         * splittedFEN[1] = Reihe 7
         * splittedFEN[2] = Reihe 6
         * splittedFEN[3] = Reihe 5
         * splittedFEN[4] = Reihe 4
         * splittedFEN[5] = Reihe 3
         * splittedFEN[6] = Reihe 2
         * splittedFEN[7] = Reihe 1
         * jede Reihe besteht von A bis H entweder aus Figuren oder aus einer Anzahl aufeinanderfolgender Leerfelder
         * z.B. 8 (leere Reihe), rnbqkbnr (Anfangsstellung schwarz), nB1p3N (im Spiel)
         * //sonstiges:
         * splittedFEN[8] = activeColor		w|b
         * splittedFEN[9] = castlingRight	- | [K][Q][k][q]
         * splittedFEN[10] = enPassant		- | ( a|b|c|d|e|f|g|h 3|6   )  //ohne Leerzeichen 
         * splittedFEN[11] = halfmoveClock	0 | positive Ganzzahl
         * splittedFEN[12] = fullmoveNumber	positive Ganzzahl
         */
        
        if (splittedFEN[8].equals("w")) { whiteTurn(); } else { blackTurn(); }
	}
	
	private void whiteTurn() {
	}
	
	private void blackTurn() {
		increaseFullmoveNumber();
	}

	
	private String increaseHalfmoveClock() {
		int newHalfmoveInt = Integer.parseInt(splittedFEN[11]) + 1;
		return String.valueOf(newHalfmoveInt);
		}
	
	private String increaseFullmoveNumber() {
		int newFullmoveInt = Integer.parseInt(splittedFEN[12]) + 1;
		return String.valueOf(newFullmoveInt);
		}
	
	private void setFEN(String[] unravelledFEN) {
		newFEN =	splittedFEN[0] + "/" + splittedFEN[1] + "/" + splittedFEN[2] +"/" +  splittedFEN[3] + "/" + 
					splittedFEN[4] + "/" + splittedFEN[5] + "/" + splittedFEN[6] + "/" + splittedFEN[7] + " " + 
					splittedFEN[8] + " " + splittedFEN[9] + " " + splittedFEN[10] + " " + splittedFEN[11] + " " +
					splittedFEN[12] + " ";
	}

	public String getFENList() {
        //testweiser Aufruf zum zusammenmatschen des FEN
        setFEN(splittedFEN);
		return newFEN; }
	
}//end class
