package chessengine;

public class MoveEvaluator {
	//Variablendefinitionen
	private Board currentBoard;
	private Board predictionBoard;
	private int depth;
	private int time;
	private int timeRemaining;
	private int userCancel;
	
	public String IncomingFEN;
	public String OutgoingFEN;
	
	//Ab hier soll Voodoo folgen (fast fertig)
	
	public void init() {
		
	}
	
	public void start() {
		
	}

	public void stop() {
		//Abbruch wenn nur noch eine Sekunde verfuegbar
		if (timeRemaining == '1') {
			
		}
		//Abbruch durch Benutzer
		if (userCancel == '1') {
			
		}
	}
	
	public void destroy() {
		//Abbruch wenn nur noch eine Sekunde verfuegbar
		if (timeRemaining == '1') {
			
		}
		//Abbruch durch Benutzer
		if (userCancel == '1') {
			
		}
	}
	
	class ZugThread extends Thread {
		//Hier Threadgenerierung
		
	}
}
