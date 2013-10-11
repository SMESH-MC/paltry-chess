package chessengine;

import chessengine.figurbewertung.FigurBewertung;
import chessengine.moveevaluator.MoveEvaluatorTree;

/**
 * Manager-Klassen, dient zum inizialisieren der anderen Module und deren
 * Kommunikation untereinander.
 *
 * @author Alexander Kessler, Thorsten Jakobs
 */
public class Manager implements Runnable {

    //---------------------------Objektvariablen
    private UCI_Interface newUCI;
    private FigurBewertung figurBewertung;
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
    private int vollzuege;

    /**
     * Standartkonstruktor des Managers.
     */
    public Manager() {
        stop = false;
        go = false;
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR "
                + "w KQkq - 0 1";
        wtime = 0;
        btime = 0;
        movetime = 0;
        winc = 0;
        binc = 0;
        bestZug = null;
        engineIsWhite = false;
        vollzuege = 1;
    }

    /**
     * Initialisiert den Manager fuer den ersten Gebrauch.
     */
    public void initialize() {
        try {
            newUCI = new UCI(this);
            figurBewertung = new FigurBewertung();
            newUCI.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Uebernimmt alle relevanten Variablenwerte aus UCI.
     */
    private void getAll() {
        //fen = newUCI.getFEN();
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
     *
     * @return engineIsWhite
     */
    public boolean isWhite() {
        return engineIsWhite;
    }

    /**
     * Setter-Methode um benutzerdefinierte Werte in die Figurbewertung zu
     * uebernehmen.
     *
     * @param queen
     * @param rook
     * @param knight
     * @param bishop
     */
    public void setWerte(int queen, int rook, int knight, int bishop) {
        figurBewertung.setQueenBewertung(queen);
        figurBewertung.setRookBewertung(rook);
        figurBewertung.setKnightBewertung(knight);
        figurBewertung.setBishopBewertung(bishop);
    }

    /**
     * Startpunkt eines Threads. Dieser dient zur Zugberechnung.
     */
    @Override
    public void run() {
        getAll();
        String moves = newUCI.getMovesList();
        String move;
        if (moves != null) {
            move = moves.substring(moves.length() - 5);
            this.fen = new ToFen2(fen, move).getNewFen();
        }

        if (vollzuege > 10) {
            MoveEvaluatorTree tree = new MoveEvaluatorTree(fen, figurBewertung, this);
            bestZug = tree.getBestMove();
            this.fen = tree.getBestMoveFen();
            newUCI.bestmove(bestZug);
        } else {
            //fakebestmove hier hin
        }
        vollzuege++;
    }
}
