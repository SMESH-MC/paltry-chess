package chessengine.tools;
/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
public class Figur {
	
	private boolean istWeis;
	private char typ; // Oder vom typ FigurenTyp kleinBuchstabe
	
	public Figur(boolean isWeis, char typ) {
		super();
		this.istWeis = isWeis;
		setTyp(typ);
	}
	

	public boolean getIstWeis() {
		return istWeis;
	}
	public void setWeis(boolean isWeis) {
		this.istWeis = isWeis;
	}
	public char getTyp() { 
		return typ; //als int
	}

	public void setTyp(char typ) { //wandelt in klein um
		if(istWeis){
			int charInt = typ + 32 ;
			this.typ =(char)charInt;
		}else{
			this.typ = typ;
		}
	}
	/**
	 * 
	 * nicht geprueft
	 */
	public String toString(){ // wandelt in gross um 
		int charInt = typ;
		String rueckgabe;
		
		if(istWeis){
			charInt = charInt - 32 ;
			
			rueckgabe =(char)charInt+"";
		}else{
			rueckgabe = typ+"";
		}
	
		return rueckgabe;
	}
	public char toChar(){// wandelt in gross um 
		int charInt = typ;
		char rueckgabe;
		
		if(istWeis){
			charInt = charInt - 32 ;
			
			rueckgabe =(char)charInt;
		}else{
			rueckgabe = typ;
		}
	
		return rueckgabe;
	}
}
