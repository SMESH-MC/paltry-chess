package chessengine;

/**
 * @author Christopher Schuetz
 */
public class ToFen extends Board {
	
	// TODO (von chschuetz)
	// aktuelles Board abgreifen (muss am Board ge�ndert werden)								80%
	// alle moves abgreifen																		ERLEDIGT
	// letzten move des spielers abgreifen (muss in UCI ge�ndert werden, statische Methode)		80%
	// Figuren �ndern																			80%
	// +- Rochade 	 																			ERLEDIGT
	// +- normaler Zug 																			ERLEDIGT
	// +- En passant?!?!																		0%
	// neues Board in ein FEN umwandeln															0%
	
	// Fragen:
	// was passiert bei Rochade?
	// wie registriere ich einen enpassant zug?

	private String allMoves;		// String aller Moves bis dato
	private String lastMove;		// String des letzten Zuges des Spielers
	private boolean currentColor;	// Farbe am Zug
	private Board currentBoard;		// aktuelles Board
	private int[] temp;				// Inhalt des Boards in Zahlen
	private String OutgoingFen;		// Fertiger FEN String
	private boolean rochadeGrossW;
	private boolean rochadeKleinW;
	private boolean rochadeGrossS;
	private boolean rochadeKleinS;
	private byte aktuelleRochade;	// Gebrauch bei runRochade(), 0 = nix, 1 = grossW, 2 = kleinW, 3 = grossS, 4 = kleinS
	

	/**
	 * Konstruktor, benoetigt aktuelles Board
	 * @param currentBoard aktuelles Board
	 */
	public ToFen(Board currentBoard) {
		this.currentBoard = currentBoard;
		this.temp = currentBoard.boardArray;
		this.currentColor = !currentBoard.color;	// Da der Spieler am Zug ist/war muss negiert werden, oder?
		this.allMoves = UCI.getMovesList();			// muss die Funktion statisch machen damits funzt
		this.lastMove = allMoves.substring(allMoves.length()-4, allMoves.length());
		this.aktuelleRochade = 0;
		detectRochade();
	}
	
	/**
	 * Methode zur Ueberpruefung einer Rochade
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
		this.temp[startFeld] = 0;										// Startfeld l�schen
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
	
}