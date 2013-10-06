package chessengine;

public interface BoardInterface {

        /**
         * Methode initialisiert das Board mit der Standardbelegung.
         */
	public abstract void InitBoard();

        /**
         * Methode inizialisiert das Board mit uebergebener Stellung.
         * @param fen Stellung als FEN-String
         */
	public abstract void InitBoard(String fen);

        /**
         * Methode setzt das Board auf Startstellung zurueck.
         * @return 
         */
	public abstract boolean ResetBoard();

        /**
         * TODO Kommentar ergaenzen
         * @param boardFen 
         */
	public abstract void BoardOutputFen(String boardFen);

        /**
         * Getter-Methode fuer das aktuelle Board.
         * @return altuelles Board
         */
	public abstract BoardInterface getBoard();

        /**
         * Methode zum decodieren des FEN-String
         * @param s FEN-String
         * @return int-Array zum weiterbearbeiten
         */
	public abstract int[] FenDecode(String s);

        /**
         * Methode zum encodieren des FEN-Strings
         * @return FEN-String
         */
	public abstract String FenEncode();

        /**
         * Methode zum konvertieren eines int zu einem FEN-String
         * @param toConvert int
         * @return FEN-String
         */
	public abstract Character IntToFen(int toConvert);

	/**
	 * Methode liefert Boardvalue zurueck
	 * @return int wert die die Stellung des Bords hat
	 * nach schachbewertung 
	 * negativer wert ist ein vorteil fuer Schwarz
	 * positiver wert ist ein vorteil fuer Weiss
	 */
	public abstract int getBoardValue();

        /**
         * Methode gibt zurueck ob eine grosse Rochade moeglich ist.
         * @return int 
         */
	public abstract int getRochadeGross();

        /**
         * Methode gibt zurueck ob eine kleine Rochade moeglich ist.
         * @return int 
         */
	public abstract int getRochadeKlein();

        /**
         * Methode gibt zurueck ob ein Zug ein EnPassent-Zug ist
         * @return boolean
         */
	public abstract boolean isEnPassent();

        /**
         * Getter-Methode fuer die aktuelle Zugnummer.
         * @return int Zugnummer
         */
	public abstract int getZugnummer();

        /**
         * Getter-Methode fuer den aktuellen Halbzug
         * @return int Halbzug
         */
	public abstract int getHalbzuege();

}