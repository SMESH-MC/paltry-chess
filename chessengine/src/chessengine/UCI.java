/**
 * The UCI protocol as publiced by Stefan-Meyer Kahlen
 */
package chessengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UCI-Klasse, dient zur Kommunikation zwischen Engine und der GUI.
 *
 * @author Alexander Kessler, Thorsten Jakobs
 */
public class UCI implements UCI_Interface, Runnable {

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
    private static final String QUEEN = "Queen_Value";
    private static final String BISHOP = "Bishop_Value";
    private static final String KNIGHT = "Knight_Value";
    private static final String ROOK = "Rook_Value";
    private static final String SETOPTION = "setoption";
    //********Variablen********
    private String fen;
    private boolean stop;
    private BufferedReader reader;
    private int wtime;
    private int btime;
    private int movetime;
    private int winc;
    private int binc;
    private boolean go;
    private Manager manager;
    private String movesList;
    private int queenValue;
    private int bishopValue;
    private int knightValue;
    private int rookValue;

    /**
     * Konstruktor der UCI-Klasse.
     *
     * @param manager Referenz auf den zustaendigen Manager.
     */
    public UCI(Manager manager) {
        //FEN initialisiert mit Standard Startposition
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR "
                + "w KQkq - 0 1";
        reader = new BufferedReader(new InputStreamReader(System.in));
        stop = false;
        wtime = 999999999;
        btime = 999999999;
        winc = 0;
        binc = 0;
        movetime = 60000;
        go = false;
        this.manager = manager;
        movesList = null;
        queenValue = 900;
        bishopValue = 300;
        knightValue = 300;
        rookValue = 500;
    }

    /**
     * input-Methode, liest Eingaben aud stdin ein und wertet diese aus.
     *
     * @throws IOException
     */
    @Override
    public void input() throws Exception {
        String cmdIN = null;
        String[] cmdArray = null;
        String cmd = null;

        do {
            cmdIN = reader.readLine();
            //konvertiert den Befehl in Kleinbuchstaben
            cmdIN = cmdIN.toLowerCase();
            cmdArray = cmdIN.split(SPLITPOINT);
            cmd = cmdArray[0];

            // Fallunterscheidung fuer die versch. Befehle
            switch (cmd) {
                case UCI:
                    id();
                    options();
                    System.out.println(UCIOK);
                    break;
                case ISREADY:
                    System.out.println(READYOK);
                    break;
                case POSITION:
                    position(cmdIN, cmdArray);
                    break;
                case STOP:
                    manager.setStop(true);
                    break;
                case GO:
                    go = true;
                    go(cmdArray);
                    break;
                case SETOPTION:
                    setoption(cmdArray);
                    break;
            }
        } while (!cmdIN.equals(QUIT));
        System.exit(0);
    }

    /**
     * Gibt die ID der Engine auf stdout aus.
     */
    private void id() {
        System.out.println("id name " + NAME + "\nid author " + AUTHOR);
    }

    private void options() {
        System.out.println("option name " + QUEEN + " type spin default 900 "
                + "min 100 max 1000\n");
        System.out.println("option name " + ROOK + " type spin default 900 "
                + "min 100 max 1000\n");
        System.out.println("option name " + BISHOP + " type spin default 900 "
                + "min 100 max 1000\n");
        System.out.println("option name " + KNIGHT + " type spin default 900 "
                + "min 100 max 1000\n");
    }

    private void position(String cmdIN, String[] cmdArray) {
        int movesIndex = cmdIN.indexOf(MOVES);

        if (cmdArray[1].equalsIgnoreCase("fen")) {
            String newFen = null;
            if (movesIndex == -1) {
                for (int i = 2; i < cmdArray.length; i++) {
                    newFen += cmdArray[i] + " ";
                    manager.setWhite();
                }
            } else {
                for (int i = 2; i < movesIndex; i++) {
                    newFen += cmdArray[i] + " ";
                }
                for (int j = movesIndex + 1; j < cmdArray.length; j++) {
                    movesList += cmdArray[j] + " ";
                }
            }
            fen = newFen;
        } else {
            if (movesIndex == -1) {
                manager.setWhite();
            } else {
                for (int j = movesIndex + 1; j < cmdArray.length; j++) {
                    movesList += cmdArray[j] + " ";
                }
            }
        }
    }

