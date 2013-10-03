package chessengine;

/**
 * Klasse zur Umschreibung des letzten Zug des Spielers in einen FEN String
 * Benutzung:
 * 1. Erstellung eines Objektes (benoetigt aktuelles UCI Objektreferenz + aktuelles Board)
 * 2. Benutzung der (noch nicht vorhandenen) getFEN Methode
 * @author Christopher Schuetz
 */
public class ToFen extends Board implements ToFenInterface {
	
	// TODO (von chschuetz)
	// aktuelles Board abgreifen (muss am Board geaendert werden)								80%
	// BOOLs fuer Rochade abgreifen																0%
	// alle moves abgreifen																		ERLEDIGT
	// letzten move des spielers abgreifen														ERLEDIGT
	// Figuren ändern																			80%
	// +- Rochade 	 																			ERLEDIGT
	// +- normaler Zug 																			ERLEDIGT
	// +- En passant?!?!																		0%
	// +- PROMOTION																				ERLEDIGT
	// neues Board in ein FEN umwandeln															0%
	
	// Fragen:
	// wie registriere ich einen enpassant zug?

	private String allMoves;		// String aller Moves bis dato
	private String[] allMovesSplit;	// alle Moves gesplittet
	private String lastMove;		// String des letzten Zuges des Spielers
	private boolean currentColor;	// Farbe am Zug
	private Board currentBoard;		// aktuelles Board
	private int[] temp;				// Inhalt des Boards in Zahlen
	private String outgoingFen;		// Fertiger FEN String
	private UCI uci;
	private int rochadeGross;
	private int rochadeKlein;
	private boolean rochadeGrossW;
	private boolean rochadeKleinW;
	private boolean rochadeGrossS;
	private boolean rochadeKleinS;
	private byte aktuelleRochade;	// Gebrauch bei runRochade(), 0 = nix, 1 = grossW, 2 = kleinW, 3 = grossS, 4 = kleinS
	

	/**
	 * Konstruktor, benoetigt aktuelles Board + UCI Objektreferenz
	 * @param uci aktuelle UCI Objektreferenz
	 * @param currentBoard aktuelles Board
	 */
	public ToFen(UCI uci, Board currentBoard) {
		this.uci = uci;
		this.currentBoard = currentBoard;
		this.temp = currentBoard.boardArray;
		this.currentColor = !currentBoard.color;	// Da der Spieler am Zug ist/war muss negiert werden, oder?
		this.allMoves = uci.getMovesList();			
		this.allMovesSplit = this.allMoves.split(" ");
		this.lastMove = this.allMovesSplit[allMovesSplit.length-1];
		this.outgoingFen = "";
		this.aktuelleRochade = 0;
		this.rochadeGross = currentBoard.getRochadeGross();
		this.rochadeKlein = currentBoard.getRochadeKlein();
		initRochadeGross(rochadeGross);
		initRochadeKlein(rochadeKlein);
		start();
		setFEN();
	}
	
	/**
	 * startmethode zum schauen ob der String laenger ist als 4 Zeichen
	 * Wenn ja ist es eine Promotion
	 * Wenn nein schauen ob Rochade
	 */
	private void start() {
		if (this.lastMove.length() > 4) {
			runPromotion();
		} else {
			detectRochade();
		}
	}
	
	private void initRochadeGross(int rochade) {
		switch (rochade) {
		case (0):
			this.rochadeGrossS = false;
			this.rochadeGrossW = false;
			break;
		case (1):
			this.rochadeGrossS = false;
			this.rochadeGrossW = true;
			break;
		case (2):
			this.rochadeGrossS = true;
			this.rochadeGrossW = false;
			break;
		case (3):
			this.rochadeGrossS = true;
			this.rochadeGrossW = true;
		default:
			break;
		}
	}
	
	private void initRochadeKlein (int rochade) {
		switch (rochade) {
		case (0):
			this.rochadeKleinS = false;
			this.rochadeKleinW = false;
			break;
		case (1):
			this.rochadeKleinS = false;
			this.rochadeKleinW = true;
			break;
		case (2):
			this.rochadeKleinS = true;
			this.rochadeKleinW = false;
			break;
		case (3):
			this.rochadeKleinS = true;
			this.rochadeKleinW = true;
			break;
		default:
			break;
		}
	}
	
	/**
	 * Methode zum Ausfuehren einer Promotion
	 */
	private void runPromotion() {
		String startPos = this.lastMove.substring(0, 2);		// Erste zwei Zeichen Startposition
		String endPos = this.lastMove.substring(2, 4);			// Zweite zwei Zeichen Endposition
		char neueFigur = this.lastMove.charAt(4);				// letztes Zeichen Buchstabe der neuen Figur
		if (this.currentColor) {
			neueFigur = Character.toUpperCase(neueFigur);		// wenn weiss, Grossbuchstabe
		} else {
			neueFigur = Character.toLowerCase(neueFigur);		// wenn schwarz, Kleinbuchstabe
		}
		int figurWert = this.getWertFigur(neueFigur);				// int wert fuer die Figur (wie im Board)
		int startFeld = this.getField(startPos);				// # des Startfeldes im Array
		int endFeld = this.getField(endPos);					// # des Endfeldes im Array
		this.temp[startFeld] = 0;								// Startfeld loeschen
		this.temp[endFeld] = figurWert;							// neue Figur in das Endfeld schreiben
	}
	
