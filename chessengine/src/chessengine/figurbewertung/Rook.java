/**
 * 
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
package chessengine.figurbewertung;

import java.util.LinkedList;
import java.util.Stack;
import chessengine.tools.SchachPosition;

public class Rook extends OberFigur{

	//SchachPosition zeiger;
	private LinienLaeufer linienlaeufer;
	private char[]rochadeCharArray ;
	private final static char[] ROCHADEOPTION = new char[4];
	
	private final static SchachPosition SCHWARZERTURMLINKS = new SchachPosition(0,7);;
	private final static SchachPosition SCHWARZERTURMRECHTS = new SchachPosition(7,7); ;
	private final static SchachPosition WEISERTURMLINKS = new SchachPosition(0,0);;
	private final static SchachPosition WEISERTURMRECHTS = new SchachPosition(7,0);;
	
	public Rook(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
		ROCHADEOPTION[0] = 'K';
		ROCHADEOPTION[1] = 'Q';
		ROCHADEOPTION[2] = 'k';
		ROCHADEOPTION[3] = 'q';
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		String neueRochade = entferneRochade(istWeis, position) ;


		return linienlaeufer.ermittleZuege( position,  istWeis,  getMuster(),  8, neueRochade); //uebergebe das an ermittlezuege wo draus einen ZukkZukk baut
	}
	/** auf basis (kopiert) von der King klasse 
	 * loesch alle eigenen Rochade moeglich raus und speicher die rochade moeglichkeiten des gegespieler
	 * @param istWeis
	 * @return rochademoeglichkeiten
	 */
	public String entferneRochade(boolean istWeis, SchachPosition position){
		StringBuffer neueRochade = new StringBuffer();
		int zeiger = 0;
		if(rochadeCharArray[0] != '-') //moeglich rochade ueberhaupt vorhanden
		{
			
			for(int i = 0 ; i < this.rochadeCharArray.length ; i++){
				//neueRochade = entferneRochade(i); // erstellt den String fuer den neuen FEN fuer den fall das ein rochade ausgefuhrt wird
				while( ROCHADEOPTION[zeiger] != rochadeCharArray[i]){ //geht solange durch die moeglich rochaden bis eine gueltige gefunden wurden ist
					zeiger++;
				}
				
					if(zeiger == 0  && !WEISERTURMRECHTS.equals(position)){ // wenn Kurze Rochade Weis turm NICHT auf rechter startPosition
						neueRochade.append(ROCHADEOPTION[zeiger]);  //Dann fuege rochadeOption 
					}
					if(zeiger == 1  && !WEISERTURMLINKS.equals(position)){//Lange Rochade Weis turm NICHT auf linker startPosition
						neueRochade.append(ROCHADEOPTION[zeiger]);
					}
				
					if(zeiger == 2  && !SCHWARZERTURMRECHTS.equals(position)){//Kurze Rochade Schwarz turm NICHT auf rechter startPosition
						neueRochade.append(ROCHADEOPTION[zeiger]);	 
					}
					if(zeiger == 3  && !SCHWARZERTURMLINKS.equals(position)){//Lange Rochade Schwarz  turm NICHT auf linker startPosition
						neueRochade.append(ROCHADEOPTION[zeiger]);  
					}
				
				zeiger ++; // damit der zeiger gleichzeit mit dem i ingrementiert wird (++ halt) erspart eine schleifendurchlauf
			}//for 
		}//wenn mindest 1 rochade moeglich
		return neueRochade.toString();
	}//entferne
	public void inizialisiere( String rochade) {
		rochadeCharArray = rochade.toCharArray();
	}

	public Stack<SchachPosition> getMuster() {
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		bewegungsMuster.push(  new SchachPosition(0 , 1) ) ; //nach oben
		bewegungsMuster.push(  new SchachPosition(0 , -1) ) ; // nach unten
		bewegungsMuster.push(  new SchachPosition(1 , 0) ) ; //nach rechts
		bewegungsMuster.push(  new SchachPosition(-1 , 0) ) ; //nach links
		return bewegungsMuster;
	}

}
