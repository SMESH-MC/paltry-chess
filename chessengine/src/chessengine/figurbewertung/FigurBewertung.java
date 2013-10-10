package chessengine.figurbewertung;
/**  
* @author Philip Hunsicker
* Stand : 25.09.2013
*/
import chessengine.tools.*;

import java.util.LinkedList;
import java.util.Stack;

public class FigurBewertung implements FigurBewertungInterface  {
	
	private final static int KING_DEFAULT 	=100000;
	private final static int QUEEN_DEFAULT 	=900;
	private final static int BISHOP_DEFAULT =300;
	private final static int KNIGHT_DEFAULT =300;
	private final static int ROOK_DEFAULT 	=500;
	private final static int PAWN_DEFAULT 	=100;
	
	private final static byte KING 	= 	6;
	private final static byte QUEEN = 	5;
	private final static byte BISHOP=	4;
	private final static byte KNIGHT=	3;
	private final static byte ROOK	=	2;
	private final static byte PAWN	=	1;
	
	private King king;
	private Queen queen;
	private Bishop bishop;
	private Knight knight;
	private Rook rook;
	private Pawn pawn;
	private Brett schachBrett;
	
	private LinienLaeufer linienLaeufer;
	private FenDecoder decoder;
	

	public FigurBewertung(){
		this(  QUEEN_DEFAULT, BISHOP_DEFAULT , KNIGHT_DEFAULT, ROOK_DEFAULT);
	}
	public FigurBewertung( int bewertungQueen, int bewertungRook, int bewertungBishop, int bewertungKnight){
		
		 //Brett brett = new Array2Dim();
		 Brett brett = new X88();
		
		 decoder = new FenDecoder(brett);
		 linienLaeufer = new LinienLaeufer(); // Eine Klasse die Zugeneration von mehrenKlassen uebernimmt
		 
		 
		 bishop = new Bishop( bewertungBishop, linienLaeufer );
		 knight = new Knight( bewertungKnight, linienLaeufer );
		 rook = new Rook( bewertungRook , linienLaeufer);
		 pawn = new Pawn( PAWN_DEFAULT , linienLaeufer);
		 queen = new Queen( bewertungQueen,  bishop, rook);
		 king = new King( KING_DEFAULT, linienLaeufer, knight.getMuster(), rook.getMuster(), bishop.getMuster() );
	}

	/**
	 *Methoder die Alle Figuren mit einem Spielfeld versorgt auf grunde dessen es das Zuege berechnet
	 */
	public void inizialisiereBrett(String fen){
		
		
		this.schachBrett = decoder.decodiere(fen);
		linienLaeufer.inizialisiere(schachBrett, decoder);
		//linienLaeufer.inizialisiere(schachBrett);
		king.inizialisiere(schachBrett, decoder.getRochaden());
		//queen.inizialisiere(schachBrett); wird nicht benoetig das diese nur rook und bishop aufruft
		//bishop.inizialisiere(schachBrett); // wird nicht benoetig da diese nur linienlaeufer/musterlaeufer aufrufen
		//knight.inizialisiere(schachBrett);
		rook.inizialisiere( decoder.getRochaden());

		pawn.inizialisiere(schachBrett, decoder.getEnPassant());	
		
	}
	/**
	 * @param position die Position von der Figure desen Zuegen ermittlet werden soll (x=0 unten | y = 0  links| Weis ist unten
	 * @return liefert Zuege CHO CHO CHO CHO CHO CHO CHO CHO als Linked List
	 */
	public  LinkedList<String> ermittleZuege(SchachPosition position) {
		LinkedList<String> rueckgabe = new LinkedList<String>();
		Figur figur 	= schachBrett.getInhalt(position);
		byte typ 		= figur.getZahlModulo();//ho
		boolean istWeis = figur.istWeis();
		
		switch ( typ )
	    {
	      	  case PAWN:
	      		  rueckgabe = pawn.ermittleZuege(position, istWeis);
		        break;
		      case ROOK:
		    	  rueckgabe = rook.ermittleZuege(position, istWeis);
		        break;
		      case KNIGHT:
		    	  rueckgabe = knight.ermittleZuege(position, istWeis);
		        break;
		      case BISHOP:
		    	  rueckgabe = bishop.ermittleZuege(position, istWeis);
		    	break;
			  case QUEEN:
				  rueckgabe = queen.ermittleZuege(position, istWeis);
			    break;
			  case KING:
				  rueckgabe = king.ermittleZuege(position, istWeis);
			    break;					  
			  default: 
		}//switch
		return rueckgabe;	
	} //ermittle
	/**
	 *  Ermittle alle zuege fuer die farbe die gerade am Zug ist
	 * @param fen der aktuellen ausgangstellung
	 * @return list Alle Zuege die von diese Stellung ausgefuehrt werden koennen
	 */
		public  LinkedList<String> ermittleAlleZuege(String fen) {
			LinkedList<String> rueckgabe = new LinkedList<String>();
			SchachPosition position = new SchachPosition();
			inizialisiereBrett(fen);
			boolean istWeisAmZug = decoder.getIstWeisAmZug() ;
			
			

			
			for(int y =0 ;y <8;y++)
			{
				for (int x =0; x < 8 ; x++ ){
					position.setXY(x, y);
					if( schachBrett.getIsEmpty(x, y) == false){//ein figur auf dem feld
						if( istWeisAmZug == schachBrett.getInhalt(x, y).istWeis() ){ // Wenn Figur auf Feld die gleiche Farbe hat wie der der am Zug ist
							rueckgabe.addAll( ermittleZuege(position) );
							
						}
						
					}
				}//for spalten
			}//for zeilen
			
			/*
			if(king.istBedroht(sucheKoenig(istWeisAmZug), istWeisAmZug)){ //sucht den koenig und prueft ob er bedroht ist

				rueckgabe = loescheBedrohteZuege(rueckgabe, istWeisAmZug); //Filtert zuege Raus die die Bedrohung nicht aufheben
			}
			*/
			return rueckgabe;
				
			
	} //ermittle Alle
		/** Loescht alle Zuege aus der Linked List in der der Koenig immernoch Bedroht ist
		 * 
		 * @param liste
		 * @param istWeisAmZug
		 * @return list ohne bedrote zuege
		 */
	private LinkedList<String> loescheBedrohteZuege(LinkedList<String> liste, boolean istWeisAmZug) {
		LinkedList<String> modifierteListe = new LinkedList<String>();
		String lokalerFen;
		while(!liste.isEmpty()){
			lokalerFen = liste.pop();
			inizialisiereBrett(lokalerFen); 
			if(!king.istBedroht(sucheKoenig(istWeisAmZug), istWeisAmZug)){ // Wenn koenig nach Zug nicht bedroht
				modifierteListe.add(lokalerFen);
			}
			///loesch bedrohte zuege 
		}
		return modifierteListe;
	}

