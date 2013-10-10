package chessengine;

import chessengine.figurbewertung.FigurBewertung;

/**
 * Klasse zur Umschreibung des letzten Zug des Spielers in einen FEN String
 * Benutzung:
 * 1. Erstellung eines Objektes (benoetigt aktuelles UCI Objektreferenz + aktuelles Board)
 * 2. Benutzung der (noch nicht vorhandenen) getFEN Methode
 * @author Christopher Schuetz
 */
public class ToFen implements ToFenInterface {

	// ATTRIBUTE
	private String allMoves;		// String aller Moves bis dato
	private String[] allMovesSplit;	// alle Moves gesplittet
	private String lastMove;		// String des letzten Zuges des Spielers
	private boolean currentColor;	// Farbe am Zug
	private Board currentBoard;		// aktuelles Board
	private int[] temp;				// Inhalt des Boards in Zahlen
	private String outgoingFen;		// Fertiger FEN String
	private UCI uci;				// UCI Objektreferenz
	private int boardValueOld;		// Boardwert vor dem Zug des Spielers (benutzt um einen Schlag zu erkennen, fuer Halbzuege)
	private int boardValueNew;		// Boardwert nach dem Zug des Spielers (benutzt um einen Schlag zu erkennen, fuer Halbzuege)
	private int rochadeGross;		// Wert der angibt welche grosse Rochade moeglich ist, selbes Format wie im Board
	private int rochadeKlein;		// Wert der angibt welche kleine Rochade moeglich ist, selbes Format wie im Board
	private String enpassantNew;	// String des EnPassant Feldes fuer den FEN
	private int halbzuege;			// Wert der Halbzuege fuer den FEN
	private int zuege;				// Wert der Zuege fuer den FEN
	private boolean rochadeGrossW;	// boolean fuer grosse weisse Rochade
	private boolean rochadeKleinW;	// boolean fuer kleine weisse Rochade
	private boolean rochadeGrossS;	// boolean fuer grosse schwarze Rochade
	private boolean rochadeKleinS;	// boolean fuer kleine schwarze Rochade
	private char bewegteFigur;		// character der bewegten Figur
	private byte aktuelleRochade;	// Gebrauch bei runRochade(), 0 = nix, 1 = grossW, 2 = kleinW, 3 = grossS, 4 = kleinS
	//private FigurBewertung bewertungKlasse; //TODO

	/**
	 * Konstruktor, benoetigt aktuelles Board + UCI Objektreferenz
	 * @param uci aktuelle UCI Objektreferenz
	 * @param currentBoard aktuelles Board
	 */
	public ToFen(UCI uci, Board currentBoard) {
		//this.bewertungKlasse = bewertungKlasse;//TODO
		this.uci = uci;						// Initialisierung des UCI Objekts
		this.currentBoard = currentBoard;	// Initialisierung des Board Objekts
		initVariables();					// Initialisierung von allem anderen (ausgelagert)
		start();							// Mache den Zug
		setFEN();							// Schreibe das FEN
	}
	
	// HAUPTMETHODEN
	
	/**
	 * Methode zum initialisieren der ganzen Variablen
	 */
	private void initVariables() {
		this.temp = this.currentBoard.boardArray;
		this.currentColor = !this.currentBoard.color;				// Da der Spieler am Zug ist/war muss negiert werden, oder?
		this.allMoves = this.uci.getMovesList();			
		this.allMovesSplit = this.allMoves.split(" ");
		this.lastMove = this.allMovesSplit[allMovesSplit.length-1];
		this.halbzuege = this.currentBoard.getHalbzuege();
		this.zuege =  this.currentBoard.getZugnummer();
		this.outgoingFen = "";
		this.aktuelleRochade = 0;
		this.rochadeGross = this.currentBoard.getRochadeGross();
		this.rochadeKlein = this.currentBoard.getRochadeKlein();
		//this.boardValueOld = this.currentBoard.getBoardValue(bewertungKlasse); //TODO
		initRochadeGross(rochadeGross);								// initiiert grosse Rochaden
		initRochadeKlein(rochadeKlein);								// initiiert kleine Rochaden
	}
	
