package chessengine;

import java.util.LinkedList;


/**
 * @author Schuhmacher, Kaub
 * @version 201309082306
 */
public interface MoveGeneratorInterface {
	public LinkedList<String> getZuege(String aktuelleFEN);
}
