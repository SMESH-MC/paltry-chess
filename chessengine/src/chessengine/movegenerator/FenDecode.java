package chessengine.movegenerator;
/**
 * Diese Klasse macht aus einem FEN-String ein Board in 0x88-Darstellung (Arrays aus Zahlen vom Typ Byte)
 * 
 * @author Schuhmacher, Kaub
 * @version 201309102044
 *
 */
public class FenDecode
implements Definitions {
	//Klassenvariable
	byte[]		schachbrett;		//Array, das die aktuelle Stellung in 0x88-Darstellung aufnimmt
	
	/**
	 * Konstruktor der Klasse, initialisiert das Schachbrett vorerst als unbelegtes Byte-Array der Laenge 128
	 */
	public FenDecode() {
		schachbrett = new byte[128];
	}
	 
	/**
	 *	Dieser Setter trifft Vorbereitungen zur Darstellung des FEN-Strings in 0x88-Darstellung und
	 *	ruft dann die Methode setSchachbrett mit den errechneten Variablen auf
	 *
	 * @param fen	Der FEN-String der aktuellen Stellung
	 */
	public void setFEN(String fen) {
		//trimmt den Stellungsteil der FEN bis vor dem ersten Leerzeichen
		String stellung = fen.substring(0, fen.indexOf(" "));
		//bereitet mit einer Hilfsmethode diese Stellungsteil auf die 0x88-Darstellung vor
		String stellung0x88 = stellungTo0x88(stellung);
		//=> stellung0x88.length() = 120
		
		//splitted die restlichen Infos der FEN an Leerzeichen auf und schreibt die Bestandteile in ein Array 
		String[] fenInfos = (fen.substring(fen.indexOf(" ") + 1)).split(" ");
		/*
		 * fenInfos[0] = Farbe am Zug
		 * fenInfos[1] = Rochadenmoeglichkeiten
		 * fenInfos[2] = en passant Moeglichkeit
		 * fenInfos[3] = Halbzuege
		 * fenInfos[4] = Zugnummer
		 */
		
		//ruft Methode auf, die das Schachbrett aus der aktuellen Stellung und den restlichen Infos aus der FEN zusammensetzt
		setSchachbrett(stellung0x88, fenInfos);
	}
	
	/**
	 * Hilfsmethode, die im FEN-String eine Reihe aufeinanderfolgende leere Felder in aufeinanderfolge einzelne leere Felder aendert und
	 * das "/" (Reihentrenner) durch ungueltige 0x88-Felder ("x") ersetzt
	 * 
	 * @param stellung	Der Stellungsteil der FEN
	 * @return	String der Laenge 120: Die Stellung in 0x88 Darstellung von A1 bis H8
	 */
	private String stellungTo0x88(String stellung) {
		stellung = stellung.replace("/", "xxxxxxxx");
		stellung = stellung.replace("2","11");
		stellung = stellung.replace("3","111");
		stellung = stellung.replace("4","1111");
		stellung = stellung.replace("5","11111");
		stellung = stellung.replace("6","111111");
		stellung = stellung.replace("7","1111111");
		stellung = stellung.replace("8","11111111");
		String stellung0x88 = stellung.replace("1", "0");
		return stellung0x88;
	}
	

	/**
	 * Getter fuer das Schachbrett in 0x88-Darstellung
	 * 
	 * @return Schachbrett in 0x88-Darstellung
	 */
	public byte[] getSchachbrett() {
		return schachbrett;  //(nur das nich gelb untersrich) //Komentar iknorisieren
	}
	
	/**
	 *	Dieser Setter beschreibt das Byte-Arry in 0x88-Darstellung mit der aktuellen Stellung
	 * 
	 * @param stellung0x88	Die aktuelle Stellung in 0x88-Darstellung aufbereitet
	 * @param fenRest		Die restlichen Infos aus der FEN 
	 */
	private void setSchachbrett(String stellung0x88, String[] fenRest) {
		//setzt mit einer Hilfsmethode die Stellung ins Schachbrett-Array
		stellungInsArray(stellung0x88);
		
		//setze ein Markerbit fuer die Farbe am Zug in das 0x88-Array
		if (fenRest[0].equals("w")) { schachbrett[120] = 1; } else { schachbrett[120] = 0;}

		//Ersetzt die Rochadenmoeglichkeiten mit einer Hilfsmethode durch einen vierstelligen String (hardcoded)
		String rochade0x88 = rochade0x88(fenRest[1]);
		//setzt die Markerbits fuer Rochadenmoeglichkeiten in das 0x88-Arrays
		// Indizes: 121=K, 122=Q, 123=k, 124=q
		for (int i=0; i<=3; i++) {
			schachbrett[121 + i] = Byte.parseByte(rochade0x88.substring(i, i+1));
		}
		
		//Ersetzt mit einer Hilfsmethode die en passant-Moeglichkeit durch eine Zahl (= Feld in 0x88-Notation) (hardcoded)
		fenRest[2] = enPassant(fenRest[2]);
		
		//setze das enPassant-Feld sowie die Anzahl der Halbzuege und Zugnummer in die letzten 3 Felder des Arrays
		for (int i = 125; i<=127; i++) {
			schachbrett[i] = Byte.parseByte(fenRest[i-123]);
		}
		
	}
	/**
	 * Hilfsmethode zu setSchachbrett, die die aktuelle Stellung ins Byte-Array (Board in 0x88-Darstellung) schreib
	 * 
	 * @param stellung	Die aktuelle Stellung als String in 0x88-Darstellung aufbereitet
	 */
	private void stellungInsArray(String stellung) {
		for (int i=0; i<=119; i++) {
			switch (stellung.charAt(i)) {
			/*
			 * x = Feld des 0x88-"Geisterschachbretts"
			 */
			//1. Moeglichkeit: breche bei einem Feld des 0x88-Geisterschachbretts ab, ohne etwas hineinzuschreiben
			case 'x' :	break;
			//2. Moeglichkeit: schreibe einen Wert in das Feld. Verhindert Zugriff auf null beim durchlaufen des ganzen Arrays
			//case 'x' : schachbrett[i] = ungueltigesFeld;
				//break;
			case '1' : schachbrett[i] = leeresFeld;	break;
			case 'P' : schachbrett[i] = pawn_w;		break;
			case 'p' : schachbrett[i] = pawn_b;		break;
			case 'R' : schachbrett[i] = rook_w;		break;
			case 'r' : schachbrett[i] = rook_b;		break;
			case 'N' : schachbrett[i] = knight_w;	break;
			case 'n' : schachbrett[i] = knight_b;	break;
			case 'B' : schachbrett[i] = bishop_w;	break;
			case 'b' : schachbrett[i] = bishop_b;	break;
			case 'K' : schachbrett[i] = king_w;		break;
			case 'k' : schachbrett[i] = king_b;		break;
			case 'Q' : schachbrett[i] = queen_w;	break;
			case 'q' : schachbrett[i] = queen_b;	break;
			}
		}
	}
	/**
	 * Diese Hilfsmethode macht aus den Infos zu den Rochademoeglichkeiten aus der FEN einen vierstellige String aus 0en und 1en, wobei
	 * die vier Stellen die Rochademoeglichkeiten von KQkq mittels eines Bitmarkers markieren
	 * 
	 * @param fenRochadeMoeglichkeiten	Die Rochademoeglichkeiten in FEN
	 * @return Die Rochademoeglichkeiten als Bitmarker fuer KQkq
	 */
	private String rochade0x88(String fenRochadeMoeglichkeiten) {
		String rochadeMoegl0x88 = "";
		switch (fenRochadeMoeglichkeiten) {
		case "-" : rochadeMoegl0x88 = "0000";			break;
		case "K" : rochadeMoegl0x88 = "1000";			break;
		case "Q" : rochadeMoegl0x88 = "0100";			break;
		case "k" : rochadeMoegl0x88 = "0010";			break;
		case "q" : rochadeMoegl0x88 = "0001";			break;
		case "KQ" : rochadeMoegl0x88 = "1100";			break;
		case "Kk" : rochadeMoegl0x88 = "1010";			break;
		case "Kq" : rochadeMoegl0x88 = "1001";			break;
		case "Qk" : rochadeMoegl0x88 = "0110";			break;
		case "Qq" : rochadeMoegl0x88 = "0101";			break;
		case "kq" : rochadeMoegl0x88 = "0011";			break;
		case "KQk" : rochadeMoegl0x88 = "1110";			break;
		case "KQq" : rochadeMoegl0x88 = "1101";			break;
		case "Kkq" : rochadeMoegl0x88 = "1011";			break;
		case "Qkq" : rochadeMoegl0x88 = "0111";			break;
		case "KQkq" : rochadeMoegl0x88 = "1111";		break;
		}
		return rochadeMoegl0x88;
	}
	
	/**
	 * Hilfsmethode, die aus der enPassant-Info der FEN das enPassant-Feld in 0x88-Darstellung ausgibt
	 * 
	 * @param enPassantFeld	Das Feld (z.B. g6), das im letzten Zug von einem Bauer uebersprungen wurde
	 * @return	En Passant-Feld in 0x88-Darstellung
	 */
	private String enPassant(String enPassantFeld) {
		String enPassant0x88 = "";
		switch (enPassantFeld) {
		case "-" : enPassant0x88 = "-1"; //negativ, wenn kein en Passant möglich
			break;
		case "a3" : enPassant0x88 = "32";		break;
		case "b3" : enPassant0x88 = "33";		break;
		case "c3" : enPassant0x88 = "34";		break;
		case "d3" : enPassant0x88 = "35";		break;
		case "e3" : enPassant0x88 = "36";		break;
		case "f3" : enPassant0x88 = "37";		break;
		case "g3" : enPassant0x88 = "38";		break;
		case "h3" : enPassant0x88 = "39";		break;
		case "a6" : enPassant0x88 = "80";		break;
		case "b6" : enPassant0x88 = "81";		break;
		case "c6" : enPassant0x88 = "82";		break;
		case "d6" : enPassant0x88 = "83";		break;
		case "e6" : enPassant0x88 = "84";		break;
		case "f6" : enPassant0x88 = "85";		break;
		case "g6" : enPassant0x88 = "86";		break;
		case "h6" : enPassant0x88 = "87";		break;
		}
		return enPassant0x88;
	}
		
}
