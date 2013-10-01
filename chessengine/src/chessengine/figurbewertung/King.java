package chessengine.figurbewertung;
/**  
* @author Philip Hunsicker
* Stand : 25.09.2013
*/
import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.Brett;
import chessengine.tools.Figur;
import chessengine.tools.SchachPosition;

public class King extends OberFigur{
	
	private LinienLaeufer linienlaeufer;
	//private String rochade;
	char[] rochadeCharArray;
	private Brett schachBrett;
	private final static char[] ROCHADEOPTION = new char[4];
	//Koenigspostionen
	/*private final static SchachPosition SCHWARZKURZ = new SchachPosition(2,2);;
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(2,2);;
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(2,2);;
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(2,2);*/
	//Turm Start und Ziel Position fuer Rochade
	private final static SchachPosition SCHWARZKURZSTART = new SchachPosition(7,7);;
	private final static SchachPosition SCHWARZKURZZIEL = new SchachPosition(5,7); ;
	private final static SchachPosition SCHWARZLANGSTART = new SchachPosition(0,7);;
	private final static SchachPosition SCHWARZLANGZIEL = new SchachPosition(3,7);;
	private final static SchachPosition WEISKURZSTART = new SchachPosition(7,0);;
	private final static SchachPosition WEISKURZZIEL = new SchachPosition(5,0);;
	private final static SchachPosition WEISLANGSTART = new SchachPosition(0,0);;
	private final static SchachPosition WEISLANGZIEL = new SchachPosition(3,0);;
	
	private final static int KING 	= 	6;
	private final static int QUEEN = 	5;
	private final static int BISHOP=	4;
	private final static int KNIGHT=	3;
	private final static int ROOK	=	2;
	//private final static int PAWN	=	1;
	
	private final static SchachPosition[] MUSTER = new SchachPosition[8];
	private   SchachPosition[] knightMuster;
	private   SchachPosition[] rookMuster;
	private   SchachPosition[] bishopMuster;
	
	/*private final static SchachPosition OBEN = new SchachPosition(0 , 1)  ; //nach oben
	private final static SchachPosition UNTEN = new SchachPosition(0 , -1)  ; // nach unten
	private final static SchachPosition RECHTS =  new SchachPosition(1 , 0)  ; //nach rechts
	private final static SchachPosition LINKS = new SchachPosition(-1 , 0); //nach links
	private final static SchachPosition OBENRECHTS = new SchachPosition(1 , 1 ) ; //Diagonal oben rechts
	private final static SchachPosition UNTENRECHTS = new SchachPosition(1 , -1)  ; // Diagonal unten rechts
	private final static SchachPosition OBENLINKS = new SchachPosition(-1 , 1)  ; //Diagonal oben links
	private final static SchachPosition UNTENLINKS = new SchachPosition(-1 , -1)  ; //Diagonal unten links*/
	
	
	public King(int bewertung, LinienLaeufer linienlaeufer, Stack<SchachPosition> knightMuster , Stack<SchachPosition>rookMuster, Stack<SchachPosition>bishopMuster) {
		super(bewertung);
		this.linienlaeufer =  linienlaeufer;
		ROCHADEOPTION[0] = 'K';
		ROCHADEOPTION[1] = 'Q';
		ROCHADEOPTION[2] = 'k';
		ROCHADEOPTION[3] = 'q';
	
		 MUSTER[0] =  new SchachPosition(0 , 1)  ; //nach oben
		 MUSTER[1] =  new SchachPosition(0 , -1)  ; // nach unten
		 MUSTER[2] =  new SchachPosition(1 , 0)  ; //nach rechts
		 MUSTER[3] =  new SchachPosition(-1 , 0); //nach links
		 MUSTER[4] =  new SchachPosition(1 , 1 ) ; //Diagonal oben rechts
		 MUSTER[5] =  new SchachPosition(1 , -1)  ; // Diagonal unten rechts
		 MUSTER[6] =  new SchachPosition(-1 , 1)  ; //Diagonal oben links
		 MUSTER[7] =  new SchachPosition(-1 , -1)  ; //Diagonal unten links
		
		this.knightMuster = toArray(knightMuster); 
		this.rookMuster = toArray(rookMuster);
		this.bishopMuster = toArray(bishopMuster);
		 
	}

