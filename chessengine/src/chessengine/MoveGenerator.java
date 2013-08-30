package chessengine;

/**
 *	@author Schuhmacher, Kaub
 *	@version 201308301025
 */
public class MoveGenerator {
	private String newFEN = "";
    private String[] splittedFEN = new String[13];
    
	private void unravelFEN() {
		//FEN-String vom Manager holen und an Leerzeichen und Slashes trennen
        splittedFEN = Manager.getFEN().split("[ /]");
        /* =>
         * //Stellungen:
         * splittedFEN[0] = Reihe 1
         * splittedFEN[1] = Reihe 2
         * splittedFEN[2] = Reihe 3
         * splittedFEN[3] = Reihe 4
         * splittedFEN[4] = Reihe 5
         * splittedFEN[5] = Reihe 6
         * splittedFEN[6] = Reihe 7
         * splittedFEN[7] = Reihe 8
         * jede Reihe besteht entweder aus Figuren oder aus einer Anzahl aufeinanderfolgender Leerfelder
         * z.B. 8 (leere Reihe), rnbqkbnr (Anfangsstellung weiss), Nb1P3n (im Spiel)
         * //sonstiges:
         * splittedFEN[8] = activeColor		w|b
         * splittedFEN[9] = castlingOption		K[Q][k][q] | Q[k][q] | k[q] | q | -
         * splittedFEN[10] = enPassant			- | ( a|b|c|d|e|f|g|h 3|6   )  //ohne Leerzeichen 
         * splittedFEN[11] = halfmoveClock		0 | positive Ganzzahl
         * splittedFEN[12] = fullmoveNumber	positive Ganzzahl
         */
        
        
        //testweiser Aufruf zum zusammenmatschen des FEN
        setFEN(splittedFEN);
	}
	
	private void setFEN(String[] unravelledFEN) {
		newFEN =	splittedFEN[0] + "/" + splittedFEN[1] + "/" + splittedFEN[2] +"/" +  splittedFEN[3] + "/" + 
					splittedFEN[4] + "/" + splittedFEN[5] + "/" + splittedFEN[6] + "/" + splittedFEN[7] + " " + 
					splittedFEN[8] + " " + splittedFEN[9] + " " + splittedFEN[10] + " " + splittedFEN[11] + " " +
					splittedFEN[12] + " ";
	}

	public String getFEN() { return newFEN; }

}//end class
