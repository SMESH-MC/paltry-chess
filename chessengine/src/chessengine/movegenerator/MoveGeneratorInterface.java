package chessengine.movegenerator;

import java.util.LinkedList;


/**
 * @author Schuhmacher, Kaub
 * @version 201309082306
 */
public interface MoveGeneratorInterface {
	public LinkedList<String> getZuege();
	public void setFEN(String aktuelleFEN);
}