	/**
	 * Methode zur Ueberpruefung einer Rochade
	 * Wenn die Rochade noch moeglich ist und ein Rochadenzug erscheint wird diese ausgefuehrt 
	 * und die Rochademoeglichkeit geloescht
	 */
	private void detectRochade() {
		if (rochadeGrossW && this.lastMove == "e1b1") {
			this.aktuelleRochade = 1;
			runRochade(aktuelleRochade);
			rochadeGrossW = false;
		} else if (rochadeKleinW && this.lastMove == "e1g1") {
			this.aktuelleRochade = 2;
			runRochade(aktuelleRochade);
			rochadeKleinW = false;
		} else if (rochadeGrossS && this.lastMove == "e8b8") {
			this.aktuelleRochade = 3;
			runRochade(aktuelleRochade);
			rochadeGrossS = false;
		} else if (rochadeKleinS && this.lastMove == "e8g8") {
			this.aktuelleRochade = 4;
			runRochade(aktuelleRochade);
			rochadeKleinS = false;
		} else {
			run();
		}
	}
	
	/**
	 * Ausfuehrung Rochade
	 * @param aktuelleRochade Zahll welche Rochade am Zug ist: 1 = grossW, 2 = kleinW, 3 = grossS, 4 = kleinS
	 */
	private void runRochade(byte aktuelleRochade) {
		switch (aktuelleRochade) {
		case 1:					// Rochade gross weiss
			this.temp[4] = 0;	// Position Koenig loeschen
			this.temp[2] = 12;	// neue Position Turm
			this.temp[1] = 16;	// neue Position Koenig
			this.temp[0] = 0;	// Position Turm loeschen
			break;
		case 2:					// Rochade klein weiss
			this.temp[4] = 0;	// Position Koenig loeschen
			this.temp[7] = 0;	// Position Turm loeschen
			this.temp[5] = 12;	// neue Position Turm
			this.temp[6] = 16;	// neue Position Koenig
			break;
		case 3:					// Rochade gross schwarz
			this.temp[116] = 0;	// Position Koenig loeschen
			this.temp[112] = 0;	// Position Turm loeschen
			this.temp[113] = 6; // neue Position Koenig
			this.temp[114] = 2; // neue Position Turm
			break;
		case 4:					// Rochade klein schwarz
			this.temp[116] = 0;	// Position Koenig loeschen
			this.temp[119] = 0; // Position Turm loeschen
			this.temp[117] = 2; // neue Position Turm
			this.temp[118] = 6; // neue Position Koenig
			break;
		default:
			break;
		}
	}
	
	/**
	 * Methode die den letzten Zug des Spielers in das Array schreibt
	 */
	private void run() {
		String startPos = this.lastMove.substring(0, 2);				// Erste zwei Zeichen als Startposition festlegen
		String endPos = this.lastMove.substring(2, lastMove.length());	// Letzte zwei Zeichen als EndPosition festlegen
		
		int bewegendeFigur;												
		int startFeld = this.getField(startPos);						// String der Position in Position Array umwandeln
		int endFeld = this.getField(endPos);
		bewegendeFigur = this.temp[startFeld];							// die zu bewegende Figur speichern
		this.temp[startFeld] = 0;										// Startfeld löschen
		this.temp[endFeld] = bewegendeFigur;							// Figur auf Endfeld setzen
	}
	
