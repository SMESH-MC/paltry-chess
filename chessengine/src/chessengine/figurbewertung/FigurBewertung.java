package chessengine.figurbewertung;

import chessengine.tools.*;
import java.util.LinkedList;

public class FigurBewertung {
	
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
		//rook.inizialisiere(schachBrett);

		pawn.inizialisiere(schachBrett, decoder.getEnPassant());	
		
	}
	/**
	 * 
	 * @param position die Position von der Figure desen Zuegen ermittlet werden soll (x=0 unten | y = 0  links| Weis ist unten
	 * @param schachbrett ein Schachbrett das mit dem FenDecoder genereriert worden ist
	 * @param rochade Welche Rochaden möglich sind
	 * @param enPassant ob ein querschlagen des Bauers moeglich ist
	 * @return liefert Zuege CHO CHO CHO CHO CHO CHO CHO CHO als Stack 
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
	/**
	 *  Ermittle alle zuege fuer die farbe die gerade am Zug ist
	 * @param fen der aktuellen ausgangstellung
	 * @return list Alle Zuege die von diese Stellung ausgefuehrt werden koennen
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
	
	/**
	 * q b n r p 
	 */
	public void setBewertung(int q, int b, int n, int r, int p){
		queen.setBewertung(q);
		bishop.setBewertung(b);
		knight.setBewertung(n);
		rook.setBewertung(r);
		pawn.setBewertung(p);
	}
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
	
}
