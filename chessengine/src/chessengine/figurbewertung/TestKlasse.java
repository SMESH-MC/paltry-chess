/**
 *  Testklasse zum Testen der FigurBewertung und des beinhalteted Zuggenerators
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
package chessengine.figurbewertung;


import java.util.LinkedList;

import chessengine.movegenerator.*;
import chessengine.tools.*;
public class TestKlasse {


	public static void main (String[] args){
			String fen;
			Brett brett = new X88();
			//Brett brett = new Array2Dim();
			 FenDecoder decoder = new FenDecoder(brett);
			 
			//startstellung
			//Figur[][] "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			// rochaden
			//fen =  "r2kqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbk3r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/R3KBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1";
			//fen =  "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
			//fen =  "r3k2r/ppppRppp/7N/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
			//promotion
			//fen =  "8/P7/8/8/8/8/8/8 w KQkq - 0 1";
			//Szenarien
			//fen =  "rnbkqbnr/8/8/PPPP4/8/4pppp/8/RNBQKBNR w KQkq - 0 1";
			fen =  "8/p2p2p1/1NNP1p1b/5pp1/4pPp1/8/3p3p/2NrN3 b KQkq f3 1 1";
			////////////fen = "k7/3P3P/8/4PpP1/5PP1/5P1B/6P1/K7 w - f6 0 1";
			//enPassannt
			//fen = "rnbkqbnr/pppp1ppp/8/3Pp3/4Pp2/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
			//fen = "rnbkqbnr/pppp1ppp/8/3Pp3/4Pp2/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 1";
	    Brett ausgabe = decoder.decodiere(fen);
		fenAusgabe(ausgabe);
		System.out.println("Alle Zuege fur farbe am Zug---------------------------------------"+fen);	

		
		//MoveGeneratorInterface figuren= new ZugGeneratorPhilip();
		MoveGeneratorInterface figuren= new MoveGenerator();
		
		long zeit = 0;
		//int durchlaeufe = 64000;
		int durchlaeufe = 1;
		Long time = System.currentTimeMillis();	
		
		for(int i = 0 ;i < durchlaeufe;i++){


			//SchachPosition position = new SchachPosition(0,0);
		
			//System.out.println();
			figuren.setFEN(fen);
			LinkedList<String> liste2 = figuren.getZuege();
			
			
			while(liste2.isEmpty() == false){
			
				ausgabe = decoder.decodiere(liste2.pop());
				
				fenAusgabe( ausgabe );
				
				System.out.println(decoder.toString());
			}
			//System.out.print(".");
		}
		zeit = System.currentTimeMillis()-time;
		System.out.println();
		System.out.println("Zeit(ms):"+ zeit + "fuer " + durchlaeufe + "durchlaeufe");
		System.out.println("Zeit/durchlaufe(ms) : "+ zeit/(double)durchlaeufe);
	}
	
	
	
	
	public static void fenAusgabe(Brett ausgabe){
		
		
		  System.out.println();
		  if(ausgabe != null){
			System.out.println("X||0|1|2|3|4|5|6|7");
			System.out.println("-------------------");
			for(int i = 7; i >= 0 ;i--){
				System.out.print(i+"||");
				for(int k = 0 ; k < 8; k++){
					
					if(ausgabe.getIsEmpty(k, i) == false ){
					
					
						System.out.print( ausgabe.getInhalt(k, i) + "|");//\t
					}else{
					
						System.out.print(" |");
					
					}
				
				}
				System.out.println();
			}
			
			
			System.out.println("-------------------");
		}
		
	    
	}
	
}