	/**
	 * Methode zur Bestimmung des Platzes im Array
	 * @param feld String indem das Feld ausgeschrieben steht
	 * @return feld als Zahl (Platz im Array)
	 */
	private int getField(String feld) {
		int fieldNum = 0;
		switch (feld) {
		case "a1":
			fieldNum = 0;
			break;
		case "a2":
			fieldNum = 16;
			break;
		case "a3":
			fieldNum = 32;
			break;
		case "a4":
			fieldNum = 48;
			break;
		case "a5":
			fieldNum = 64;
			break;
		case "a6":
			fieldNum = 80;
			break;
		case "a7":
			fieldNum = 96;
			break;
		case "a8":
			fieldNum = 112;
			break;
		case "b1":
			fieldNum = 1;
			break;
		case "b2":
			fieldNum = 17;
			break;
		case "b3":
			fieldNum = 33;
			break;
		case "b4":
			fieldNum = 49;
			break;
		case "b5":
			fieldNum = 65;
			break;
		case "b6":
			fieldNum = 81;
			break;
		case "b7":
			fieldNum = 97;
			break;
		case "b8":
			fieldNum = 113;
			break;
		case "c1":
			fieldNum = 2;
			break;
		case "c2":
			fieldNum = 18;
			break;
		case "c3":
			fieldNum = 34;
			break;
		case "c4":
			fieldNum = 50;
			break;
		case "c5":
			fieldNum = 66;
			break;
		case "c6":
			fieldNum = 82;
			break;
		case "c7":
			fieldNum = 98;
			break;
		case "c8":
			fieldNum = 114;
			break;
		case "d1":
			fieldNum = 3;
			break;
		case "d2":
			fieldNum = 19;
			break;
		case "d3":
			fieldNum = 35;
			break;
		case "d4":
			fieldNum = 51;
			break;
		case "d5":
			fieldNum = 67;
			break;
		case "d6":
			fieldNum = 83;
			break;
		case "d7":
			fieldNum = 99;
			break;
		case "d8":
			fieldNum = 115;
			break;
		case "e1":
			fieldNum = 4;
			break;
		case "e2":
			fieldNum = 20;
			break;
		case "e3":
			fieldNum = 36;
			break;
		case "e4":
			fieldNum = 52;
			break;
		case "e5":
			fieldNum = 68;
			break;
		case "e6":
			fieldNum = 84;
			break;
		case "e7":
			fieldNum = 100;
			break;
		case "e8":
			fieldNum = 116;
			break;
		case "f1":
			fieldNum = 5;
			break;
		case "f2":
			fieldNum = 21;
			break;
		case "f3":
			fieldNum = 37;
			break;
		case "f4":
			fieldNum = 53;
			break;
		case "f5":
			fieldNum = 69;
			break;
		case "f6":
			fieldNum = 85;
			break;
		case "f7":
			fieldNum = 101;
			break;
		case "f8":
			fieldNum = 117;
			break;
		case "g1":
			fieldNum = 6;
			break;
		case "g2":
			fieldNum = 22;
			break;
		case "g3":
			fieldNum = 38;
			break;
		case "g4":
			fieldNum = 54;
			break;
		case "g5":
			fieldNum = 70;
			break;
		case "g6":
			fieldNum = 86;
			break;
		case "g7":
			fieldNum = 102;
			break;
		case "g8":
			fieldNum = 118;
			break;
		case "h1":
			fieldNum = 7;
			break;
		case "h2":
			fieldNum = 23;
			break;
		case "h3":
			fieldNum = 39;
			break;
		case "h4":
			fieldNum = 55;
			break;
		case "h5":
			fieldNum = 71;
			break;
		case "h6":
			fieldNum = 87;
			break;
		case "h7":
			fieldNum = 103;
			break;
		case "h8":
			fieldNum = 119;
			break;
		default:
			break;	
		}
		return fieldNum;
	}
	
	private int getWertFigur(char c) {
		switch (c) {
		case 'p':
			return 1;
		case 'P':
			return 11;
		case 'r':
			return 2;
		case 'R':
			return 12;
		case 'n':
			return 3;
		case 'N':
			return 13;
		case 'b':
			return 4;
		case 'B':
			return 14;
		case 'q':
			return 5;
		case 'Q':
			return 15;
		case 'k':
			return 6;
		case 'K':
			return 16;
		default:
			return 0;	
		}
	}
	
	private char figurNachFen (int figur) {
		switch (figur) {
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
	
	private void setFEN() {
		// Konstruiere das Brett als Fen
		for (int i = 0; i <=119; i++) {
			if ((i & 136) == 0) {
				outgoingFen += figurNachFen(temp[i]);
			} else {
				outgoingFen += "/";
				i += 7;
			}
		}
		// Leerfelder richtig schreiben
		outgoingFen = summiereLeerfelder(outgoingFen);
		// Farbe hinzufuegen
		if (this.currentColor) {
			outgoingFen += " w ";
		} else {
			outgoingFen += " s ";
		}
		// Rochade hinzufuegen
		int keineRochade = 0;
		if (this.rochadeKleinW) {
			outgoingFen += "K";
		} else {
			keineRochade++;
		}
		
		if (this.rochadeGrossW) {
			outgoingFen += "Q";
		} else {
			keineRochade++;
		}
		
		if (this.rochadeKleinS) {
			outgoingFen += "k";
		} else {
			keineRochade++;
		}
		
		if (this.rochadeGrossS) {
			outgoingFen += "q";
		} else {
			keineRochade++;
		}
		
		if (keineRochade == 4) {
			outgoingFen += "-";
		}
		
		// Enpassant Feld
		// Halbzuege
		// Zugnummer
		
		
	}
	
	private String summiereLeerfelder(String fen) {
		fen = fen.replace("00000000","8");
		fen = fen.replace("0000000","7");
		fen = fen.replace("000000","6");
		fen = fen.replace("00000","5");
		fen = fen.replace("0000","4");
		fen = fen.replace("000","3");
		fen = fen.replace("00","2");
		fen = fen.replace("0","1");
		return fen;
	}
	
	/* (non-Javadoc)
	 * @see chessengine.ToFenInterface#getFEN()
	 */
	@Override
	public String getFEN() {
		return this.outgoingFen;
	}
	
}