    private void setoption(String[] cmdArray) {
        /**
         * Queen_Value Bishop_Value Night_Value Rook_Value
         */
        switch (cmdArray[2]) {
            case QUEEN:
                queenValue = Integer.parseInt(cmdArray[4]);
                break;
            case BISHOP:
                bishopValue = Integer.parseInt(cmdArray[4]);
                break;
            case KNIGHT:
                knightValue = Integer.parseInt(cmdArray[4]);
                break;
            case ROOK:
                rookValue = Integer.parseInt(cmdArray[4]);
                break;
        }
    }

    /**
     * go-Methode, empfaengt die Einstellungen fuer das go-Kommando und leitet
     * den aufruf an die Engine weiter.
     *
     * @param cmdArray
     */
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

        manager.run();
    }

    /**
     * bestmove-Methode, gibt den von der Engine errechneten Zug in die stdout
     * aus und setzt die go- und stop-variable wieder auf false zurueck.
     *
     * @param move - der beste Zug, der durch die Engine gefunden wurde
     */
    @Override
    public void bestmove(String move) {
        //Stop wird auf false gesetzt, fuer den naechsten Zug
        manager.setStop(false);
        go = false;
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

    /**
     * Getter fuer wtime.
     *
     * @return wtime
     */
    @Override
    public int getWtime() {
        return wtime;
    }

    /**
     * Getter fuer btime
     *
     * @return btime
     */
    @Override
    public int getBtime() {
        return btime;
    }

    /**
     * Getter fuer winc
     *
     * @return winc
     */
    @Override
    public int getWinc() {
        return winc;
    }

    /**
     * Getter fuer binc
     *
     * @return binc
     */
    @Override
    public int getBinc() {
        return binc;
    }

    /**
     * Getter fuer movetime
     *
     * @return movetime
     */
    @Override
    public int getMovetime() {
        return movetime;
    }

    /**
     * Getter fuer moveList
     *
     * @return moveList
     */
    public String getMovesList() {
        return movesList;
    }

    /**
     * Getter-Methode fuer den Wert der Dame.
     *
     * @return queenValue
     */
    public int getQueenValue() {
        return queenValue;
    }

    /**
     * Getter-Methode fuer den Wert des Turms.
     *
     * @return rookValue
     */
    public int getRookValue() {
        return rookValue;
    }

    /**
     * Getter-Methode fuer den Wert des Springers.
     *
     * @return knightValue
     */
    public int getKnightValue() {
        return knightValue;
    }

    /**
     * Getter-Methode fuer den Wert des Laeufers.
     *
     * @return bishopValue
     */
    public int getBishopValue() {
        return bishopValue;
    }

    private void writeValues(File datei) throws IOException {
        try (FileWriter ausgabe = new FileWriter(datei)) {
            ausgabe.write(queenValue + "\n" + rookValue + "\n" + knightValue
                    + "\n" + bishopValue);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UCI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readValues(File datei) throws IOException {
        //FileReader eingabe = null;

        try (LineNumberReader eingabe =
                        new LineNumberReader(
                        new FileReader(datei));) {
            String zeile = null;
            while ((zeile = eingabe.readLine()) != null) {
                switch (eingabe.getLineNumber()) {
                    case 1:
                        queenValue = Integer.parseInt(zeile);
                        break;
                    case 2:
                        rookValue = Integer.parseInt(zeile);
                        break;
                    case 3:
                        knightValue = Integer.parseInt(zeile);
                        break;
                    case 4:
                        bishopValue = Integer.parseInt(zeile);
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UCI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Threadstart fuer das Einlesen der Kommandos.
     */
    @Override
    public void run() {
        try {
            input();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}