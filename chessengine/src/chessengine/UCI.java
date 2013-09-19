/**
 * The UCI protocol as publiced by Stefan-Meyer Kahlen
 */
// Nur ein kleiner Testkommentar um Commit zu testen.
package chessengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Alexander Kessler
 *
 */
public class UCI implements UCI_Interface, Runnable  {

    /**
     * GUI to engine :(ueber Reader.readln) uci, debug [on | off], isready,
     * setoption name [value], register, ucinewgame, position[fen | startpos ],
     * go "+ weitere Befehle"(s.
     * http://wbec-ridderkerk.nl/html/UCIProtocol.html), stop, ponderhit, quit
     *
     */
    //********Konstanten********
    private static final String NAME = "Projekt Schachengine 2013";
    private static final String AUTHOR = "Projektgruppe Schachengine";
    private static final String QUIT = "quit";
    private static final String UCI = "uci";
    private static final String UCIOK = "uciok";
    private static final String ISREADY = "isready";
    private static final String READYOK = "readyok";
    private static final String POSITION = "position";
    private static final String SPLITPOINT = "\\s+";
    private static final String UNKNOWN_CMD = "Kommando nicht erkannt!";
    private static final String MOVES = "moves";
    private static final String SPACE = " ";
    private static final String STOP = "stop";
    private static final String GO = "go";
    private static final String WTIME = "wtime";
    private static final String BTIME = "btime";
    private static final String WINC = "winc";
    private static final String BINC = "binc";
    private static final String MOVETIME = "movetime";
    private static final String INFINITE = "infinite";
    //********Variablen********
    private String fen;
    private boolean stop;
    private BufferedReader reader;
    private int wtime;
    private int btime;
    private int movetime;
    private int winc;
    private int binc;

    public UCI() {
        //FEN initialisiert mit Standard Startposition
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR "
                + "w KQkq - 0 1";
        reader = new BufferedReader(new InputStreamReader(System.in));
        stop = false;
        wtime = 0;
        btime = 0;
        winc = 0;
        binc = 0;
        movetime = 0;

    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void input() throws IOException {
        String cmdIN = null;
        String[] cmdArray = null;
        String cmd = null;

        while (!cmdIN.equals(QUIT)) {
            cmdIN = reader.readLine();
            //konvertiert den Befehl in Kleinbuchstaben
            cmdIN = cmdIN.toLowerCase();
            cmdArray = cmdIN.split(SPLITPOINT);

            // Fallunterscheidung fuer die versch. Befehle
            switch (cmd) {
                case UCI:
                    id();
                    System.out.println(UCIOK);
                    break;
                case ISREADY:
                    System.out.println(READYOK);
                    break;
                case POSITION:
                    position(cmdIN, cmdArray);
                    break;
                case STOP:
                    stop = true;
                    break;
                case GO:
                    go(cmdArray);
                    break;
                default:
                    // TODO falsche kommandos sollen ignoriert werden.
                    //Keine fehlermeldung?
                    System.err.println(UNKNOWN_CMD);
                    break;
            }
            System.exit(0);
        }
    }

    /**
     * Gibt die ID der Engine auf stdout aus.
     */
    private void id() {
        System.out.print("id name " + NAME + "\nid author " + AUTHOR);
    }

    private void position(String cmdIN, String[] cmdArray) {
        int movesIndex = cmdIN.indexOf(MOVES);
        if (cmdArray[1].equalsIgnoreCase("fen")) {
            String newFen = null;
            if (movesIndex == -1) {
                for (int i = 2; i < cmdArray.length; i++) {
                    newFen += cmdArray[i] + " ";
                }
            } else {
                for (int i = 2; i < movesIndex; i++) {
                    newFen += cmdArray[i] + " ";
                }
            }
            fen = newFen;
        }
    }

    private void go(String[] cmdArray) {
        for (int i = 1; i < cmdArray.length; i++) {
            try {
                switch (cmdArray[i]) {
                    case INFINITE:
                        wtime = 999999999;
                        btime = 999999999;
                        break;
                    case MOVETIME:
                        movetime = Integer.parseInt(cmdArray[i + 1]);
                        break;
                    case WTIME:
                        wtime = Integer.parseInt(cmdArray[i + 1]);
                        break;
                    case BTIME:
                        btime = Integer.parseInt(cmdArray[i + 1]);
                        break;
                    case WINC:
                        winc = Integer.parseInt(cmdArray[i + 1]);
                        break;
                    case BINC:
                        binc = Integer.parseInt(cmdArray[i + 1]);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("e");
            }
        }
    }

    /**
     *
     * @param move - der beste Zug, der durch die Engine gefunden wurde
     */
    @Override
    public void bestmove(String move) {
        //Stop wird auf false gesetzt, fuer den naechsten Zug
        stop = false;
        System.out.println("bestmove " + move);
    }

    /**
     * Die Methode getFEN liefert den gespeicherten FEN-String zurueck.
     *
     * @return String FEN-String
     */
    @Override
    public String getFEN() {
        return fen;
    }

    @Override
    public boolean getStop() {
        return stop;
    }

    @Override
    public int getWtime() {
        return wtime;
    }

    @Override
    public int getBtime() {
        return btime;
    }

    @Override
    public int getWinc() {
        return winc;
    }

    @Override
    public int getBinc() {
        return binc;
    }

    @Override
    public int getMovetime() {
        return movetime;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    class UCIListener implements Runnable{

        @Override
        public void run() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
}