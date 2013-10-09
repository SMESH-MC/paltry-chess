package chessengine.tools;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.List;
import java.io.*;

import chessengine.movegenerator.MoveGeneratorInterface;
import chessengine.movegenerator.MoveGenerator;
import chessengine.figurbewertung.ZugGeneratorPhilip;
import chessengine.*;
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
	private int hochzeahlen ;
	private char w ;
	private char b ;
	private boolean farbwechsel ;
	private boolean ishochzeahlen;
	
	public static void main (String[] args){

		 ZuggeneratorTest test = new ZuggeneratorTest();
		 test.testlauf();
	}
	
	public void testlauf(){
		bericht = new StringBuffer();
		errorLog = new StringBuffer();

		generator = new MoveGenerator();
	    //generator = new ZugGeneratorPhilip();
		farbwechsel = false;
		ishochzeahlen = false;

		teste("Testlauf Bauer Weis: ","pawnwhite.txt");
		teste("Testlauf Bauer black:","pawnblack.txt");

		teste("Testlauf Knight Weis:","knightwhite.txt");
		
		teste("Testlauf Knight Black:","knightblack.txt");
		teste("Testlauf Bishop Weis:","bishopwhite.txt");
		teste("Testlauf Bishop black:","bishopblack.txt");
		teste("Testlauf Rook white:","rookwhite.txt");
		teste("Testlauf Rook black:","rookblack.txt");
		teste("Testlauf Queen white:","queenwhite.txt");
		teste("Testlauf Queen black:","queenblack.txt");
		
		teste("Testlauf Rochade+Koenig b:","rochadeblack.txt");
		teste("Testlauf Rochade+Koenig b1:","rochadeblack1.txt");
		teste("Testlauf Rochade+Koenig b2:","rochadeblack2.txt");
		teste("Testlauf Rochade+Koenig b3:","rochadeblack3.txt");
		teste("Testlauf Rochade+Koenig b4:","rochadeblack4.txt");
		teste("Testlauf Rochade+Koenig w:","rochadewhite.txt");
		teste("Testlauf Rochade+Koenig w1:","rochadewhite1.txt");
		teste("Testlauf Rochade+Koenig w2:","rochadewhite2.txt");
		teste("Testlauf Rochade+Koenig w3:","rochadewhite3.txt");
		teste("Testlauf Rochade+Koenig w4:","rochadewhite4.txt");
		
		
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
		  //System.err.printf(""+e+"\n");                 
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
                            new FileReader(filename));
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
		        System.err.println( "cat: Fehler beim Verarbeiten von " + filename );
		        System.exit( 1 );
		      }

		
		return protokolle;
	}
	
	
	public boolean testDurchlauf(LinkedList<ProtokollSegment> protokoll, String text){
		String fen="";
		fen = protokoll.pop().getFen();
		
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
				errorLog.append("Fehler in "+text+":  "+suchFen+":"+meldung+" \n");
			}
		}
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			//errorLog.append("zuviele moeglichkeiten:"+ergebnis.toString());
		}
		
		return testErgebnis;
	}
	
	
	
}
