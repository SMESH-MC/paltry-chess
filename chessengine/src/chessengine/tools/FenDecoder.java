	/**
 	* 
 	* @author Philip Hunsicker
 	* Stand : 25.09.2013

	 * Decodiert den FEN String in eine Array mit 0 0 ist unten links, und weis  ist unten
	 *
	 * @param fen genormter fen string (Forsyth-Edwards-Notation)
	 * @return figuren array mit 

	 * schwarz
	 * 02
	 * 01
	 * 00 10 20
	 * weis
	 */
package chessengine.tools;

public class FenDecoder {
	private final static String trennzeichen = "/";
	private String[]  fenTeile; // der restlich teil vom FEN string der nicht fuer die Figuren positionen benoetigt wird
	private Brett schachBrett;
	
	public FenDecoder (Brett schachBrettFormat){
		
		schachBrett = schachBrettFormat;
	}
	
	public Brett decodiere(String fen){

		//String[] brett 
		 fenTeile =  fen.split(" ");//spaltet deb fen String
		String[] zeilen = fenTeile[0].split(trennzeichen);  // teillt 
		for(int y = 7; y>= 0 ; y--){  // laueft die String bloeck ab
			//int anzahlSymbole = zeilen[i].length();
			
			char[]  symbole = 	zeilen[7-y].toCharArray(); //Teilt die zeile in zeichen und spiegelt an der der y achse damit Weis unten bleibt
			int symbolPos = 0; //position im Char Array
			int x = 0; // position im Figuren array
			while(x < 8){  // durchlaueft die einzelne zeile
				//rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
				
				if( symbole[symbolPos] <122 && symbole[symbolPos] > 65){ //wenn buchstabe
					
					schachBrett.setInhalt(x, y , new Figur( symbole[symbolPos]) );
					
					x++;
				}else{
					int zahl = symbole[symbolPos]  - 48; //berechnete aud dem asci code die Zahl der leeren felder
					//if(zahl<1 || zahl > 8){Throw Exception} // Fehlerbehandlung???
					
					for(int i=0;i<zahl;i++){ //loescht  eventuel belegete Felder
						schachBrett.setEmpty(x, y);
						x++;
					}
					//x = x + zahl; 
				}	
				symbolPos++;	
			}//while
		}
		
		return schachBrett;
	}//ermittler

/**
 * 
 * @return gibt die psition auf die per doppelzug gezogen worden ist
 */
	public SchachPosition getEnPassant(){
		SchachPosition rueckgabe;
		if(fenTeile[3].length() == 2 ){ // Wenn eine gueltige rochade auf dem Feld . fenTeile[3].equals("-")

			rueckgabe = new SchachPosition();
			rueckgabe.setX( fenTeile[3].charAt(0) ); // setX(char ) 
			rueckgabe.setY( (char) (fenTeile[3].charAt(1) - 1 ) ); // -1 um es auf von dem Feld 1-8 auf 0-7 anzupassen
			
		}else{
			rueckgabe = new SchachPosition(-2,-2);
		}
		return rueckgabe ;
	}
	public String getRochaden(){
		
		return fenTeile[2];
	}
	public boolean getIstWeisAmZug(){
		if(fenTeile[1].equals("w")){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	
	public String codiererNeuenZug(Brett brett){
			return codiererNeuenZug(brett, "-",null); // "-" setzt fuer jeden zug das EnPassant auf nicht moeglich
	}
	public String codiererNeuenZug(Brett brett, String rochade){
		return codiererNeuenZug(brett, "-", rochade);
	}
	public String codiererNeuenZugEnpassant(Brett brett, SchachPosition enPassant){
		String neuesEnPassant; 
		
		neuesEnPassant = enPassant.getCharX() +""+ enPassant.getY();
		
		return codiererNeuenZug(brett, neuesEnPassant,null);
	}
	/**
	 *  Verarbeitet  die Parrameter zu einen neuen Zug/Fen
	 * @param brett
	 * @param neuEnPassant
	 * @param neuRochade
	 * @return
	 */
	private String codiererNeuenZug(Brett brett, String neuEnPassant, String neuRochade){
			
			String rochade = fenTeile[2];
			String enPassant = fenTeile[3];
			int zugnummer  = Integer.parseInt(fenTeile[5]);
			int halbzuege = Integer.parseInt(fenTeile[4]) + 1;  // zaehlt um 1 hoch
			String amZug = fenTeile[1];
			String positionen = arrayFenWandler(brett);
				
			if( amZug.equals("b")){ // wenn schwarz am zug
				amZug = "w"; //wechsel amZug farbe auf weis
				zugnummer++; // zaehlt die zufnummer hoch 
			}else{ // sonst ist weis am zug
				amZug = "b"; //wechsel amZug farbe auf schwarz
				
			}
			
			if(neuEnPassant != null){// schreibt fall vorhanden 
				enPassant = neuEnPassant;
			}
			if( neuRochade != null ){// schreibt fall vorhanden 
				rochade = neuRochade;
			}
			
			
			
			
		return positionen +" "+  amZug +" "+ rochade +" "+  enPassant +" "+  halbzuege+" "+  zugnummer;
	}//codierung
	
	/**
	 * Wandelt ein 2dim Figure array in die 1 Gruppe vom Fen String um
	 * @param brett
	 * @return
	 */
	private String arrayFenWandler(Brett brett){
		StringBuffer rueckgabe = new StringBuffer();
		char letztesZeichen = '?'; //  ? == dummer inizial wert der in den schleifen rausgefiltert wird  
		for(int y = 7; y >= 0 ; y--){ //Zeilen
			
			for(int x = 0; x < 8 ; x++){ //spalten
				
				if(brett.getIsEmpty(x, y) == false){ // Wenn istFigur	(<-nicht leer)
					
						
						if(letztesZeichen != '?'){ //filterung des dummes Zeichen  das nicht in den String reingeschoben werden soll
							rueckgabe.append( letztesZeichen ); // schiebt letztes Zeichen  in den String
						}
						
						
						letztesZeichen =  brett.getInhalt(x, y).toChar();
				}else{  //sonst leeres Feld
					if( letztesZeichen >= '0' && letztesZeichen <= '9'){ // Wenn letztes Zeiche eine Zahl
						letztesZeichen++;
					}else{
						if(letztesZeichen != '?'){ //filterung des dummes Zeichen  das nicht in den String reingeschoben werden soll
							rueckgabe.append( letztesZeichen ); // schiebt letztes Zeichen  in den String
						}
						letztesZeichen = '1';
					}
					
				}//sonst zahl
				
			}
			rueckgabe.append( letztesZeichen ); // schiebt letztes Zeichen  in den String
			letztesZeichen = '/'; 
		}
		return rueckgabe.toString();
		
	}
	public String toString(){
		
		return fenTeile[0]+" "+fenTeile[1]+" "+ fenTeile[2] +" "+  fenTeile[3] +" "+  fenTeile[4]+" "+  fenTeile[5];
	}
	
}//class



















