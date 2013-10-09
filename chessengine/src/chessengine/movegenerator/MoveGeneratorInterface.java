package chessengine.movegenerator;

import java.util.LinkedList;


/**
 * @author Schuhmacher, Kaub
 */
public interface MoveGeneratorInterface {
	public LinkedList<String> getZuege();
	public void setFEN(String aktuelleFEN);
}
 