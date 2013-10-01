package chessengine.figurbewertung;

import java.util.LinkedList;
import java.util.Stack;

import chessengine.tools.Brett;
import chessengine.tools.FenDecoder;
import chessengine.tools.Figur;
import chessengine.tools.SchachPosition;


/**
 *  erzeugt zugmöglichkeiten für klassische linieneinheiten wie Läufer und Turm und König
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
public class LinienLaeufer {

	protected Brett schachBrett;
	protected Brett neuesBrett;
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

				if( schachBrett.getIsEmpty(x, y) == false  ){//Wenn eine Figur auf dem Feld
					
					if(istWeis != schachBrett.getInhalt(x, y).getIstWeis()){// Wenn feindlich figure
						
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
	public void inizialisiere(Brett schachBrett, FenDecoder decoder) {
		this.schachBrett = schachBrett;
		this.decoder = decoder;
	}
	
	public String generiereFen(int x, int y, SchachPosition position, String neueRochade){

		neuesBrett = schachBrett.bewegeFigur(x, y ,position);
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

		neuesBrett = schachBrett.bewegeFigur(x,y,position); // bewegt den koenig von position nach x y
		neuesBrett = neuesBrett.bewegeFigurOhneKopie(zielDesTurms.getX(),zielDesTurms.getY(),ausgangsPostionTurm);
		
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
		neuesBrett = schachBrett.bewegeFigur(x,y,position);
		neuesBrett.setEmpty(feindlicherBauer); // entfernt den geschlagen Bauer
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
		neuesBrett = schachBrett.bewegeFigur(x,y,position);
		return decoder.codiererNeuenZugEnpassant(neuesBrett, schlagPosition);
	}
	
	//speziall fuer Koenig
	/** modizfiziert Kopie von ermittlerZuege
	 * laeuft die Muster ab und  liefert  angetroffen "schlagbaren" einheiten zurueckt
	 * @return Figure der anderen Farbe auf den "Linien/Mustern"
	 */
	public Stack<Figur> ermittleSchlaege(SchachPosition position, boolean istWeis, SchachPosition[] bewegungsMuster, int reichWeite) {
		int zeiger = 0;
		Figur figur ; // zwischenspeicher
		Stack<Figur> treffer = new Stack<Figur>();
		//zeiger.setXY(position);
		int x = position.getX();
		int y = position.getY();
		int weite;
		
		while( zeiger < bewegungsMuster.length  ){//läuft alle Muster ab
			x =  position.getX(); //setze auf Ausgangsposition
			y =  position.getY();
			weite = 0;
			x = x + bewegungsMuster[zeiger].getX();
			y = y + bewegungsMuster[zeiger].getY();
			while(x < 8  && x >=0 && y >=0 && y < 8 && weite < reichWeite){    //Solange Rand nicht rand ereicht und reichweiter nich tueberschritten

				if( schachBrett.getIsEmpty(x, y) == false ){//Wenn eine Figur auf dem Feld
					figur =  schachBrett.getInhalt(x, y);
					if(istWeis != figur.getIstWeis()){// Wenn feindlich figure
						treffer.add(figur);
					}
					x=8; //in beiden fällen beende schleife
					
				}else{//else keine figure auf dem Feld
					
					x = x + bewegungsMuster[zeiger].getX();//bewege nach muster
					y = y + bewegungsMuster[zeiger].getY();
				}//else
				weite++ ; //weite +1 das nächstes Feld  geprüft wird. Diesen komentar ignorieren
				
			}//linie /Muster
			zeiger++;// naechste muster bearbeiten
		}//richtungen
		

		return treffer;
	}
}//class
