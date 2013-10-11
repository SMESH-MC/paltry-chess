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
		int aenderung1Pos = 0;
		int aenderung1Typ;
		int aenderung2Pos = 0;
		int aenderung2Typ;

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
				aenderung1Pos = 7;
				break;
			case 'b':
				aenderung1Pos = 6;
				break;
			case 'c':
				aenderung1Pos = 5;
				break;
			case 'd':
				aenderung1Pos = 4;
				break;
			case 'e':
				aenderung1Pos = 3;
				break;
			case 'f':
				aenderung1Pos = 2;
				break;
			case 'g':
				aenderung1Pos = 1;
				break;
			case 'h':
				aenderung1Pos = 0;
				break;
			}
			aenderung1Typ = Integer.parseInt(move.substring(1,1));
			
			boardPosition = move.charAt(2);
			switch (boardPosition) {
			case 'a':
				aenderung2Pos = 7;
				break;
			case 'b':
				aenderung2Pos = 6;
				break;
			case 'c':
				aenderung2Pos = 5;
				break;
			case 'd':
				aenderung2Pos = 4;
				break;
			case 'e':
				aenderung2Pos = 3;
				break;
			case 'f':
				aenderung2Pos = 2;
				break;
			case 'g':
				aenderung2Pos = 1;
				break;
			case 'h':
				aenderung2Pos = 0;
				break;
			}
			aenderung2Typ = Integer.parseInt(move.substring(3,3));
			
			
			fenArray[aenderung1Pos] = modifieFen1(fenArray[aenderung1Pos], aenderung1Typ);
			fenArray[aenderung2Pos]	= modifieFen2(fenArray[aenderung2Pos], aenderung2Typ);
			
			if (amZug.equals("s") ) {
				amZug = "w";
				Integer i = new Integer((Integer.parseInt(halbzug) + 1));
				halbzug = i.toString();
				Integer j = new Integer((Integer.parseInt(zug) + 1));
				zug = j.toString();
			} else {
				amZug = "s";
				Integer i = new Integer((Integer.parseInt(halbzug) + 1));
				halbzug = i.toString();
			}
			
			newFen = fenArray[0]+fenArray[1]+fenArray[2]+fenArray[3]+fenArray[4]+fenArray[5]+fenArray[6]+fenArray[7]+" "+amZug+" "+rochade+" "+enPassant+" "+halbzug+" "+zug;
			
	}
	
	
	
	
	private String modifieFen1(String fenPart, int aenderungTyp) {
		String tempFen = homologizeFen(fenPart);
		movedFigure = tempFen.charAt(aenderungTyp - 1);
		tempFen.replace(movedFigure, '0');
		return makeFen(tempFen);
	}
	
	private String modifieFen2(String fenPart, int aenderungTyp) {
		String part1, part2, completedString;
		String tempFen = homologizeFen(fenPart);
		part1 = tempFen.substring(0, aenderungTyp - 1 );
		part2 = tempFen.substring(aenderungTyp + 1);
		completedString = part1 + movedFigure + part2;
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
		String ausgabe = "";
		int felder = 0;
		for (int i = 0; i < s.length(); i++) {
			felder = 0;
			int j=i;
			while (s.substring(j, j+ 1).equalsIgnoreCase("0")) {
				felder++;				
			}
			if (felder != 0) {
				ausgabe += felder;
			} else {
			ausgabe += s.substring(i, i+1);
			}
		}
		return ausgabe;
	}
	
	public String getNewFen() {
		return newFen;
	}
	
}
