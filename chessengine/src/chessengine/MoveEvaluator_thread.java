package chessengine;

public class MoveEvaluator_thread {
	//Variablendefinitionen
	private Board currentBoard;
	private Board predictionBoard;
	private int depth;
	private int time;
	private int timeRemaining;
	private int userCancel;
	ZugThread zthread;
	
	public String IncomingFEN;
	public String OutgoingFEN;
	
	//Ab hier soll Voodoo folgen (fast fertig)
	
	public void init() {
		
		zthread = new ZugThread();
		zthread.start();
		
	}
	
	public void start() {
		
		if (zthread == null) {
			zthread = new ZugThread();
			zthread.start();
		}
		
	}

	public void stop() {

		if (zthread != null) {
			
			zthread.interrupt();
			zthread = null;
			
		}
	}
	
	public void destroy() {
		if (zthread != null) {
			
			zthread.interrupt();
			zthread = null;
			
		}
	}
	
	class ZugThread extends Thread {
		//Hier Threadgenerierung
		public void run(){
			
		}
		
	}
}
