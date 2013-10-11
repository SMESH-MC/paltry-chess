package chessengine;

public class ToFen2 {
	
	private String newFen; 
	private char movedFigure;
	
	public ToFen2(String fen, String move) {
		String[] fenArray;
		String[] zusatzInfo;
		String amZug;
		String rochade;
		String enPassant;
		String halbzug;
		String zug;
		char boardPosition;
		int linieStartIndexIndex = 0;
		int reiheStartIndex;
		int linieZielIndex = 0;
		int reiheZiel;

		fenArray = null;
		zusatzInfo = null;
		
		fenArray = fen.split("/");
		zusatzInfo = fenArray[7].split(" ");
		fenArray[7] = zusatzInfo[0].trim();
		amZug = zusatzInfo[1].trim();
		rochade = zusatzInfo[2].trim();
		enPassant = zusatzInfo[3].trim();
		halbzug = zusatzInfo[4].trim();
		zug = zusatzInfo[5].trim();
		
			boardPosition = move.charAt(0);
			switch (boardPosition) {
			case 'a':
				linieStartIndexIndex = 0;
				break;
			case 'b':
				linieStartIndexIndex = 1;
				break;
			case 'c':
				linieStartIndexIndex = 2;
				break;
			case 'd':
				linieStartIndexIndex = 3;
				break;
			case 'e':
				linieStartIndexIndex = 4;
				break;
			case 'f':
				linieStartIndexIndex = 5;
				break;
			case 'g':
				linieStartIndexIndex = 6;
				break;
			case 'h':
				linieStartIndexIndex = 7;
				break;
			}
			
			reiheStartIndex = Integer.parseInt(String.valueOf(move.charAt(1))) - 1;
			
			boardPosition = move.charAt(2);
			switch (boardPosition) {
			case 'a':
				linieZielIndex = 0;
				break;
			case 'b':
				linieZielIndex = 1;
				break;
			case 'c':
				linieZielIndex = 2;
				break;
			case 'd':
				linieZielIndex = 3;
				break;
			case 'e':
				linieZielIndex = 4;
				break;
			case 'f':
				linieZielIndex = 5;
				break;
			case 'g':
				linieZielIndex = 6;
				break;
			case 'h':
				linieZielIndex = 7;
				break;
			}
			reiheZiel = Integer.parseInt(String.valueOf(move.charAt(3))) - 1; 
			
			
			
		
			fenArray[reiheStartIndex] = modifieFen1(fenArray[reiheStartIndex], linieStartIndexIndex);
			fenArray[reiheZiel]	= modifieFen2(fenArray[reiheZiel], linieZielIndex);
			
				
				
 //    		if (amZug.equals("s") ) {
//				amZug = "w";
//				Integer i = new Integer((Integer.parseInt(halbzug) + 1));
//				halbzug = i.toString();
//				Integer j = new Integer((Integer.parseInt(zug) + 1));
//				zug = j.toString();
//			} else {
//				amZug = "s";
//				Integer i = new Integer((Integer.parseInt(halbzug) + 1));
//				halbzug = i.toString();
//			}
			
			newFen = (fenArray[0]+"/"+fenArray[1]+"/"+fenArray[2]+"/"+fenArray[3]+"/"+fenArray[4]+"/"+fenArray[5]+
					"/"+fenArray[6]+"/"+fenArray[7]+" "+amZug+" "+rochade+" "+enPassant+" "+halbzug+" "+zug);
			
	}
	
	
	
	
	private String modifieFen1(String fenPart, int aenderungTyp) {
		String tempFen = homologizeFen(fenPart);
		movedFigure = tempFen.charAt(aenderungTyp);
		tempFen = tempFen.substring(0, aenderungTyp) + "0" + tempFen.substring(aenderungTyp + 1, tempFen.length());
		return makeFen(tempFen);
	}
	
	private String modifieFen2(String fenPart, int aenderungTyp) {
		String completedString;
		
		String tempFen = homologizeFen(fenPart);
		completedString = tempFen.substring(0, aenderungTyp) + movedFigure + tempFen.substring(aenderungTyp + 1, tempFen.length());
		/*
		if (aenderungTyp < 7) {
			part2 = tempFen.substring(aenderungTyp + 1);
			completedString = part1 + movedFigure + part2;
		} else {
			completedString = part1 + movedFigure;
		}
		*/
		return makeFen(completedString);
	}
	
	
	private String homologizeFen(String s) {
		String temp = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i, i+ 1).matches("[1-8]") ) {
				int leerFelder = Integer.parseInt(s.substring(i, i+ 1)); 
				for (int j = 0; j < leerFelder; j++) {
					temp= temp + "0";
				}
			} else {
				temp= temp + s.substring(i, i+ 1);
			}
		}
		return temp;
	}
	
	private String makeFen(String s) {
	  
	    
	    s = s.replace("00000000","8");
		s = s.replace("0000000","7");
		s = s.replace("000000","6");
		s = s.replace("00000","5");
		s = s.replace("0000","4");
		s = s.replace("000","3");
		s = s.replace("00","2");
		s = s.replace("0","1");
	    
	    /*
		String ausgabe = "";
		int felder = 0;
		for (int i = 0; i < s.length(); i++) {
			felder = 0;
			while(true) {
				if (i < s.length() && s.substring(i, i+1).equals("0")) {
					felder++;
					i++;
				} else {
					break;
				}
			}
			if (felder != 0) {
				ausgabe += felder;
			} else {
			ausgabe += s.substring(i, i+1);
			}
		}
		*/
		
		return s;
	}
	
	public String getNewFen() {
		return newFen;
	}
	
}
