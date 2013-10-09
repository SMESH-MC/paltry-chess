package chessengine.tools;

public class ProtokollSegment {

	private String fen;
	private String meldung;
	
	public ProtokollSegment(String fen, String meldung){
		setFen(fen);
		setMeldung(meldung);
		
	}
	
	
	public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	public String getMeldung() {
		return meldung;
	}
	public void setMeldung(String meldung) {
		this.meldung = meldung;
	}
	
}
