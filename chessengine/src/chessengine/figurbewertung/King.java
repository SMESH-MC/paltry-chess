package chessengine.figurbewertung;

import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.Figur;
import chessengine.tools.SchachPosition;

public class King extends OberFigur{
	
	private LinienLaeufer linienlaeufer;
	//private String rochade;
	char[] rochadeCharArray;
	private Figur[][] schachBrett;
	private final static char[] ROCHADEOPTION = new char[4];
	
	//Koenigspostionen
	/*private final static SchachPosition SCHWARZKURZ = new SchachPosition(2,2);;
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(2,2);;
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(2,2);;
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(2,2);*/
	//Turm Start und Ziel Position fuer Rochade
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(0,7);;
	private final static SchachPosition SCHWARZKURZZIEL = new SchachPosition(2,7); ;
	private final static SchachPosition SCHWARZLANGSTART = new SchachPosition(7,7);;
	private final static SchachPosition SCHWARZLANGZIEL = new SchachPosition(4,7);;
	private final static SchachPosition WEISKURZSTART = new SchachPosition(7,0);;
	private final static SchachPosition WEISKURZZIEL = new SchachPosition(5,0);;
	private final static SchachPosition WEISLANGSTART = new SchachPosition(0,0);;
	private final static SchachPosition WEISLANGZIEL = new SchachPosition(3,0);;
	
	
	
	public King(int bewertung, LinienLaeufer linienlaeufer) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
		ROCHADEOPTION[0] = 'K';
		ROCHADEOPTION[1] = 'Q';
		ROCHADEOPTION[2] = 'k';
		ROCHADEOPTION[3] = 'q';
	
		
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		LinkedList<String> rueckgabe = new LinkedList<String>();
		String neueRochade = entferneRochade(istWeis); // loescht die rochade moeglichkeiten der farbe des koenigs da diese nach einen koenigst zug nicht mehr erlabut sind
		
		int zeiger = 0; //zeigt auf die stelle in den ROCHADEOPTION 

		rueckgabe.addAll( linienlaeufer.ermittleZuege( position,  istWeis ,  getMuster(),  1, neueRochade) );// hollt alle standartzuege
		// ermittle Rochade zuege \/\/\/\/
		
		if(rochadeCharArray[0] != '-') //moeglich rochade ueberhaupt vorhanden
		{
			
			for(int i = 0 ; i < this.rochadeCharArray.length ; i++){
				//neueRochade = entferneRochade(i); // erstellt den String fuer den neuen FEN fuer den fall das ein rochade ausgefuhrt wird
				while( ROCHADEOPTION[zeiger] != rochadeCharArray[i]){ //geht solange durch die moeglich rochaden bis eine gueltige gefunden wurden ist
					zeiger++;
				}
				if(istWeis){//wenn weis
					if(zeiger == 0 && schachBrett[5][0] == null && schachBrett[6][0] == null ){ // wenn Kurze Rochade Weis und stell zwischendrin frei
						
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 6,0, position , WEISKURZSTART, WEISKURZZIEL , neueRochade) ) ;
					}
					if(zeiger == 1 && schachBrett[1][0] == null && schachBrett[2][0] == null && schachBrett[3][0] == null){//Lange Rochade Weis
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 2,0, position , WEISLANGSTART, WEISLANGZIEL , neueRochade ));
					}
				}else{//sonst schwarz
					if(zeiger == 2 && schachBrett[1][7] == null && schachBrett[2][7] == null){//Kurze Rochade Schwarz
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 1,7, position , SCHWARZKURZSTART, SCHWARZKURZZIEL, neueRochade));
					}
					if(zeiger == 3 && schachBrett[4][7] == null && schachBrett[5][7] == null && schachBrett[6][7] == null){//Lange Rochade Schwarz
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 5,7, position , SCHWARZLANGSTART, SCHWARZLANGZIEL, neueRochade));
					}
				}//sonst schwarz
				zeiger ++; // damit der zeiger gleichzeit mit dem i ingrementiert wird (++ halt) erspart eine schleifendurchlauf
			}//for 
		}//wenn mindest 1 rochade moeglich
		
		
		
		
		
		return rueckgabe;
	}
	/**
	 * loesch alle eigenen Rochade moeglich raus und speicher die rochade moeglichkeiten des gegespieler
	 * @param istWeis
	 * @return rochademoeglichkeiten
	 */
	
	public String entferneRochade(boolean istWeis){
		StringBuffer neueRochade = new StringBuffer();
		int zeiger = 0;
		if(rochadeCharArray[0] != '-') //moeglich rochade ueberhaupt vorhanden
		{
			
			for(int i = 0 ; i < this.rochadeCharArray.length ; i++){
				//neueRochade = entferneRochade(i); // erstellt den String fuer den neuen FEN fuer den fall das ein rochade ausgefuhrt wird
				while( ROCHADEOPTION[zeiger] != rochadeCharArray[i]){ //geht solange durch die moeglich rochaden bis eine gueltige gefunden wurden ist
					zeiger++;
				}
				if(istWeis){//wenn weis
					if(zeiger == 2  ){ // wenn Kurze Rochade Weis und stell zwischendrin frei
						neueRochade.append(ROCHADEOPTION[zeiger]);
					}
					if(zeiger == 3 ){//Lange Rochade Weis
						neueRochade.append(ROCHADEOPTION[zeiger]);
					}
				}else{//sonst schwarz
					if(zeiger == 0 ){//Kurze Rochade Schwarz
						neueRochade.append(ROCHADEOPTION[zeiger]);	
					}
					if(zeiger == 1 ){//Lange Rochade Schwarz
						neueRochade.append(ROCHADEOPTION[zeiger]);
					}
				}//sonst schwarz
				zeiger ++; // damit der zeiger gleichzeit mit dem i ingrementiert wird (++ halt) erspart eine schleifendurchlauf
			}//for 
		}//wenn mindest 1 rochade moeglich
		

		
		return neueRochade.toString();
	}
	public void inizialisiere(Figur[][] schachBrett, String rochade) {
		this.schachBrett = schachBrett;
		rochadeCharArray = rochade.toCharArray();
	}

	public Stack<SchachPosition> getMuster() {
		/* könnte von Rook und Bishop uebernommen werden.
		 * 
		 */
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		bewegungsMuster.push( new SchachPosition(0 , 1) ) ; //nach oben
		bewegungsMuster.push( new SchachPosition(0 , -1) ) ; // nach unten
		bewegungsMuster.push( new SchachPosition(1 , 0) ) ; //nach rechts
		bewegungsMuster.push( new SchachPosition(-1 , 0) ) ; //nach links
		bewegungsMuster.push( new SchachPosition(1 , 1 ) ); //Diagonal oben rechts
		bewegungsMuster.push( new SchachPosition(1 , -1) ) ; // Diagonal unten rechts
		bewegungsMuster.push( new SchachPosition(-1 , 1) ) ; //Diagonal oben links
		bewegungsMuster.push( new SchachPosition(-1 , -1) ) ; //Diagonal unten links
		return bewegungsMuster;
	}

	
}
