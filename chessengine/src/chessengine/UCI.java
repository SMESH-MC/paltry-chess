/**
 * The UCI protocol as publiced by Stefan-Meyer Kahlen
 */
package chessengine;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Alexander Kessler
 *
 */
public class UCI {

    public BufferedReader reader;
    /**
     * GUI to engine :(ueber Reader.readln) uci, debug [on | off], isready,
     * setoption name [value], register, ucinewgame, position[fen | startpos ],
     * go "+ weitere Befehle"(s.
     * http://wbec-ridderkerk.nl/html/UCIProtocol.html), stop, ponderhit, quit
     *
     */
    private static final String name = "Projekt Schachengine 2013\n";
    private static final String author = "Projektgruppe Schachengine\n";
    private static final String quit = "quit";
    private static final String uci = "uci";
    private static final String uciok = "uciok";
    private static final String isready = "isready";
    private static final String readyok = "readyok";

    public void input() throws IOException {
        String cmdIN = null;
        reader = new BufferedReader(new InputStreamReader(System.in));
        while (!cmdIN.equals(quit)) {
            cmdIN = reader.readLine();
            // Fallunterscheidung fuer die versch. Befehle
            switch (cmdIN) {
                case uci:
                    id();
                    System.out.println(uciok);
                    break;
                case isready:
                    System.out.println(readyok);
                    break;
                // usw...
            }
        }
    }

    public void id() {
        System.out.print("id name " + name + "id author " + author);
    }
}
