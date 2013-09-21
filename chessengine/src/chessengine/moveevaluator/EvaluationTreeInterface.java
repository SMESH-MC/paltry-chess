package chessengine.moveevaluator;

public interface EvaluationTreeInterface {

	public abstract void MinMax();

	public abstract void bewertung();

	public abstract void getCurrentBoard();

	public abstract void setCurrentBoard();

	public abstract void macheZug();

	public abstract void macheFakeZug();

	public abstract void macheStartZug();

}