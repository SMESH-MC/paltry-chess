package chessengine.figurbewertung;

public class TestBewertung {
	
	public static void main (String[] args){
		
		FigurBewertung test = new FigurBewertung(9,8,7,6);
		
		
		System.out.println(test.getKingBewertung());
		System.out.println(test.getQueenBewertung());
		System.out.println(test.getRookBewertung());
		System.out.println(test.getBishopBewertung());
		System.out.println(test.getKnightBewertung());
		System.out.println(test.getPawnBewertung());
		
		
	}

}
