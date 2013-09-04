package chessengine;

public interface BoardInterface {

	public abstract void InitBoard();

	public abstract boolean ResetBoard();

	public abstract void BoardOutputFen(String boardFen);

	public abstract int[] getBoard();

	public abstract int[] FenDecode(String s);

	public abstract String FenEncode();

	public abstract Character IntToFen(int toConvert);

}