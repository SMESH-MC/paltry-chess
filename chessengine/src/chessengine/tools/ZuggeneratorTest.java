package chessengine.tools;
import java.util.LinkedList;

import java.io.*;

import chessengine.movegenerator.MoveGeneratorInterface;
import chessengine.movegenerator.MoveGenerator;
import chessengine.figurbewertung.ZugGeneratorPhilip;
/**
 * 
 * @author Philip Hunsicker
 * @date 7.10.2013
 *
 */
public class ZuggeneratorTest {

	private static MoveGeneratorInterface generator;
	private StringBuffer errorLog = new StringBuffer();
	private StringBuffer bericht = new StringBuffer();
	
	private boolean farbwechsel = false ;
	private boolean ishochzeahlen = false;
	private boolean onlyFenAusgabe = true;
	
//	private String dropboxPfad = "E:\Programme\Dropbox\schachengine\Tests\";
	private String dropboxPfad = "E:/Programme/Dropbox/schachengine/Tests/";
	//private String dropboxPfad = "";
	public static void main (String[] args){

		 ZuggeneratorTest test = new ZuggeneratorTest();
		 test.testlauf();
	}
	
	public void testlauf(){
		bericht = new StringBuffer();
		errorLog = new StringBuffer();

		generator = new MoveGenerator();
	    //generator = new ZugGeneratorPhilip();

		//teste("Testlauf Bauer Weis: ","pawnwhite.txt");  //zuviel bauer zuwenig koenig??
		teste("Testlauf Bauer Weis: ","pawnwhite2.txt");

		//teste("Testlauf Bauer black:","pawnblack.txt"); // a 2 problem und der absturz ruiniert meinen test
		teste("Testlauf Bauer black2:","pawnblack2.txt");
		
		teste("Testlauf Knight Weis:","knightwhite.txt");
		teste("Testlauf Knight Black:","knightblack.txt");
		teste("Testlauf Bishop Weis:","bishopwhite.txt");
		teste("Testlauf Bishop black:","bishopblack.txt");
		teste("Testlauf Rook white:","rookwhite.txt");
		teste("Testlauf Rook black:","rookblack.txt");
		teste("Testlauf Queen white:","queenwhite.txt");
		teste("Testlauf Queen black:","queenblack.txt");
		
		teste("Testlauf Rochade+Koenig <<b>>:","rochadeblack.txt");
		teste("Testlauf Rochade+Koenig <<b1>>:","rochadeblack1.txt");
		teste("Testlauf Rochade+Koenig <<b2>>:","rochadeblack2.txt");
		teste("Testlauf Rochade+Koenig <<b3>>:","rochadeblack3.txt");
		teste("Testlauf Rochade+Koenig <<b4>>:","rochadeblack4.txt");
		teste("Testlauf Rochade+Koenig <<b5>>:","rochadeblack5.txt");
		teste("Testlauf Rochade+Koenig <<w>>:","rochadewhite.txt");
		teste("Testlauf Rochade+Koenig <<w1>>:","rochadewhite1.txt");
		teste("Testlauf Rochade+Koenig <<w2>>:","rochadewhite2.txt");
		teste("Testlauf Rochade+Koenig <<w3>>:","rochadewhite3.txt");
		teste("Testlauf Rochade+Koenig <<w4>>:","rochadewhite4.txt");
		teste("Testlauf Rochade+Koenig <<w5>>:","rochadewhite5.txt");
		
		System.out.println(bericht);
		System.out.println(errorLog);
	}
	public void teste(String text,String pfad){
		LinkedList<ProtokollSegment> protokoll;
		try
		{
			bericht.append("\n"+text+"\t ");
			protokoll = testProtokollEinlesen(pfad);
			
			
			bericht.append(testDurchlauf( protokoll ,text));
			
		}
		catch ( Exception e )
		{
		 bericht.append(" FEHLERHAFT ");
		 bericht.append(":"+e);
		  System.err.printf(""+e+"\n");                 
	 }
		

		
	}
	
