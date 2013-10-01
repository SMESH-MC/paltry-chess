package chessengine.tools;
/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
public class Figur {
	
	private int zahl;
	
	public Figur( char typ) {
		setFigur(typ);
	}
	public Figur( int zahl) {
		this.zahl = zahl;
	}	
	
	public int getZahl(){
		return zahl ;
	}
	/**
	 *  gibt die Ziffer zurueck die fuer die figurtyp bestimmung notwenig ist 1,2,3,4,5,6
	 * @return zahl%10
	 */
	public int getZahlModulo(){
		return zahl%10 ;
	}
	
	
	public boolean istWeis() {
		return zahl>9;
	}
	public boolean istBlack() {
		return !istWeis();
	}
	public boolean istPawn(){
		return zahl%10 ==1;
	}
	public boolean istRook(){
		return zahl%10 ==2;
	}
	public boolean istKnight(){
		return zahl%10 ==3;
	}
	public boolean istBishop(){
		return zahl%10 ==4;
	}
	public boolean istQueen(){
		return zahl%10 ==5;
	}
	public boolean istKing(){
		return zahl%10 ==6;
	}


	public String toString(){ // wandelt in gross um 
	
		return toChar()+"";
	}
	public char toChar(){ // wandelt in gross um 
	
		char rueckgabe;
		switch (zahl) {
		case 1:
			rueckgabe = 'p';
			break;
		case 11:
			rueckgabe = 'P';
			break;
		case 2:
			rueckgabe = 'r';
			break;
		case 12:
			rueckgabe = 'R';
			break;
		case 3:
			rueckgabe = 'n';
			break;
		case 13:
			rueckgabe= 'N';
			break;
		case 4:
			rueckgabe = 'b';
			break;
		case 14:
			rueckgabe = 'B';
			break;
		case 5:
			rueckgabe = 'q';
			break;
		case 15:
			rueckgabe = 'Q';
			break;
		case 6:
			rueckgabe= 'k';
			break;
		case 16:
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
			zahl = 1;
			break;
		case 'P':
			zahl = 11;
			break;
		case 'r':
			zahl = 2;
			break;
		case 'R':
			zahl = 12;
			break;
		case 'n':
			zahl = 3;
			break;
		case 'N':
			zahl = 13;
			break;
		case 'b':
			zahl = 4;
			break;
		case 'B':
			zahl = 14;
			break;
		case 'q':
			zahl = 5;
			break;
		case 'Q':
			zahl = 15;
			break;
		case 'k':
			zahl = 6;
			break;
		case 'K':
			zahl = 16;
			break;
		}
	}
}
