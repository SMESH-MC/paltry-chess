package chessengine.tools;
import java.util.LinkedList;

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

	public static void main (String[] args){
		 generator = new MoveGenerator();
	   // generator = new ZugGeneratorPhilip();
		 
		 ZuggeneratorTest test = new ZuggeneratorTest();
		 test.testlauf();
	}
	
	public void testlauf(){
		StringBuffer bericht = new StringBuffer();
		errorLog = new StringBuffer();
		int hochzeahlen = 0;
		char w = 'w';
		char b = 'b';
		
		bericht.append("Testlauf Bauer Weis:\t ");
		bericht.append( pawnWhiteTestLauf(hochzeahlen,'b') );
		bericht.append("\nTestlauf Knight black:\t ");
		bericht.append( knightBlackTestLauf(hochzeahlen,b) );
		bericht.append("\nTestlauf Knight Weis:\t ");
		bericht.append( knightWhiteTestLauf(hochzeahlen,w) );
		
		bericht.append("\nTestlauf Bishop Weis:\t ");
		bericht.append( bishopWhiteTestLauf(hochzeahlen,w) );
		
		bericht.append("\nTestlauf Bishop black:\t ");
		bericht.append( bishopBlackTestLauf(hochzeahlen,b) );
		
		
		
		System.out.println(bericht);
		System.out.println(errorLog);
	}
	
	public boolean test(int zahl ,char farbe){
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
