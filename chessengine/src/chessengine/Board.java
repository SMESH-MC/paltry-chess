<<<<<<< HEAD

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
	
	public int[] FenDecode(String s) {
		int i = 0; //pos im Feld
		int[] ausgabe = new int[128];
		Character fen;
		
		if (s.equals(boardStart) ) {
			InitBoard();
		}
		
		while (s != " ") {
			int pos = 0; //pos in FenString
			fen = s.charAt(pos);
			
			switch (fen) {
			case 'p':
				ausgabe[i] = 1;
				break;
			case 'P':
				ausgabe[i] = 11;
				break;
			case 'r':
				ausgabe[i] = 2;
				break;
			case 'R':
				ausgabe[i] = 12;
				break;
			case 'n':
				ausgabe[i] = 3;
				break;
			case 'N':
				ausgabe[i] = 13;
				break;
			case 'b':
				ausgabe[i] = 4;
				break;
			case 'B':
				ausgabe[i] = 14;
				break;
			case 'q':
				ausgabe[i] = 5;
				break;
			case 'Q':
				ausgabe[i] = 15;
				break;
			case 'k':
				ausgabe[i] = 6;
				break;
			case 'K':
				ausgabe[i] = 16;
				break;
			case ('1' | '2' | '3' | '4' | '5' | '6' | '7' | '8'):
				int leerFelder = Integer.parseInt( fen.toString() );
				for (int j = 0; j < leerFelder; j++) {
					ausgabe[i] = 0;
					i++;
				}
				break;
			case '/':
				i = i + 8;
				break;
			}
			i++;
			pos++;
		} //endWhile

		return ausgabe;
		
	}
	
	public String FenEncode() {
		//decodiert FEN Strings
		
		return OutgoingFen;
		
	}
	
	public void IntToFen() {
		
	}
}
=======

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
>>>>>>> branch 'master' of ssh://d-erb@git.code.sf.net/p/piprojektss2013/code