	@Override
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis) {
		LinkedList<String> rueckgabe = new LinkedList<String>();
		String neueRochade = entferneRochade(istWeis); // loescht die rochade moeglichkeiten der farbe des koenigs da diese nach einen koenigst zug nicht mehr erlabut sind
		
		int zeiger = 0; //zeigt auf die erste stelle in den ROCHADEOPTION 

		rueckgabe.addAll( linienlaeufer.ermittleZuege( position,  istWeis ,  getNichtBedrohteMuster(position, istWeis),  1, neueRochade) );// hollt alle standartzuege von Felder die nicht bedroht sind
		// ermittle Rochade zuege \/\/\/\/
		
		if(rochadeCharArray[0] != '-') //moeglich rochade ueberhaupt vorhanden
		{
			
			for(int i = 0 ; i < this.rochadeCharArray.length ; i++){
				//neueRochade = entferneRochade(i); // erstellt den String fuer den neuen FEN fuer den fall das ein rochade ausgefuhrt wird
				while( ROCHADEOPTION[zeiger] != rochadeCharArray[i]){ //geht solange durch die moeglich rochaden bis eine gueltige gefunden wurden ist
					zeiger++;
				}
				if(istWeis){//wenn weis
					if(zeiger == 0 && istKurzeWeiseRochade(istWeis) ){ // wenn Kurze Rochade Weis und stell zwischendrin frei
						
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 6,0, position , WEISKURZSTART, WEISKURZZIEL , neueRochade) ) ;
					}
					if(zeiger == 1 && istLangeWeiseRochade(istWeis)){//Lange Rochade Weis
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 2,0, position , WEISLANGSTART, WEISLANGZIEL , neueRochade ));
					}
				}else{//sonst schwarz
					if(zeiger == 2 && istKurzeSchwarzeRochade(istWeis)){//Kurze Rochade Schwarz
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 6,7, position , SCHWARZKURZSTART, SCHWARZKURZZIEL, neueRochade));
					}
					if(zeiger == 3 && istLangeSchwarzeRochade(istWeis)){//Lange Rochade Schwarz
						rueckgabe.push(linienlaeufer.generiereRochadenFen( 2,7, position , SCHWARZLANGSTART, SCHWARZLANGZIEL, neueRochade));
					}
				}//sonst schwarz
				zeiger ++; // damit der zeiger gleichzeit mit dem i ingrementiert wird (++ halt) erspart eine schleifendurchlauf
			}//for 
		}//wenn mindest 1 rochade moeglich
		
		
		
		
		
		return rueckgabe;
	}
	private boolean istKurzeWeiseRochade(boolean istWeis){
		return (schachBrett.getIsEmpty(5, 0) && schachBrett.getIsEmpty(6,0)
				&& !istBedroht(5,0,istWeis) && !istBedroht(6,0,istWeis) && !istBedroht(4,0,istWeis) );
	}
	private boolean istLangeWeiseRochade(boolean istWeis){
		return (schachBrett.getIsEmpty(1,0) && schachBrett.getIsEmpty(2,0) && schachBrett.getIsEmpty(3,0)
				&& !istBedroht(1,0,istWeis) && !istBedroht(2,0,istWeis) && !istBedroht(3,0,istWeis) && !istBedroht(4,0,istWeis));
	}
	private boolean istKurzeSchwarzeRochade(boolean istWeis){
		return (schachBrett.getIsEmpty(5,7) && schachBrett.getIsEmpty(6,7)
				&& !istBedroht(5,7,istWeis) && !istBedroht(6,7,istWeis) && !istBedroht(4,7,istWeis));
	}
	private boolean istLangeSchwarzeRochade(boolean istWeis){
		return (schachBrett.getIsEmpty(1,7)  && schachBrett.getIsEmpty(2,7) && schachBrett.getIsEmpty(3,7)
				&& !istBedroht(1,7,istWeis) && !istBedroht(2,7,istWeis) && !istBedroht(3,7,istWeis) && !istBedroht(4,7,istWeis));
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
	public void inizialisiere(Brett schachBrett2, String rochade) {
		this.schachBrett = schachBrett2;
		rochadeCharArray = rochade.toCharArray();
	}
	/**
	 * gibt nur die Muster zurück die auf einen nicht bedrohte feld sind
	 * @return
	 */
	
	public Stack<SchachPosition> getNichtBedrohteMuster(SchachPosition position, boolean istWeis) {
		/* könnte von Rook und Bishop uebernommen werden.
		 * 
		 */
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		SchachPosition zeiger = new SchachPosition(0 , 0);  
		Figur koenig;
		koenig = schachBrett.getInhalt(position); //zwischenspeich fuer den koenig
		schachBrett.setEmpty(position); //loescht den Koenig raus damit istBedroht sauber arbeitet
		for(int i = 0; i<8 ; i++){
			zeiger.setX(position.getX()); // setzt den Zeiger auf die pos vom koenig
			zeiger.setY(position.getY());
			if(zeiger.bewege(MUSTER[i])){ // bewegt den Zeiger true <- wenn bewget worden ist
				if(!istBedroht(zeiger, istWeis)){ //preuft ob dies  nicht Position bedroht ist
					bewegungsMuster.push( MUSTER[i] ) ;
				}
			}
		}
		schachBrett.setInhalt(position, koenig );//fuegt den koenig wieder ein den ohne koeenig ist doof
		return bewegungsMuster;
	}
	public Stack<SchachPosition> getMuster() {
		/* könnte von Rook und Bishop uebernommen werden.
		 * 
		 */
		Stack<SchachPosition> bewegungsMuster = new Stack<SchachPosition>(); // erstelle eine Reihe an bewegungsmuster
		
		for(int i = 0; i<8 ; i++){
			bewegungsMuster.push( MUSTER[i] ) ;
		}
		return bewegungsMuster;
	}
	/**
	 *  prueft  ob bedroht ist. liefert false wenn bedroht  einrifft
	 * @param position
	 * @return true wenn bedroht, .
	 */
	public boolean istBedroht(int x, int y, boolean istWeis){
		 SchachPosition position = new SchachPosition(x , y);  
		return istBedroht(position , istWeis);
	}//istBedrohte
	public boolean istBedroht(SchachPosition position, boolean istWeis){
		SchachPosition zeiger = new SchachPosition(0 , 0);  
		Figur figur;
		boolean istBedroht = false;
		Stack<Figur> figuren;
		//pruefe auf Koenig
		figuren = linienlaeufer.ermittleSchlaege(position, istWeis, MUSTER, 1);
		if( pruefeAuf(KING, figuren) && !istBedroht) {
			istBedroht = true;}
		//preufe auf Pferd
		figuren = linienlaeufer.ermittleSchlaege(position, istWeis, knightMuster, 1);
		if( pruefeAuf(KNIGHT, figuren)&& !istBedroht) {
			istBedroht = true;}
		//preufe auf Turm
		figuren = linienlaeufer.ermittleSchlaege(position, istWeis, rookMuster, 8);
		if( pruefeAuf(ROOK, figuren)&& !istBedroht) {
			istBedroht = true;}
		//preufe auf Laeufer
		figuren = linienlaeufer.ermittleSchlaege(position, istWeis, bishopMuster, 8);
		if( pruefeAuf(BISHOP, figuren)&& !istBedroht) {
			istBedroht = true;}
		//pruefe auf Dame
		figuren = linienlaeufer.ermittleSchlaege(position, istWeis, rookMuster, 8);
		if( pruefeAuf(QUEEN, figuren)&& !istBedroht) {
			istBedroht = true;}
		figuren = linienlaeufer.ermittleSchlaege(position, istWeis, bishopMuster, 8);
		if( pruefeAuf(QUEEN, figuren)&& !istBedroht) {
			istBedroht = true;}
		//preufe auf Bauer
			zeiger.setX(position.getX()); 
			zeiger.setY(position.getY());
			if(istWeis){ // Wenn Weis
				zeiger.bewege(1, 1);
				
				if(schachBrett.getIsEmpty(zeiger) == false){
					figur = schachBrett.getInhalt(zeiger);
					if(figur.istPawn()){
						if(figur.istBlack()){
							istBedroht = true;}
						}
				}
				zeiger.setX(position.getX()); 
				zeiger.setY(position.getY());
				zeiger.bewege(-1, 1);
				if(schachBrett.getIsEmpty(zeiger) == false){
					figur = schachBrett.getInhalt(zeiger);
					if(figur.istPawn()){
						if(figur.istBlack()){
							istBedroht = true;}
						}
				}
			}else{ //sonst schwarz
				zeiger.bewege(-1, -1);
				if(schachBrett.getIsEmpty(zeiger) == false){
					figur = schachBrett.getInhalt(zeiger);
					if(figur.istPawn()){
						if(figur.istWeis()){
							istBedroht = true;}
						}
				}
				zeiger.setX(position.getX()); 
				zeiger.setY(position.getY());
				zeiger.bewege(1, -1);
				if(schachBrett.getIsEmpty(zeiger) == false){
					figur = schachBrett.getInhalt(zeiger);
					if(figur.istPawn()){
						if(figur.istWeis()){
							istBedroht = true;}
						}
				}
			}
		return istBedroht;
	}//istBedrohte
	private boolean pruefeAuf (int figur, Stack<Figur> figuren){
		boolean istGetroffen = false;
		while(!figuren.isEmpty()){

			if(figuren.pop().getZahlModulo() == figur){
				istGetroffen = true;
				
			}
			
		}
		
		return istGetroffen;
	}//pruefeAuf
	private SchachPosition[] toArray (Stack<SchachPosition> muster){
		int size = muster.size();
		SchachPosition[] array =  new SchachPosition[size];
		for(int i  = 0 ; i < size; i++){
			array[i] = muster.pop();
		}
		return array;
	}//pruefeAuf
}

