package chessengine;

import java.io.IOException;

/**
 *
 * @author Arax
 */
public interface UCI_Interface {

    /**
     *
     * @param move - der beste Zug, der durch die Engine gefunden wurde
     */
    void bestmove(String move);

    String getFEN();

    boolean getStop();

    void input() throws IOException;
    
}
