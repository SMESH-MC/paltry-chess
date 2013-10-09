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
		//generator = new MoveGenerator();
	    generator = new ZugGeneratorPhilip();
		 
		 ZuggeneratorTest test = new ZuggeneratorTest();
		 test.testlauf();
	}
	
	public void testlauf(){
		StringBuffer bericht = new StringBuffer();
		LinkedList<String> protokoll;
		errorLog = new StringBuffer();
		hochzeahlen = 1;
		w = 'b';
		b = 'w';
		farbwechsel = true;
		ishochzeahlen = true;
		bericht.append("Testlauf Bauer Weis:\t ");
		//bericht.append( pawnWhiteTestLauf(hochzeahlen,'b') );
		
		protokoll = testProtokollEinlesen("pawnwhite.txt");

		bericht.append(testDurchlauf( protokoll ));
		
		bericht.append("\nTestlauf Knight black:\t ");
		bericht.append( knightBlackTestLauf(hochzeahlen,b) );
		bericht.append("\nTestlauf Knight Weis:\t ");
		bericht.append( knightWhiteTestLauf(hochzeahlen,w) );
		
		bericht.append("\nTestlauf Bishop Weis:\t ");
		//bericht.append( bishopWhiteTestLauf(hochzeahlen,w) );
		
		bericht.append("\nTestlauf Bishop black:\t ");
		//bericht.append( bishopBlackTestLauf(hochzeahlen,b) );
		
		bericht.append("\nTestlauf rook black:\t ");
		bericht.append( rookBlack(hochzeahlen,b) );
		
		bericht.append("\nTestlauf rook white:\t ");
		bericht.append( rookWhite(hochzeahlen,w) );
		
		
		
		
		
		System.out.println(bericht);
		System.out.println(errorLog);
	}
	public LinkedList<String> testProtokollEinlesen(String filename){
			String zeile = null;
			String ausgangsFen ;
			String[] teile = null;
			String farbe ;
			int halbzuege;
			int zuege;
			LinkedList<String> protokolle = new LinkedList<String>();
		      try {
		            BufferedReader bIn = new BufferedReader(
                            new FileReader(filename));
		            ausgangsFen= bIn.readLine();
		            while (( zeile = bIn.readLine()) != null){
		            	
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
		            	//System.out.println(halbzuege +" "+ zuege);
		            	
		            	protokolle.push(teile[0]+" "+farbe+" "+teile[2]+" "+teile[3]+" "+halbzuege+" "+zuege);
		            }
		            protokolle.push(ausgangsFen);
		      }
		      catch ( IOException e ) {
		        System.err.println( "cat: Fehler beim Verarbeiten von " + filename );
		        System.exit( 1 );
		      }

		
		return protokolle;
	}
	
	
	public boolean testDurchlauf(LinkedList<String> protokoll){
		String fen="";
		
		fen = protokoll.pop();
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		
		
		boolean testErgebnis = true;
		
		while( !protokoll.isEmpty()){
			
			if(!  ergebnis.remove(protokoll.pop()  )){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
		}
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append("zuviele moeglichkeiten:"+ergebnis.toString());
		}
		
		return testErgebnis;
	}
	
	
	
	public boolean test2(int zahl ,char farbe){
		String fen  =  "n7/8/2B1K3/1Q3R2/3n4/1P3B2/2N1P3/7n b KQkq f6 1 1";
		int nr = 1+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" "+nr;
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		if(!  ergebnis.remove((""+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		
		
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}

	public boolean queenWeis(int zahl ,char farbe){
		String fen  =  "1Q6/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r w KQkq - 4 5";
		int nr = 4+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" 5";
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		if(!  ergebnis.remove(("1Q6/5p2/2k4B/7b/8/BBB2Q1n/BQB5/BB5r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		
		if(!  ergebnis.remove(("1Q6/5p2/2k5/6Bb/8/BBB2Q1n/BQB5/BB5r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/5B2/BBB2Q1n/BQB5/BB5r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB1BQ1n/BQB5/BB5r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB2Q1n/BQBB4/BB5r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p1B/2k5/7b/8/BBB2Q1n/BQ6/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k3B1/7b/8/BBB2Q1n/BQ6/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/5B1b/8/BBB2Q1n/BQ6/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/4B3/BBB2Q1n/BQ6/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBBB1Q1n/BQ6/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB2Q1n/BQ6/BBBB3r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q3B2/5p2/2k5/7b/8/1BB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/4Bp2/2k5/7b/8/1BB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2kB4/7b/8/1BB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/2B4b/8/1BB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/1B6/1BB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5B2/2k5/7b/8/B1B2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k1B3/7b/8/B1B2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/3B3b/8/B1B2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/2B5/B1B2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/B7/B1B2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q5B/5p2/2k5/7b/8/BB3Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5pB1/2k5/7b/8/BB3Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k2B2/7b/8/BB3Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/4B2b/8/BB3Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/3B4/BB3Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BB3Q1n/BQB5/BBB1B2r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BB3Q1n/BQBB4/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/B6b/8/BB3Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/1B6/BB3Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7Q/8/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/6Q1/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB4n/BQB5/BBB4Q"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB4n/BQB3Q1/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2Q5/7b/8/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/3Q3b/8/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/4Q3/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB4n/BQB5/BBBQ3r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB4n/BQB1Q3/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5Q2/2k5/7b/8/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k2Q2/7b/8/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/5Q1b/8/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/5Q2/BBB4n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB4n/BQB5/BBB2Q1r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB4n/BQB2Q2/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB4Q/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB3Qn/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBBQ3n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1Q6/5p2/2k5/7b/8/BBB1Q2n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/2k5/7b/8/BBB2Q1n/BQB4Q/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/2k5/7b/8/BBB2QQn/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/2k5/7b/5Q2/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/2k5/4Q2b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/2kQ4/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/2Q2p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/Q4p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/2k5/7b/1Q6/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/2k5/1Q5b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5p2/1Qk5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/1Q3p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("7Q/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("6Q1/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("5Q2/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("4Q3/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("3Q4/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("2Q5/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("Q7/5p2/2k5/7b/8/BBB2Q1n/BQB5/BBB4r"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}
	
	public boolean queenSchwarz(int zahl ,char farbe){
		String fen  =  "1q6/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R b KQkq - 5 5";
		int nr = 1+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" "+nr;
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		if(!  ergebnis.remove(("1q6/5P2/2K4b/7B/8/bbb2q1N/bqb5/bb5R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		
		if(!  ergebnis.remove(("1q6/5P2/2K5/6bB/8/bbb2q1N/bqb5/bb5R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/5b2/bbb2q1N/bqb5/bb5R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb1bq1N/bqb5/bb5R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P1b/2K5/7B/8/bbb2q1N/bq6/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K3b1/7B/8/bbb2q1N/bq6/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/5b1B/8/bbb2q1N/bq6/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/4b3/bbb2q1N/bq6/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbbb1q1N/bq6/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb2q1N/bq6/bbbb3R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q3b2/5P2/2K5/7B/8/1bb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/4bP2/2K5/7B/8/1bb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2Kb4/7B/8/1bb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/2b4B/8/1bb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/1b6/1bb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5b2/2K5/7B/8/b1b2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K1b3/7B/8/b1b2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/3b3B/8/b1b2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/2b5/b1b2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/b7/b1b2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q5b/5P2/2K5/7B/8/bb3q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5Pb1/2K5/7B/8/bb3q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K2b2/7B/8/bb3q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/4b2B/8/bb3q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/3b4/bb3q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bb3q1N/bqb5/bbb1b2R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bb3q1N/bqbb4/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/b6B/8/bb3q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/1b6/bb3q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7q/8/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/6q1/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb4N/bqb5/bbb4q"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb4N/bqb3q1/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2q5/7B/8/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/3q3B/8/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/4q3/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb4N/bqb5/bbbq3R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb4N/bqb1q3/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5q2/2K5/7B/8/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K2q2/7B/8/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/5q1B/8/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/5q2/bbb4N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb4N/bqb5/bbb2q1R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb4N/bqb2q2/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb4q/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb3qN/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbbq3N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("1q6/5P2/2K5/7B/8/bbb1q2N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/2K5/7B/8/bbb2q1N/bqb4q/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/2K5/7B/8/bbb2qqN/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/2K5/7B/5q2/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/2K5/4q2B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/2Kq4/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/2q2P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/q4P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/2K5/7B/1q6/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/2K5/1q5B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/5P2/1qK5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/1q3P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("7q/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("6q1/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("5q2/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("4q3/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("3q4/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("2q5/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("q7/5P2/2K5/7B/8/bbb2q1N/bqb5/bbb4R"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}
	
	public boolean rookWhite(int zahl ,char farbe){
		String fen  =  "8/3p1P2/2P2R1P/8/k2R2b1/5P2/1R6/3n4 w KQkq - 4 4";
		int nr = 4+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" 4";
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		if(!  ergebnis.remove(("1R6/3p1P2/2P2R1P/8/k2R2b1/5P2/8/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/1R1p1P2/2P2R1P/8/k2R2b1/5P2/8/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/1RP2R1P/8/k2R2b1/5P2/8/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/1R6/k2R2b1/5P2/8/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/kR1R2b1/5P2/8/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/1R3P2/8/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/8/1R1n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/7R/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/6R1/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/5R2/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/4R3/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/3R4/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/2R5/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2b1/5P2/R7/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R1Pb1/8/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k2R2P1/8/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3R1P2/2P2R1P/8/k5b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2PR1R1P/8/k5b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/3R4/k5b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k5b1/5P2/1R6/3R4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k5b1/5P2/1R1R4/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k5b1/3R1P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k5R1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k4Rb1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k3R1b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/R5b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/kR4b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P2R1P/8/k1R3b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/2Pp1P2/5R1P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3P1P2/5R1P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P4P/8/k2R1Rb1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P4P/5R2/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P3RP/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2PR3P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P2/2P1R2P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("8/3p1P1P/2P2R2/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("5R2/3p4/2P2R1P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("5B2/3p4/2P2R1P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("5N2/3p4/2P2R1P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("5Q2/3p4/2P2R1P/8/k2R2b1/5P2/1R6/3n4"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}
	public boolean rookBlack(int zahl ,char farbe){
			String fen  =  "8/3P1p2/2p2e1p/8/K2r2B1/5p2/1r6/3N4 b KQkq - 4 4";
			int nr = 4+ zahl;
			String restFEN =  " "+farbe+" KQkq - "+nr+" "+nr;
			generator.setFEN(fen);
			LinkedList<String> ergebnis = generator.getZuege();
			boolean testErgebnis = true;
			
			if(!  ergebnis.remove(("1r6/3P1p2/2p2E1p/8/K2r2B1/5p2/8/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/1r1P1p2/2p2E1p/8/K2r2B1/5p2/8/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/1rp2E1p/8/K2r2B1/5p2/8/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/1r6/K2r2B1/5p2/8/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/Kr1r2B1/5p2/8/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/1r3p2/8/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/8/1r1N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/7r/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/6r1/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/5r2/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/4r3/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/3r4/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/2r5/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/5p2/r7/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K2r2B1/8/1r3p2/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3r1p2/2p2E1p/8/K5B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2pr1E1p/8/K5B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/3r4/K5B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K5B1/5p2/1r6/3r4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K5B1/5p2/1r1r4/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K5B1/3r1p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K5r1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K4rB1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K3r1B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/r5B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/Kr4B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E1p/8/K1r3B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/5E1p/2p5/K2r2B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			if(!  ergebnis.remove(("8/3P1p2/2p2E2/7p/K2r2B1/5p2/1r6/3N4"+restFEN) ) ){
				testErgebnis = false;
				errorLog.append("Fehler:   \n");
			}
			
			
			
			if(!ergebnis.isEmpty()){
				testErgebnis = false;
			}
			if(!testErgebnis){
				errorLog.append(ergebnis.toString());
			}
			return testErgebnis;
	}
	public boolean bishopBlackTestLauf(int zahl ,char farbe){
		String fen  =  "p6K/1b4p1/6P1/1R3P2/2Bb4/3b4/2K1Q3/pB3Np1 b KQkq f6 6 6";
		int nr = 6+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" "+nr;
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3b2/2Bb4/8/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2Bbb3/8/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2Bb4/8/2K1b3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2bb4/8/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2Bb4/8/2b1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/5bP1/1R3P2/2B5/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R2bP2/2B5/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2B5/3b4/2K1Qb2/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2B5/3bb3/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/bb4p1/6P1/1R3P2/2B5/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/1b4P1/1R3P2/2B5/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1Rb2P2/2B5/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2B5/3b4/1bK1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/1b4p1/6P1/1R3P2/2B5/2bb4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p1b4K/6p1/6P1/1R3P2/2Bb4/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/6p1/6P1/1R3P2/2Bb4/3b4/2K1Q3/pB3Npb"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/6p1/6P1/1R3P2/2Bb4/3b4/2K1Q1b1/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/6p1/6P1/1R3P2/2Bb4/3b1b2/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/6p1/6P1/1R3P2/2Bbb3/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/6p1/6P1/1R1b1P2/2Bb4/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/6p1/2b3P1/1R3P2/2Bb4/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("p6K/6p1/b5P1/1R3P2/2Bb4/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("7K/pb4p1/6P1/1R3P2/2Bb4/3b4/2K1Q3/pB3Np1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}
	public boolean bishopWhiteTestLauf(int zahl ,char farbe){
		String fen  =  "P6k/1B4P1/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1 w KQkq f6 6 3";
		int nr = 6+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" 3";
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2bB4/3B4/P1k1q3/1b3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2bB4/3B4/2k1q1P1/Pb3n2"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3B2/2bB4/8/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2bBB3/8/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2bB4/8/2k1B3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2BB4/8/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2bB4/8/2B1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/5Bp1/1r3p2/2b5/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r2Bp2/2b5/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2b5/3B4/2k1qB2/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2b5/3BB3/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/BB4P1/6p1/1r3p2/2b5/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/1B4p1/1r3p2/2b5/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1rB2p2/2b5/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2b5/3B4/1Bk1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/1B4P1/6p1/1r3p2/2b5/2BB4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P1B4k/6P1/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/6P1/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nPB"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/6P1/6p1/1r3p2/2bB4/3B4/2k1q1B1/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/6P1/6p1/1r3p2/2bB4/3B1B2/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/6P1/6p1/1r3p2/2bBB3/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/6P1/6p1/1r1B1p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/6P1/2B3p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6k/6P1/B5p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P5Rk/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P5Bk/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P5Nk/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P5Qk/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6R/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6B/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6N/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}
		if(!  ergebnis.remove(("P6Q/1B6/6p1/1r3p2/2bB4/3B4/2k1q3/Pb3nP1"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler:   \n");
		}

		
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}		
	
	public boolean knightBlackTestLauf(int zahl ,char farbe){
		String fen  =  "n7/8/2B1K3/1Q3R2/3n4/1P3B2/2N1P3/7n b KQkq f6 1 1";
		int nr = 1+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" "+nr;
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		
		if(!  ergebnis.remove(("n7/8/2B1K3/1Q3R2/3n4/1P3B2/2N1Pn2/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler links oben  \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1K3/1Q3R2/3n4/1P3Bn1/2N1P3/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler oben links  \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1K3/1Q3n2/8/1P3B2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag rechts oben   \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1K3/1Q3R2/8/1P3n2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag rechts unten  \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1K3/1n3R2/8/1P3B2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag links oben  \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1K3/1Q3R2/8/1n3B2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schage links unten  \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1n3/1Q3R2/8/1P3B2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag oben rechts  \n");
		}
		if(!  ergebnis.remove(("n7/8/2n1K3/1Q3R2/8/1P3B2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag oben links  \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1K3/1Q3R2/8/1P3B2/2N1n3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag unten rechts  \n");
		}
		if(!  ergebnis.remove(("n7/8/2B1K3/1Q3R2/8/1P3B2/2n1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag unten links  \n");
		}
		if(!  ergebnis.remove(("8/2n5/2B1K3/1Q3R2/3n4/1P3B2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler rechts unten  \n");
		}
		if(!  ergebnis.remove(("8/8/1nB1K3/1Q3R2/3n4/1P3B2/2N1P3/7n"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler unten rechts  \n");
		}
		
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}
	public boolean knightWhiteTestLauf(int zahl, char farbe){
		String fen  =  "N6N/8/2b1b3/1b3b2/3N4/1b3b2/2b1b3/N6N w KQkq f6 0 1";
		int nr = 0+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" 1";
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/3N4/1b3b2/2N1b3/7N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler rechts oben \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/3N4/1N3b2/2b1b3/7N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler oben rechts schlag \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/3N4/1b3b2/2b1bN2/N7"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler links oben \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/3N4/1b3bN1/2b1b3/N7"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler oben links \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3N2/8/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler rechts oben schlag \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/8/1b3N2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler rechts unten schlag  \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1N3b2/8/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler links oben schlag \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/8/1N3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler links unten schlag \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1N3/1b3b2/8/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler  oben rechts schlag \n");
		}
		if(!  ergebnis.remove(("N6N/8/2N1b3/1b3b2/8/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler  oben links schlag \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/8/1b3b2/2b1N3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler unten rechts schlag \n");
		}
		if(!  ergebnis.remove(("N6N/8/2b1b3/1b3b2/8/1b3b2/2N1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler unten rechts schlag \n");
		}
		if(!  ergebnis.remove(("7N/2N5/2b1b3/1b3b2/3N4/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler rechts unten \n");
		}
		if(!  ergebnis.remove(("7N/8/1Nb1b3/1b3b2/3N4/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler unten rechts \n");
		}
		if(!  ergebnis.remove(("N7/5N2/2b1b3/1b3b2/3N4/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler  links unten \n");
		}
		if(!  ergebnis.remove(("N7/8/2b1b1N1/1b3b2/3N4/1b3b2/2b1b3/N6N"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler  unten links \n");
		}
		
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}
	public boolean pawnBlackTestLauf(int zahl, char farbe){
		boolean testErgebnis = true;
		
		

		
		return testErgebnis;
	}
	public  boolean pawnWhiteTestLauf(int zahl, char farbe){
		String fen  =  "2nPn3/P2P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8 w KQkq f6 0 1";
		//String fen  =  "8/8/8/8/8/8/8/7P w KQkq f5 0 1";
		int nr = 0+ zahl;
		String restFEN =  " "+farbe+" KQkq - "+nr+" 1";
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/P4PP1/1nnp1P1B/3P2P1/8 "+farbe+" KQkq a2 "+nr+" 1") ) ){
			testErgebnis = false;
			errorLog.append("Fehler 2vor \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/Pnnp1P1B/3P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler 1vor \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/1Pnp1P1B/3P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag rechts \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/1nPp1P1B/P5P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag links \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/1nnp1PPB/P2P4/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler 1vor \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PPP1/5P2/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler schlag links \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/4P3/5pP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler 1vor \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/5P2/6P1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler enpassant  rechts \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/5P2/4P3/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler enpassant  links \n");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/6P1/4Pp2/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler 1vor \n");
		}
		if(!  ergebnis.remove(("R1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		if(!  ergebnis.remove(("B1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		if(!  ergebnis.remove(("N1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		if(!  ergebnis.remove(("Q1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		if(!  ergebnis.remove(("2nPR3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor rechts \n");
		}
		if(!  ergebnis.remove(("2nPB3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor rechts \n");
		}
		if(!  ergebnis.remove(("2nPN3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor rechts \n");
		}
		if(!  ergebnis.remove(("2nPQ3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor rechts \n");
		}
		if(!  ergebnis.remove(("2RPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor links \n");
		}
		if(!  ergebnis.remove(("2BPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor links \n");
		}
		if(!  ergebnis.remove(("2NPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor links \n" );
		}
		if(!  ergebnis.remove(("2QPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor links \n");
		}
		if(!  ergebnis.remove(("2nPn2R/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		if(!  ergebnis.remove(("2nPn2B/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		if(!  ergebnis.remove(("2nPn2N/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		if(!  ergebnis.remove(("2nPn2Q/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler promototion vor \n");
		}
		
		
		
		if(!ergebnis.isEmpty()){
			testErgebnis = false;
		}
		if(!testErgebnis){
			errorLog.append(ergebnis.toString());
		}
		return testErgebnis;
	}
	
	
	
}
