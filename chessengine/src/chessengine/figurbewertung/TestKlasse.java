package chessengine.figurbewertung;


import java.util.LinkedList;

import chessengine.tools.Figur;
import chessengine.tools.SchachPosition;
import chessengine.tools.FenDecoder;
public class TestKlasse {


	public static void main (String[] args){
			String fen;
			 FenDecoder decoder = new FenDecoder();
			Figur[][] schachBrett;
			//startstellung
			//Figur[][] "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			// rochaden
			//fen =  "r2kqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbk3r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/R3KBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1");
			fen =  "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
			
			//Szenarien
			//fen =  "rnbkqbnr/8/8/PPPP4/8/4pppp/8/RNBQKBNR w KQkq - 0 1";
			//fen =  "rnbkqbnr/8/p7/PPPP4/3pp3/4pppp/8/RNBQKBNR w KQkq - 0 1";
			
			//enPassannt
			//fen = "rnbkqbnr/pppp1ppp/8/3Pp3/4Pp2/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
			fenAusgabe(fen,decoder);
			schachBrett = decoder.decodiere(fen);
			//arrayAusgabe( schachBrett);
			
		System.out.println(fen);	
		FigurBewertungInterface figuren= new FigurBewertung();
		figuren.inizialisiereBrett( fen ); //WICHTIG
		SchachPosition position = new SchachPosition(0,0);
		/*
		System.out.print("Alle Zuege-fuer beide farben--------------------------------------");
		for(int y =0 ;y <8;y++)
		{
			for (int x =0; x < 8 ; x++ ){
				position.setXY(x, y);
					
				if(schachBrett[x][y] != null){//ein figur auf dem feld
					Figur figur 	= schachBrett[ position.getX() ][ position.getY() ];
					String typ 		= figur.toString();
					System.out.print("Zuege fuer "+typ+" auf"+ position + "--------------------");
					System.out.println("Zuege fuer "+typ+" auf"+ position + "-----------------------------------------");
					//System.out.println( figuren.ermittleZuege(position)  );
					
					LinkedList<String> liste= figuren.ermittleZuege(position);
					while(liste.isEmpty() == false){
						fenAusgabe(liste.pop(),decoder );
					}
					
				}
			}	
		}*/
		System.out.println("Alle Zuege fur farbe am Zug---------------------------------------");
		LinkedList<String> liste2= figuren.ermittleAlleZuege(fen);
		while(liste2.isEmpty() == false){
			fenAusgabe(liste2.pop(),decoder );
		}
	}
	
	
	
	
	public static void fenAusgabe(String ausgabeFen, FenDecoder decoder){
		Figur[][] ausgabe = decoder.decodiere(ausgabeFen);
		if(ausgabe != null){
			System.out.println("X||0|1|2|3|4|5|6|7");
			System.out.println("-------------------");
			for(int i = ausgabe.length-1; i >= 0 ;i--){
				System.out.print(i+"||");
				for(int k = 0 ; k < ausgabe.length; k++){
					
					if(ausgabe[k][i]!= null){
					
					
						System.out.print( ausgabe[k][i] + "|");//\t
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
