package tools;

public class Zug extends SchachPosition{

	/*
	 * halbfertig
	 */
	
	private boolean schlaegt;
	private boolean rochade;
	private boolean enPassant;
	private char schlagTyp; //typ der geschlagen Einheit
	private SchachPosition ausgangsPosition;
	private SchachPosition ausgangsPosition2; //ausgangsPositionRochadeTurm und Postion des geschlagenen Bauer bei enpass
	private SchachPosition zielPositionRochadeTurm;
	
	/**
	 * normaler Zug
	 * @param x
	 * @param y
	 * @param ausgangsPosition
	 */
	public Zug(int x, int y, SchachPosition ausgangsPosition) {
		super(x, y);
		this.ausgangsPosition = ausgangsPosition ;
		schlaegt = false;
		this.rochade = false;
		this.enPassant = false;
	}
	


	/**
	 * schlagen
	 * @param x
	 * @param y
	 * @param ausgangsPosition
	 * @param true wenn figur dabei geschlagen wird
	 */
	public Zug(int x, int y, SchachPosition ausgangsPosition, char schlagTyp) {
		super(x, y);
		this.ausgangsPosition = ausgangsPosition ;
		this.schlaegt = true;
		this.rochade = false;
		this.enPassant = false;
		this.schlagTyp = schlagTyp;
	}
	/**
	 * schlagen En Passant
	 * @param x
	 * @param y
	 * @param ausgangsPosition
	 * @param position des zu Schlagenden Bauerns
	 */
	public Zug(int x, int y, SchachPosition ausgangsPosition, SchachPosition schlagPostion, char schlagTyp) {
		super(x, y);
		this.ausgangsPosition = ausgangsPosition ;
		ausgangsPosition2 = schlagPostion;
		this.schlaegt = true;
		this.rochade = false;
		this.enPassant = true;
		this.schlagTyp = schlagTyp;
	}
	/**
	 * Rochade
	 * @param x
	 * @param y
	 * @param ausgangsPosition
	 * @param position des zu Schlagenden Bauerns
	 */
	public Zug(int x, int y, SchachPosition ausgangsPosition, SchachPosition ausgangsPostionTurm, SchachPosition zielDesTurms) {
		super(x, y);
		this.ausgangsPosition = ausgangsPosition ;
		ausgangsPosition2 = ausgangsPostionTurm;
		zielPositionRochadeTurm = zielDesTurms;
		this.schlaegt = false;
		this.rochade = true;
		this.enPassant = false;
	}
	
	
	
	
	
	
	
	
	
	
	public String toString(){
		String ausgabe = "";
		if(schlaegt){
			ausgabe ="|x";
		}
		if(this.enPassant){
			ausgabe ="|e|" + ausgangsPosition2;
		}
		if(this.rochade){
			ausgabe ="|r" + ausgangsPosition2+"nach" + zielPositionRochadeTurm;
		}
		return "("+x+"|"+y+ausgabe+")";
	}

	public boolean isSchlaegt() {
		return schlaegt;
	}

	public boolean isRochade() {
		return rochade;
	}

	public boolean isEnPassant() {
		return enPassant;
	}

	public char getSchlagTyp() {
		return schlagTyp;
	}

	public SchachPosition getAusgangsPosition() {
		return ausgangsPosition;
	}

	public SchachPosition getAusgangsPosition2() {
		return ausgangsPosition2;
	}

	public SchachPosition getZielPositionRochadeTurm() {
		return zielPositionRochadeTurm;
	}

}
