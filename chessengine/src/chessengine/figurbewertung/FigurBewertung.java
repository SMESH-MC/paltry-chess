package chessengine.figurbewertung;

import chessengine.tools.*;

import java.util.LinkedList;
import java.util.Stack;

public class FigurBewertung implements FigurBewertungInterface {
	
	private final static int KING_DEFAULT 	=110000;// ITS OVER 9000!!!
	private final static int QUEEN_DEFAULT 	=900;
	private final static int BISHOP_DEFAULT =300;
	private final static int KNIGHT_DEFAULT =300;
	private final static int ROOK_DEFAULT 	=500;
	private final static int PAWN_DEFAULT 	=100;
	
	private final static char KING 	= 	'k' ;
	private final static char QUEEN = 	'q';
	private final static char BISHOP=	'b';
	private final static char KNIGHT=	'n';
	private final static char ROOK	=	'r';
	private final static char PAWN	=	'p';
	
	private King king;
	private Queen queen;
	private Bishop bishop;
	private Knight knight;
	private Rook rook;
	private Pawn pawn;
	private Figur[][] schachBrett;
	
	private LinienLaeufer linienLaeufer;
	private FenDecoder decoder;
	
	public FigurBewertung(){
		 
		 decoder = new FenDecoder();
		 linienLaeufer = new LinienLaeufer(); // Eine Klasse die Zugeneration von mehrenKlassen uebernimmt
		 
		 king = new King( KING_DEFAULT, linienLaeufer );
		 bishop = new Bishop( BISHOP_DEFAULT, linienLaeufer );
		 knight = new Knight( KNIGHT_DEFAULT, linienLaeufer );
		 rook = new Rook( ROOK_DEFAULT , linienLaeufer);
		 pawn = new Pawn( PAWN_DEFAULT , linienLaeufer);
		 queen = new Queen( QUEEN_DEFAULT,  bishop, rook);
	}

	/* (non-Javadoc)
	 * @see figurbewertung.FigurBewewertung#inizialisiereBrett(java.lang.String)
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
	/* (non-Javadoc)
	 * @see figurbewertung.FigurBewewertung#ermittleZuege(tools.SchachPosition)
	 */
	public  LinkedList<String> ermittleZuege(SchachPosition position) {
		LinkedList<String> rueckgabe = new LinkedList<String>();
		Figur figur 	= schachBrett[ position.getX() ][ position.getY() ];
		char typ 		= figur.getTyp();
		boolean istWeis = figur.getIstWeis();
		
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
	/* (non-Javadoc)
	 * @see figurbewertung.FigurBewewertung#ermittleAlleZuege(java.lang.String)
	 */
		public  LinkedList<String> ermittleAlleZuege(String fen) {
			LinkedList<String> rueckgabe = new LinkedList<String>();
			SchachPosition position = new SchachPosition();
			boolean istWeisAmZug = decoder.getIstWeisAmZug() ;
			
			inizialisiereBrett(fen);
			
			for(int y =0 ;y <8;y++)
			{
				for (int x =0; x < 8 ; x++ ){
					position.setXY(x, y);
					if(schachBrett[x][y] != null){//ein figur auf dem feld
						if( istWeisAmZug == schachBrett[x][y].getIstWeis() ){ // Wenn Figur auf Feld die gleiche Farbe hat wie der der am Zug ist
							rueckgabe.addAll( ermittleZuege(position) );
						}
						
					}
				}//for spalten
			}//for zeilen
			
			
			
			
		return rueckgabe;
	} //ermittle Alle
	
	/* (non-Javadoc)
	 * @see figurbewertung.FigurBewewertung#setBewertung(int, int, int, int, int)
	 */
	public void setBewertung(int q, int b, int n, int r, int p){
		queen.setBewertung(q);
		bishop.setBewertung(b);
		knight.setBewertung(n);
		rook.setBewertung(r);
		pawn.setBewertung(p);
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
		
	} // getBewertung
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

	
	/**
	 * addings @Dominik Erb
	 * @return Werte der Figuren
	 */
	public static int getPawnValue() {
		return PAWN_DEFAULT;
	}
	public static int getRookValue() {
		return ROOK_DEFAULT;
	}
	public static int getKnightValue() {
		return KING_DEFAULT;
	}
	public static int getBishopValue() {
		return BISHOP_DEFAULT;
	}
	public static int getQueenValue() {
		return QUEEN_DEFAULT;
	}
	
	
}
