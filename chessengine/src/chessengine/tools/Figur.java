package chessengine.tools;
/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
public class Figur {
	
	private byte zahl;
	private final static byte KING 	= 	6;
	private final static byte QUEEN = 	5;
	private final static byte BISHOP=	4;
	private final static byte KNIGHT=	3;
	private final static byte ROOK	=	2;
	private final static byte PAWN	=	1;
	private final static byte WEISMODIFER	=	10;
	private final static byte GRENZE	=	10;
	public Figur( char typ) {
		setFigur(typ);
	}
	public Figur( byte zahl) {
		this.zahl = zahl;
	}	
	
	public byte getZahl(){
		return zahl ;
	}
	/**
	 *  gibt die Ziffer zurueck die fuer die figurtyp bestimmung notwenig ist 1,2,3,4,5,6
	 * @return zahl%10
	 */
	public byte getZahlModulo(){
		return (byte)(zahl%GRENZE) ;
		/*if(zahl/10>=1){
			return (byte)(zahl-10);
		}else{
			return zahl;
		}*/
	}
	
	
	public boolean istWeis() {
		return zahl>=GRENZE;
	}
	public boolean istBlack() {
		return !istWeis();
	}
	public boolean istPawn(){
		return getZahlModulo() ==PAWN;
	}
	public boolean istRook(){
		return getZahlModulo() ==ROOK;
	}
	public boolean istKnight(){
		return getZahlModulo() ==KNIGHT;
	}
	public boolean istBishop(){
		return getZahlModulo() ==BISHOP;
	}
	public boolean istQueen(){
		return getZahlModulo() ==QUEEN;
	}
	public boolean istKing(){
		return getZahlModulo() ==KING;
	}


	public String toString(){ // wandelt in gross um 
	
		return toChar()+"";
	}
	public char toChar(){ // wandelt in gross um 
	
		char rueckgabe;
		switch (zahl) {
		case PAWN:
			rueckgabe = 'p';
			break;
		case PAWN+WEISMODIFER:
			rueckgabe = 'P';
			break;
		case ROOK:
			rueckgabe = 'r';
			break;
		case ROOK+WEISMODIFER:
			rueckgabe = 'R';
			break;
		case KNIGHT:
			rueckgabe = 'n';
			break;
		case KNIGHT+WEISMODIFER:
			rueckgabe= 'N';
			break;
		case BISHOP:
			rueckgabe = 'b';
			break;
		case BISHOP+WEISMODIFER:
			rueckgabe = 'B';
			break;
		case QUEEN:
			rueckgabe = 'q';
			break;
		case QUEEN+WEISMODIFER:
			rueckgabe = 'Q';
			break;
		case KING:
			rueckgabe= 'k';
			break;
		case KING+WEISMODIFER:
			rueckgabe = 'K';
			break;
			default : 
			rueckgabe = 'E';
				}
		return rueckgabe;
	}
	public void setFigur(char typ){
		switch (typ) {
		case 'p':
			zahl = PAWN;
			break;
		case 'P':
			zahl = PAWN+WEISMODIFER;
			break;
		case 'r':
			zahl = ROOK;
			break;
		case 'R':
			zahl = ROOK+WEISMODIFER;
			break;
		case 'n':
			zahl = KNIGHT;
			break;
		case 'N':
			zahl = KNIGHT+WEISMODIFER;
			break;
		case 'b':
			zahl = BISHOP;
			break;
		case 'B':
			zahl = BISHOP+WEISMODIFER;
			break;
		case 'q':
			zahl = QUEEN;
			break;
		case 'Q':
			zahl = QUEEN+WEISMODIFER;
			break;
		case 'k':
			zahl = KING;
			break;
		case 'K':
			zahl = KING+WEISMODIFER;
			break;
		}
	}
	public byte getPawn(){
		return PAWN;
	}
	public byte getRook(){
		return ROOK;
	}
	public byte getKnight(){
		return KNIGHT;
	}
	public byte getBishop(){
		return BISHOP;
	}
	public byte getQueen(){
		return QUEEN;
	}
	public byte getKing(){
		return KING;
	}
}
