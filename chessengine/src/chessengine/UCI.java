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
public class UCI implements UCI_Interface  {

    public BufferedReader reader;
    /**
     * GUI to engine :(ueber Reader.readln) uci, debug [on | off], isready,
     * setoption name [value], register, ucinewgame, position[fen | startpos ],
     * go "+ weitere Befehle"(s.
     * http://wbec-ridderkerk.nl/html/UCIProtocol.html), stop, ponderhit, quit
     *
     */
    private static final String NAME = "Projekt Schachengine 2013\n";
    private static final String AUTHOR = "Projektgruppe Schachengine\n";
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
    private String fen;
    private boolean stop;

    public UCI() {
        //FEN initialisiert mit Standard Startposition
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        reader = new BufferedReader(new InputStreamReader(System.in));
        stop = false;
    }

    @Override
    public void input() throws IOException {
        String cmdIN = null;
        String[] cmdArray = null;
        String cmd = null;
        int movesIndex = -1;
        while (!cmdIN.equals(QUIT)) {
            cmdIN = reader.readLine();
            cmdArray = cmdIN.split(SPLITPOINT);
            //nimmt das erste "Wort" des Befehls und konvertiert ihn in 
            //Kleinbuchstaben
            cmd = cmdArray[0].toLowerCase();

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
                    movesIndex = cmdIN.indexOf(MOVES);
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
                    break;
                case STOP:
                    stop = true;
                    break;
                default:
                    // TODO falsche kommandos sollen ignoriert werden. Keine fehlermeldung?
                    System.err.println(UNKNOWN_CMD);
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
}