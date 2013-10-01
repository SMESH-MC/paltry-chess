/**
 *  Testklasse zum Testen der FigurBewertung und des beinhalteted Zuggenerators
 * @author Philip Hunsicker
 * Stand : 25.09.2013
 */
package chessengine.figurbewertung;


import java.util.LinkedList;

import chessengine.tools.*;
public class TestKlasse {


	public static void main (String[] args){
			String fen;
			Brett brett = new X88();
			//Brett brett = new Array2Dim();
			 FenDecoder decoder = new FenDecoder(brett);
			Brett schachBrett;
			//startstellung
			//Figur[][] "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			// rochaden
			//fen =  "r2kqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbk3r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/R3KBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1";
			//fen =  "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
			//fen =  "r3k2r/ppppRppp/7N/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
			//Szenarien
			fen =  "rnbkqbnr/8/8/PPPP4/8/4pppp/8/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/8/p7/PPPP4/3pp3/4pppp/8/RNBQKBNR w KQkq - 0 1";
			
			//enPassannt
			//fen = "rnbkqbnr/pppp1ppp/8/3Pp3/4Pp2/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
			fenAusgabe(fen,decoder);
			schachBrett = decoder.decodiere(fen);
			//arrayAusgabe( schachBrett);
		
		Long time = System.currentTimeMillis();	
		
		for(int i = 0 ;i<10000;i++){
			//System.out.println(fen);	
			FigurBewertungInterface figuren= new FigurBewertung();
			SchachPosition position = new SchachPosition(0,0);
		
			//System.out.println("Alle Zuege fur farbe am Zug---------------------------------------");
			LinkedList<String> liste2 = figuren.ermittleAlleZuege(fen);
			/*
			while(liste2.isEmpty() == false){
			
				fenAusgabe(liste2.pop(),decoder );
			}*/
			//System.out.print(".");
		}
		System.out.println();
		System.out.println("Zeit:"+(System.currentTimeMillis()-time) );
	}
	
	
	
	
	public static void fenAusgabe(String ausgabeFen, FenDecoder decoder){
		Brett ausgabe = decoder.decodiere(ausgabeFen);
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
		System.out.println(decoder.toString());
		System.out.println();
	}
}
