package chessengine;

public interface BoardInterface {

	public abstract void InitBoard();

	public abstract void InitBoard(String fen);

	public abstract boolean ResetBoard();

	public abstract void BoardOutputFen(String boardFen);

	public abstract BoardInterface getBoard();

	public abstract int[] FenDecode(String s);

	public abstract String FenEncode();

	public abstract Character IntToFen(int toConvert);

	/**
	 * 
	 * @return int wert die die Stellung des Bords hat
	 * nach schachbewertung 
	 * negativer wert ist ein vorteil fuer Schwarz
	 * positiver wert ist ein vorteil fuer Weiss
	 */
	public abstract int getBoardValue();

	public abstract int getRochadeGross();

	public abstract int getRochadeKlein();

	public abstract boolean isEnPassent();

	public abstract int getZugnummer();

	public abstract int getHalbzuege();

}