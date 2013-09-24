package chessengine;

public interface BoardInterface {

	public abstract void InitBoard();							//initialisiert das Board mit Start-FEN

	public abstract boolean ResetBoard();						//Setzt Board auf Startstring zurueck

	public abstract void BoardOutputFen(String boardFen);

	public abstract Board getBoard(); 							//gibt das aktuelle Board zurück

	public abstract int[] FenDecode(String s);

	public abstract String FenEncode();

	public abstract Character IntToFen(int toConvert);
	
	public abstract int getBoardValue();

}