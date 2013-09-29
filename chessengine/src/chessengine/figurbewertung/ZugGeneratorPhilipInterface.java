package chessengine.figurbewertung;

import java.util.LinkedList;

public interface ZugGeneratorPhilipInterface {

	public abstract LinkedList<String> ermittleAlleZuege(String fen);

}