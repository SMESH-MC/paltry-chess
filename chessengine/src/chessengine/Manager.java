package chessengine;

/**
 * Manager-Klassen, dient zum inizialisieren der anderen Module und deren 
 * Kommunikation untereinander.
 * 
 * @author Alexander Kessler, Thorsten Jakobs
 */
public class Manager implements Runnable {

    //---------------------------Objektvariablen
    private UCI_Interface newUCI;
    private BoardInterface Board;
    private String fen;
    private boolean stop;
    private boolean go;
    private int wtime;
    private int btime;
    private int movetime;
    private int winc;
    private int binc;
    private String bestZug;
    private boolean engineIsWhite;

    /**
     * Standartkonstruktor des Managers.
     */
    public Manager() {
        stop = false;
        go = false;
        fen = null;
        wtime = 0;
        btime = 0;
        movetime = 0;
        winc = 0;
        binc = 0;
        bestZug = null;
        engineIsWhite = false;
    }

    /**
     * Initialisiert den Manager fuer den ersten Gebrauch.
     */
    public void initialize() {
        try {
            newUCI = new UCI(this);
            newUCI.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Uebernimmt alle relevanten Variablenwerte aus UCI.
     */
    private void getAll() {
        fen = newUCI.getFEN();
        wtime = newUCI.getWtime();
        btime = newUCI.getBtime();
        winc = newUCI.getWinc();
        binc = newUCI.getBinc();
        movetime = newUCI.getMovetime();
    }

    /**
     * Setter fuer die stop-Variable.
     *
     * @param stop neuer Wert fuer stop
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * Getter fuer die stop-Variable.
     *
     * @return liefert den aktuellen Wert von stop zurueck.
     */
    public boolean getStop() {
        return stop;
    }

    /**
     * Setzt engineIsWhite auf true.
     */
    public void setWhite() {
        engineIsWhite = true;
    }

    /**
     * Liefert den Wert von engineIsWhite zurueck.
     * @return engineIsWhite
     */
    public boolean isWhite() {
        return engineIsWhite;
    }

    /**
     * Startpunkt eines Threads. Dieser dient zur Zugberechnung.
     */
    @Override
    public void run() {
        getAll();
        //An dieser Stelle muesste das berechnende Objekt(MoveEvaluator?) 
        //erzeugt werden
        //bestZug = moveEvaluator.best_zug();
        newUCI.bestmove(bestZug);
    }
}
