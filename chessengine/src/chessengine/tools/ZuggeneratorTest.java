package chessengine.tools;
import java.util.LinkedList;

import chessengine.movegenerator.MoveGeneratorInterface;
import chessengine.movegenerator.MoveGenerator;
import chessengine.figurbewertung.ZugGeneratorPhilip;

public class ZuggeneratorTest {

	private static MoveGeneratorInterface generator;
	private StringBuffer errorLog = new StringBuffer();
	
	public static void main (String[] args){
		 //generator = new MoveGenerator();
		 generator = new ZugGeneratorPhilip();
		 
		 ZuggeneratorTest test = new ZuggeneratorTest();
		 test.testlauf();
	}
	
	public void testlauf(){
		StringBuffer bericht = new StringBuffer();
		bericht.append("Testlauf Bauer : ");
		bericht.append( pawnWhiteTestLauf() );
		
		
		
		System.out.println(bericht);
		System.out.println(errorLog);
	}
	
	public  boolean pawnWhiteTestLauf(){
		String fen  =  "2nPn3/P2P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8 w KQkq f6 0 1";
		//String fen  =  "8/8/8/8/8/8/8/7P w KQkq f5 0 1";
		String restFEN =  " b KQkq - 1 1";
		generator.setFEN(fen);
		LinkedList<String> ergebnis = generator.getZuege();
		boolean testErgebnis = true;
		
		
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/P4PP1/1nnp1P1B/3P2P1/8 b KQkq a2 1 1") ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/Pnnp1P1B/3P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/1Pnp1P1B/3P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/1nPp1P1B/P5P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PpP1/5PP1/1nnp1PPB/P2P4/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/8/4PPP1/5P2/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/4P3/5pP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/5P2/6P1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/5P2/4P3/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn3/P2P3P/6P1/4Pp2/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("R1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("B1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("N1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("Q1nPn3/3P3P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPR3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPB3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPN3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPQ3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2RPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2BPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2NPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2QPn3/P6P/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn2R/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn2B/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn2N/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
		}
		if(!  ergebnis.remove(("2nPn2Q/P2P4/8/4PpP1/5PP1/1nnp1P1B/P2P2P1/8"+restFEN) ) ){
			testErgebnis = false;
			errorLog.append("Fehler");
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
