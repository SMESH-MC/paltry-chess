/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

import java.io.IOException;

/**
 *
 * @author Alexander Kessler, Thorsten Jakobs
 */
public interface UCI_Interface {

    /**
     *
     * @param move - der beste Zug, der durch die Engine gefunden wurde
     */
    void bestmove(String move);

    int getBinc();

    int getBtime();

    /**
     * Die Methode getFEN liefert den gespeicherten FEN-String zurueck.
     *
     * @return String FEN-String
     */
    String getFEN();

    int getMovetime();

    int getWinc();

    int getWtime();

    /**
     *
     * @throws IOException
     */
    void input() throws Exception;

    public int getQueenValue();
    
    public int getRookValue();
    
    public int getKnightValue();
  
    public int getBishopValue();
    
    void run();
}
