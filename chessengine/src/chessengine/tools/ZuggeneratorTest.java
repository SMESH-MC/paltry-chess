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
	private int hochzeahlen ;
	private char w ;
	private char b ;
	private boolean farbwechsel ;
	private boolean ishochzeahlen;
	
	public static void main (String[] args){
		generator = new MoveGenerator();
	    //generator = new ZugGeneratorPhilip();
		 
		 ZuggeneratorTest test = new ZuggeneratorTest();
		 test.testlauf();
	}
	
	public void testlauf(){
		StringBuffer bericht = new StringBuffer();
		LinkedList<ProtokollSegment> protokoll;
		errorLog = new StringBuffer();

		farbwechsel = false;
		ishochzeahlen = false;
		
		bericht.append("Testlauf Bauer Weis:\t ");
		protokoll = testProtokollEinlesen("pawnwhite.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Knight Weis:\t ");
		protokoll = testProtokollEinlesen("knightwhite.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Knight Black:\t ");
		protokoll = testProtokollEinlesen("knightblack.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Bishop Weis:\t ");
		protokoll = testProtokollEinlesen("bishopwhite.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Bishop black:\t ");
		protokoll = testProtokollEinlesen("bishopblack.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Rook white:\t ");
		protokoll = testProtokollEinlesen("rookwhite.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Rook black:\t ");
		protokoll = testProtokollEinlesen("rookblack.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Queen white:\t ");
		protokoll = testProtokollEinlesen("queenwhite.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Queen black:\t ");
		protokoll = testProtokollEinlesen("queenblack.txt");
		bericht.append(testDurchlauf( protokoll ));
		
		System.out.println(bericht);
		System.out.println(errorLog);
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
	
	
	public boolean testDurchlauf(LinkedList<ProtokollSegment> protokoll){
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
				errorLog.append("Fehler:  "+suchFen+":"+meldung+" \n");
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
