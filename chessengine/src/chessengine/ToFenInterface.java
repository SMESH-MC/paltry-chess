package chessengine;

/**
 * Interface fuer die Klasse ToFen
 * @author Christopher Schuetz
 *
 */
public interface ToFenInterface {

	/**
	 * Methode zum Uebergeben des fertigen FEN Strings
	 * @return der fertige FEN String
	 */
	public abstract String getFEN();

}