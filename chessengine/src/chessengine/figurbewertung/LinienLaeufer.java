package chessengine.figurbewertung;

import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.FenDecoder;
import chessengine.tools.Figur;
import chessengine.tools.SchachPosition;

/**
 * stand 05.08.2013  
 * erzeugt zugmöglichkeiten für klassische linieneinheiten wie Läufer und Turm und König
 * 
 * @author Huns
 *
 */
public class LinienLaeufer {

	protected Figur[][] schachBrett;
	protected Figur[][] neuesBrett;
	protected FenDecoder decoder;
	
	public LinienLaeufer() {

		// TODO Auto-generated constructor stub
	}
/**
 * 
 * @param position
 * @param istWeis
 * @param schachbrett
 * @param bewegungsMuster art die die Figure bewegt werden soll
 * @param reichWeite 1 für ein feld 2 für 2felder
 * @return
 */
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis, Stack<SchachPosition> bewegungsMuster, int reichWeite) {
		return ermittleZuege( position,  istWeis,  bewegungsMuster,  reichWeite, null);
	}
	public LinkedList<String> ermittleZuege(SchachPosition position, boolean istWeis, Stack<SchachPosition> bewegungsMuster, int reichWeite, String neueRochade) {
		
		LinkedList<String> moeglichkeiten = new LinkedList<String>();
		//zeiger.setXY(position);
		int x = position.getX();
		int y = position.getY();
		int weite;
		
		while( !bewegungsMuster.isEmpty()  ){//läuft alle Muster ab
			x =  position.getX(); //setze auf Ausgangsposition
			y =  position.getY();
			weite = 0;
			x = x + bewegungsMuster.peek().getX();
			y = y + bewegungsMuster.peek().getY();
			
			while(x < 8  && x >=0 && y >=0 && y < 8 && weite < reichWeite){ //Solange Rand nicht rand ereicht und reichweiter nich tueberschritten

				if( schachBrett[x][y] != null ){//Wenn eine Figur auf dem Feld
					
					if(istWeis != schachBrett[x][y].getIstWeis()){// Wenn feindlich figure
						
						moeglichkeiten.push( generiereFen(x, y, position , neueRochade) );//schlage //, schachBrett[x][y].getTyp()) typ der geschlagen figur
						
					}
					x=8; //in beiden fällen beende schleife
					
				}else{//else keine figure auf dem Feld
					
					moeglichkeiten.push( generiereFen(x, y, position , neueRochade) );
					x = x + bewegungsMuster.peek().getX();//bewege nach muster
					y = y + bewegungsMuster.peek().getY();
				}//else
				weite++ ; //weite +1 das nächstes Feld  geprüft wird. Diesen komentar ignorieren
				
			}//linie /Muster
			bewegungsMuster.pop(); // naechste muster bearbeiten
		}//richtungen
		

		return moeglichkeiten;
	}
	
	/**
	 *  inizialisiert das schachbrett fue alle darauffolgende aufrufe von ermittle zuege
	 * @param schachBrett
	 */
	public void inizialisiere(Figur[][] schachBrett, FenDecoder decoder) {
		this.schachBrett = schachBrett;
		this.decoder = decoder;
	}
	
	public String generiereFen(int x, int y, SchachPosition position, String neueRochade){

		neuesBrett = bewegeFigur(x, y ,position);
		
		return decoder.codiererNeuenZug(neuesBrett, neueRochade);
	}


	
	/**
	 * variations von genereFend spezialisiertfuer rochade
	 * @param x
	 * @param y
	 * @param position
	 * @param ausgangsPostionTurm
	 * @param zielDesTurms
	 * @return
	 */
	public  String generiereRochadenFen(int x, int y, SchachPosition position, SchachPosition ausgangsPostionTurm, SchachPosition zielDesTurms, String neueRochade){

		neuesBrett = bewegeFigur(x,y,position); // bewegt den koenig von position nach x y
		neuesBrett = bewegeFigur(zielDesTurms.getX(),zielDesTurms.getY(),ausgangsPostionTurm,neuesBrett);
		
		return decoder.codiererNeuenZug(neuesBrett, neueRochade);
	}
	/**
	 *  
	 * @param x
	 * @param y
	 * @param position
	 * @param feindlicherBauer
	 * @return
	 */
	public  String generiereEnPassantFenSchlage(int x, int y, SchachPosition position, SchachPosition feindlicherBauer){
		neuesBrett = bewegeFigur(x,y,position);
		neuesBrett[ feindlicherBauer.getX() ][ feindlicherBauer.getY() ] = null; // entfernt den geschlagen Bauer
		return decoder.codiererNeuenZug(neuesBrett);
	}
	/**
	 *  aender den FEN so als waere ein Bauer um 2 nach vorn gerueckt und speichert die SchlagPosition ala EnPassant in den FEN
	 * @param x
	 * @param y
	 * @param position
	 * @param schlagPosition
	 * @return
	 */
	public  String generiereEnPassantFenZiehe(int x, int y, SchachPosition position, SchachPosition schlagPosition){
		Figur figur = schachBrett[ position.getX() ][ position.getY()]; //speichert sich die alte Figur
		neuesBrett = copy(schachBrett);
		neuesBrett[ position.getX() ][ position.getY() ] = null; // setzt die alte position auf null
		neuesBrett[ x][ y ] = figur; // setzt die figur auf das neue Feld
			
		return decoder.codiererNeuenZugEnpassant(neuesBrett, schlagPosition);
	}
	private Figur[][] copy(Figur[][] brett){
		Figur[][] neuesBrett = new Figur[8][8];
		for(int y =0 ;y <8;y++)
		{
			for (int x =0; x < 8 ; x++ ){
					if( brett[x][y] != null){
						Figur neueFigur = new Figur( brett[x][y].getIstWeis() , brett[x][y].toChar());	
						neuesBrett[x][y] = neueFigur;
					}
				}
			}	
		
		return neuesBrett;
	}
	/**
	 * Erstellt einen neuese Brett und bewegt die ensprechende Figur auf die gewuenschte position 
	 * @param x Zielposition
	 * @param y Zielposition
	 * @param position  Start position
	 * @return 
	 */
	private Figur[][] bewegeFigur(int x, int y, SchachPosition position) {
		neuesBrett = copy(schachBrett);
		neuesBrett = bewegeFigur( x,  y,  position, neuesBrett); 
		return neuesBrett;
	}
	private Figur[][] bewegeFigur(int x, int y, SchachPosition position, Figur[][] neuesBrett) {
		Figur figur = schachBrett[ position.getX() ][ position.getY()];
		neuesBrett[ position.getX() ][ position.getY() ] = null; // setzt die alte position auf null
		neuesBrett[ x][ y ] = figur; // setzt die figur auf das neue Feld
		return neuesBrett;
	}
}//class