	/**
	 *  Sucht die Position des Koenigs
	 * @return
	 */
	private SchachPosition sucheKoenig(boolean istWeisAmZug) {
		SchachPosition koenig = new SchachPosition();
		Figur figur ;
		for(int y =0 ;y <8;y++)
		{
			for (int x =0; x < 8 ; x++ ){
				
				if(schachBrett.getIsEmpty(x, y) == false){//ein figur auf dem feld
					figur = schachBrett.getInhalt(x,y);
					if( istWeisAmZug == figur.istWeis()  ){ // Wenn Figur auf Feld die gleiche Farbe hat wie der der am Zug ist
						if(figur.istKing()){
							koenig.setXY(x, y);
						}
					}
					
				}
			}//for spalten
		}//for zeilen
		return koenig;
	}


	/* (non-Javadoc)
	 * @see figurbewertung.FigurBewewertung#getBewertung(char)
	 */
	public int getBewertung (char typ){
		int bewertung = 0;
		switch ( typ )
	    {
	      	  case PAWN:
	      		bewertung = pawn.getBewertung();
		        break;
		      case ROOK:
		    	  bewertung = rook.getBewertung();
		        break;
		      case KNIGHT:
		    	  bewertung = knight.getBewertung();
		        break;
		      case BISHOP:
		    	  bewertung = bishop.getBewertung();
		    	break;
			  case QUEEN:
				  bewertung = queen.getBewertung();
			    break;
			  case KING:
				  bewertung = king.getBewertung();
			    break;					  
			  default: 
		}//switch
		return bewertung;
		
	} // getBewertung*/
	/**
	 * liefert die Bewegungsmuster  fuer einfache Bewegungen
	 * KEINE SPEZIAL FAELLER
	 * KEIE BAUER
	 * KEINE DAME (da dame = Turm + laeufer)
	 * @param typ figur : qbrnpk
	 * @return Stack mit Bewegungsmuster
	 */
	public Stack<SchachPosition> getMuster (char typ){
		Stack<SchachPosition> muster = null;
		switch ( typ )
	    {
	      	  case PAWN:
	      		  muster = null;
		        break;
		      case ROOK:
		    	  muster = rook.getMuster();
		        break;
		      case KNIGHT:
		    	  muster = knight.getMuster();
		        break;
		      case BISHOP:
		    	  muster = bishop.getMuster();
		    	break;
			  case QUEEN:
				  muster = null ;
			    break;
			  case KING:
				  muster = king.getMuster();
			    break;					  
			  default: 
		}//switch
		return muster;
		
	} // getBewertung

	public static int getKING_DEFAULT() {
		return KING_DEFAULT;
	}

	public static int getQUEEN_DEFAULT() {
		return QUEEN_DEFAULT;
	}

	public static int getBISHOP_DEFAULT() {
		return BISHOP_DEFAULT;
	}

	public static int getKNIGHT_DEFAULT() {
		return KNIGHT_DEFAULT;
	}

	public static int getROOK_DEFAULT() {
		return ROOK_DEFAULT;
	}

	public static int getPAWN_DEFAULT() {
		return PAWN_DEFAULT;
	}
	public  int getKingBewertung() {
		return king.getBewertung();
	}

	public  int getQueenBewertung() {
		return queen.getBewertung();
	}

	public  int getBishopBewertung() {
		return bishop.getBewertung();
	}

	public  int getKnightBewertung() {
		return knight.getBewertung();
	}

	public  int getRookBewertung() {
		return rook.getBewertung();
	}

	public  int getPawnBewertung() {
		return pawn.getBewertung();
	}


	public  void setQueenBewertung(int bewertung) {
		queen.setBewertung(bewertung);
	}

	public  void setBishopBewertung(int bewertung) {
		 bishop.setBewertung(bewertung);
	}

	public  void setKnightBewertung(int bewertung) {
		 knight.setBewertung(bewertung);
	}

	public  void setRookBewertung(int bewertung) {
		 rook.setBewertung(bewertung);
	}


	
}
