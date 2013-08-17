
package chessengine;

/**
 * @author Christian Koenig & Dominik Erb
 *
 */
public class Board implements BoardInterface  {
	
	//Variablendeklaration
	private static final String boardStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	
	public int[] boardArray;
	
	//true = weiss  ;  false = schwarz
	public boolean color;
	
	public String boardFen;
	public String IncomingFen;
	public String OutgoingFen;
	
	
	
	//Ende Variablendeklaration
	
	public Board() {
		boardArray = new int[128];
		color = true;
		
		
	}
	
	public void InitBoard() {
		IncomingFen = boardStart;
		FenDecode(IncomingFen);
		
		
	}
	
	public void ResetBoard() {
		
	}

	public void BoardOutputFen(String boardFen) {
		
	}
	
	public int[] getBoard() {
		return boardArray;
	}
	
	public String FenDecode(String s) {
		
		/*
		 * if incomingFen = startBoard then initBoard; (mit equals arbeiten)
		 */
		
		return IncomingFen;
		
	}
	
	public String FenEncode() {
		//decodiert FEN Strings
		
		return OutgoingFen;
		
	}
	
	public void IntToFen() {
		
	}
}
