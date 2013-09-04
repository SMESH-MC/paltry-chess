
package chessengine;

/**
 * @author Christian Koenig & Dominik Erb
 *
 */
public class Board implements BoardInterface  {
	
	//Variablendeklaration
	private static final String boardStart = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	
	public int[] boardArray;
	public boolean color;			//true = weiss ; false = schwarz
	public String boardFen;
	public String IncomingFen;
	public String OutgoingFen;
	private boolean enPassent;
	private int rochade_gross;
	private int rochade_klein;
	private int zugnummer;
	private int halbzuege;
	
	//Ende Variablendeklaration
	
	public Board() {
		boardArray = new int[128];	//Boardarray
		color = true;				//Farbe am Zug, true = weiss; false = schwarz
		enPassent = false;			//En Passents Verfuegbarkeit, true = ja; false= nein
		rochade_gross = 0;			//0 keiner,	1 weiss, 2 schwarz, 3 beide
		rochade_klein = 0;			//0 keiner,	1 weiss, 2 schwarz, 3 beide
		zugnummer = 0;				//
		halbzuege = 0;				//
			
	}
	
	public void InitBoard() {
		IncomingFen = boardStart;
		FenDecode(IncomingFen);
		
		
	}
	
	public boolean ResetBoard() {
		IncomingFen = boardStart;
		FenDecode(IncomingFen);
		
		return true;
		
	}

	public void BoardOutputFen(String boardFen) {
		
	}
	
	public int[] getBoard() {
		return boardArray;
	}
	
	private void fenRemainingParts(String s, int pos) {
		//rochademoeglichkeit initialisieren
		rochade_gross = 0;
		rochade_klein = 0;
		//enPassant reset
		enPassent = false;
		Character fenPart;
		int spaces = 0;
		StringBuffer numberOfMoves = new StringBuffer();
		int laengeString = s.length();
		
		while (spaces < 4) {
			fenPart = s.charAt(pos);
			
			switch (fenPart) {
			case ' ':
				spaces++;
			case 'K':
				rochade_klein = rochade_klein + 1;
				break;
			case 'Q':
				rochade_gross = rochade_gross + 1;
				break;
			case 'k':
				rochade_klein = rochade_klein + 2;
				break;
			case 'q':
				rochade_gross = rochade_gross + 2;
				break;
			case ('a'|'b'|'c'|'d'|'e'|'f'|'g'|'h'):
				enPassent = true;
			    pos++;
				break;
			default:
				break;
			}
			pos++;
		}
		//Halbzuege
		while ( s.charAt(pos) != ' ') { 
			numberOfMoves.append(s.charAt(pos));
			pos++;
		} 
		halbzuege = Integer.parseInt(numberOfMoves.toString());
		numberOfMoves.delete(0, numberOfMoves.length());
		//Zuege
		while ( pos <= laengeString) {
			numberOfMoves.append(s.charAt(pos));
			pos++;
		}
		zugnummer = Integer.parseInt(numberOfMoves.toString());
		numberOfMoves.delete(0, numberOfMoves.length());
	}
	
	
	public int[] FenDecode(String s) {
		int[] ausgabe = new int[128];
		Character fenPart;
		int pos = 0; //pos in FenString
		
		if (s.equals(boardStart) ) {
			InitBoard();
		}
		
		while (s != " ") {
			int i = 0; //pos im Feld
			fenPart = s.charAt(pos);
			
			switch (fenPart) {
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
				int leerFelder = Integer.parseInt( fenPart.toString() );
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
		fenRemainingParts(s, pos);
		
		return ausgabe;
		
	}
	
	public String FenEncode() {
		//decodiert FEN Strings
		
		return OutgoingFen;
		
	}
	
	public Character IntToFen(int toConvert) {
		switch (toConvert) {
		case 1:
			return 'p';
		case 11:
			return 'P';
		case 2:
			return 'r';
		case 12:
			return 'R';
		case 3:
			return 'n';
		case 13:
			return 'N';
		case 4:
			return 'b';
		case 14:
			return 'B';
		case 5:
			return 'q';
		case 15:
			return 'Q';
		case 6:
			return 'k';
		case 16:
			return 'K';
		default:
			return '1';
		}
	}
	 
}