	/**
	 * startmethode zum schauen ob der String laenger ist als 4 Zeichen
	 * Wenn ja ist es eine Promotion
	 * Wenn nein schauen ob Rochade
	 */
	private void start() {
		if (this.lastMove.length() > 4) {			// wenn der String laenger ist als 4 ist es eine Promotion
			runPromotion();
		} else {									// wenn nicht schaue nach obs eine Rochade ist
			detectRochade();
		}
		// folgende Zeilen werden ganz am Ende ausgefuehrt
		// das hier ist ein wenig kompliziert:
		// das endergebnis des folgenden Ausdrucks ist ein char der Figur
		// Erst wird das Startfeld aus dem letzten Zug extrahiert
		// dieses mittels getField in ein int umgewandelt
		// dann wird geschaut was im boardarray temp an dieser Stelle steht
		// und zuletzt dann zu dem richtigen char umgewandelt
		this.bewegteFigur = figurNachFen(temp[getField(this.lastMove.substring(0, 2))]);	
		this.currentBoard.boardArray = temp;
		//this.boardValueNew = this.currentBoard.getBoardValue(bewertungKlasse); //TODO
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
		int figurWert = this.getWertFigur(neueFigur);			// int wert fuer die Figur (wie im Board)
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
			run();		// falls keine Rochade wird diese Methode ausgefuehrt
		}
	}
	
	/**
	 * Ausfuehrung Rochade
	 * @param aktuelleRochade Zahl welche Rochade am Zug ist: 1 = grossW, 2 = kleinW, 3 = grossS, 4 = kleinS
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
		
		// Nachschauen ob es ein EnPassant Zug ist und ihn dann ausfuehren, ansonsten ganz normaler Zug
		
		if ((bewegendeFigur == 11) && (startPos.charAt(0) != endPos.charAt(0)) && (this.temp[endFeld] == 0)) {
			runEnPassant(bewegendeFigur, startPos, endPos, true);
		} else if ((bewegendeFigur == 1) && (startPos.charAt(0) != endPos.charAt(0)) && (this.temp[endFeld] == 0)){
			runEnPassant(bewegendeFigur, startPos, endPos, false);
		} else {
			this.temp[startFeld] = 0;										// Startfeld löschen
			this.temp[endFeld] = bewegendeFigur;							// Figur auf Endfeld setzen
		}
	}
	
	/**
	 * Methode die einen EnPassant Zug verarbeitet
	 * @param bewegendeFigur Wert der Figur die bewegt wird
	 * @param startPos String der Startposition
	 * @param endPos String der Endposition
	 * @param color true = weiss, false = schwarz
	 */
	private void runEnPassant(int bewegendeFigur, String startPos, String endPos, boolean color) {
		int startFeld = this.getField(startPos);
		int endFeld = this.getField(endPos);
		String enPassantPos;
		int enPassantFeld;
		// Logik:
		// EnPassant Zuege sind bei weiss:
		// +-- (a-h)5(a-h)6 (diagonal)
		// +-- daher ist ein schwarzer Bauer der so geschlagen werden kann immer auf Reihe 5
		// Analog bei schwarz:
		// +-- (a-h)4(a-h)3 (diagonal)
		// +-- daher ist ein weisser Bauer der so geschlagen werden kann immer auf Reihe 4
		if (color) {
			// EnPassant Zug weiss
			enPassantPos = endPos.charAt(0) + "5";
			enPassantFeld = this.getField(enPassantPos);
			
			this.temp[startFeld] = 0;				// Startfeld loeschen
			this.temp[endFeld] = bewegendeFigur;	// Bauer in das Endfeld schrieben
			this.temp[enPassantFeld] = 0;			// "geschlagenen" Bauer im EnPassantfeld loeschen
			
		} else {
			// EnPassant Zug schwarz
			enPassantPos = endPos.charAt(0) + "4";
			enPassantFeld = this.getField(enPassantPos);
			
			this.temp[startFeld] = 0;				// Startfeld loeschen
			this.temp[endFeld] = bewegendeFigur;	// Bauer in das Endfeld schreiben
			this.temp[enPassantFeld] = 0;			// "geschlagenen" Bauer im EnPassantFeld loeschen
			
		}
	}
	
	/**
	 * Methode zum Schreiben des FEN Strings
	 */
	private void setFEN() {
		// Konstruiere das Brett als Fen
		for (int i = 112; i >= 0; i -= 16) {
			for (int j = 0; j <= 7; j++) {
				outgoingFen += figurNachFen(temp[i+j]);
			}
			outgoingFen += "/";
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
		outgoingFen += " ";
		this.enpassantNew = this.writeEnpassant();
		outgoingFen += this.enpassantNew;
		// Halbzuege
		outgoingFen += " ";
		// wenn nichts geschlagen wurde und kein bauer bewegt wurde, halbzuege +1, ansonsten halbzuege reset (= 0)
		if ((this.detectSchlag(boardValueOld, boardValueNew) == 0) && (this.bewegteFigur != 'p' || this.bewegteFigur != 'P')) {
			this.halbzuege += 1;
		} else {
			this.halbzuege = 0;
		}
		outgoingFen += this.halbzuege;
		// Zugnummer, wenn weiss, zug +1
		if (this.currentColor) {
			this.zuege += 1;
		}
		outgoingFen += " ";
		outgoingFen += this.zuege;
	}
	
	// HAUPTMETHODEN ENDE
	// HILFSMETHODEN
	
	/**
	 * Methode zum Herausfinden ob bei dem Zug des Spielers eine Figur geschlagen wurde (= Der Wert des Boards sich veraendert hat)
	 * @param alterWert Wert vor dem Zug
	 * @param neuerWert Wert nach dem Zug
	 * @return Wenn ungleich 0 -> Schlag, wenn gleich 0 -> kein Schlag
	 */
	private int detectSchlag(int alterWert, int neuerWert) {
		return alterWert - neuerWert;
	}
	
	/**
	 * Methode zum Schreiben des EnPassant Teil des FEN, falls ein Bauer um 2 Felder nach vorne bewegt wurde
	 * @return Feld der EnPassant Moeglichkeit fuer den FEN
	 */
	private String writeEnpassant() {
		String feld = this.lastMove.substring(0, 2);
		char figur = figurNachFen(temp[getField(feld)]);
		// enpassant erkennung fuer weiss
		if ((this.lastMove == "a2a4" || 
			this.lastMove == "b2b4" ||
			this.lastMove == "c2c4" ||
			this.lastMove == "d2d4" ||
			this.lastMove == "e2e4" ||
			this.lastMove == "f2f4" ||
			this.lastMove == "g2g4" ||
			this.lastMove == "h2h4") &&
			figur == 'P') {
			String enpassant = feld.charAt(0) + "3";
			return enpassant;
		} else if ((this.lastMove == "a7a5" ||	// enpassant erkennung fuer schwarz
					this.lastMove == "b7b5" ||
					this.lastMove == "c7c5" ||
					this.lastMove == "d7d5" ||
					this.lastMove == "e7e5" ||
					this.lastMove == "f7f5" ||
					this.lastMove == "g7g5" ||
					this.lastMove == "h7h5") &&
					figur == 'p') {
			String enpassant = feld.charAt(0) + "6";
			return enpassant;
		} else {						// wenn kein bauerx2 zug, kein enpassant
			String enpassant = "-";
			return enpassant;
		}
		
	}
	
	/**
	 * Methode zum Initieren der grossen Rochaden
	 * @param rochade 0 = keiner, 1 = weiss, 2 = schwarz, 3 = beide
	 */
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
	
	/**
	 * Methode zum Initiieren der kleinen Rochaden
	 * @param rochade 0 = keiner, 1 = weiss, 2 = schwarz, 3 = beide
	 */
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
	
	/**
	 * Methode, die den int Wert der Figur im Array des Boards zurueckgibt
	 * @param c character der Figur
	 * @return Wert der Figur im Array
	 */
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
	
	/**
	 * Methode, die den character einer Figur zurueckgibt
	 * @param figur Wert der Figur im Array
	 * @return character der Figur
	 */
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
	
	/**
	 * Hilfsmethode, die die Leerfelder im FEN String erkennt und addiert
	 * @param fen Teil des FEN Strings, der sich um das Board kuemmert
	 * @return richtiger FEN String teil
	 */
	private String summiereLeerfelder(String fen) {
		fen = fen.replace("11111111","8");
		fen = fen.replace("1111111","7");
		fen = fen.replace("111111","6");
		fen = fen.replace("11111","5");
		fen = fen.replace("1111","4");
		fen = fen.replace("111","3");
		fen = fen.replace("11","2");
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