	public LinkedList<ProtokollSegment> testProtokollEinlesen(String filename){
			String zeile = null;
			String ausgangsFen ;
			String meldung ="";
			String[] teile = null;
			String farbe ;
			int halbzuege;
			int zuege;
			LinkedList<ProtokollSegment> protokolle = new LinkedList<ProtokollSegment>();
		      try {
		            BufferedReader bIn = new BufferedReader(
                            new FileReader((dropboxPfad+filename)));
		            ausgangsFen= bIn.readLine();
		            while (( zeile = bIn.readLine()) != null){
		            	///////////////////////System.out.println(zeile);
		            	//chessengine.Board board = new Board();
		            	//board.FenDecode(zeile);
		            	teile = zeile.split(" ");
		            	
		            	halbzuege = Integer.parseInt(teile[4]);
		            	
		            	zuege =  Integer.parseInt(teile[5]) ;
		            	farbe = teile[1];
		            	
		            	if(!ishochzeahlen){
		            		halbzuege = halbzuege - 1;
		            		if(farbe.equals("w")){
		            			zuege = zuege-1;
		            		}
		            	}
		            	
		            	if(!farbwechsel){
		            		if(farbe.equals("w")){
		            			farbe = "b";
		            		}else{
		            			farbe = "w";
		            		}
		            	}
		            	int i = 6;
		            	meldung ="";
		            	while( i< teile.length){

		            		meldung = meldung + teile[i];
		            		i++;
		            	}
		            	////////////////////////////System.out.println(teile[0]+" "+farbe+" "+teile[2]+" "+teile[3]+" "+halbzuege+" "+zuege);
		            	ProtokollSegment seg = new  ProtokollSegment(teile[0]+" "+farbe+" "+teile[2]+" "+teile[3]+" "+halbzuege+" "+zuege,meldung);
		            	protokolle.push(seg);
		            }
		            protokolle.push( new  ProtokollSegment( ausgangsFen , ""));
		      }
		      catch ( IOException e ) {
		        System.err.println( "cat: Fehler beim Verarbeiten von " + filename +e);
		        System.exit( 1 );
		      }

		
		return protokolle;
	}
	
	
	public boolean testDurchlauf(LinkedList<ProtokollSegment> protokoll, String text){
		String fen="";
		fen = protokoll.pop().getFen();
		if(onlyFenAusgabe){
			errorLog.append("--------------------------" +text+" :"+fen+"     ---------------------------------\n");
			
		}else{
			errorLog.append("-------------------------" +text+" -----------------------------------  \n"+fenAusgabe(fen));
		}
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		String suchFen;
		String meldung;
		boolean testErgebnis = true;
		
		while( !protokoll.isEmpty()){
			
			suchFen = protokoll.peek().getFen();
			meldung = protokoll.pop().getMeldung();
			if(!  ergebnis.remove(suchFen  )){
				testErgebnis = false;
				
				if(onlyFenAusgabe){
					errorLog.append("   Fehlende Zug:  ("+meldung+") :"+suchFen+" \n");
				}else{
					errorLog.append("   Fehlende Zug:  ("+meldung+") :  \n"+ fenAusgabe(suchFen));
				}
				
			}
		}
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append("nicht aufgeloesete faelle:\n"+ aufloesen(ergebnis) );
		}
		
		return testErgebnis;
	}
	
	


	private String fenAusgabe(String fen){
		StringBuffer buffer = new StringBuffer();
		FenDecoder decoder = new FenDecoder(new X88());
		Brett ausgabe = decoder.decodiere(fen);
		buffer.append("\nX||0|1|2|3|4|5|6|7\n");
		buffer.append("-------------------\n");
		for(int i = 7; i >= 0 ;i--){
			buffer.append(i+"||");
			for(int k = 0 ; k < 8; k++){
				
				if(ausgabe.getIsEmpty(k, i) == false ){
				
				
					buffer.append( ausgabe.getInhalt(k, i) + "|");//\t
				}else{
				
					buffer.append(" |");
				
				}
			
			}
			buffer.append("\n");
		}
		
		
		buffer.append("-------------------\n");
		return buffer.toString();
	}
	private String aufloesen(LinkedList<String> ergebnis) {
		StringBuffer buffer = new StringBuffer();
		while(!ergebnis.isEmpty()){
			if(onlyFenAusgabe){
				buffer.append("        "+ergebnis.pop()+"\n");
			}else{
				buffer.append(fenAusgabe(ergebnis.pop()));
			}
		}
		
		return buffer.toString();
	}
}
