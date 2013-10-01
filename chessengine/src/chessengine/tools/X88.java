package chessengine.tools;

public class X88 extends Brett{

	Figur[] schachBrett;
	
	public X88 (){
		super();
		schachBrett = new Figur[128];
	}

	@Override
	public Brett copy() {
		X88 neuesBrett = new X88();
		for(int i =0 ;i <128;i++)
		{
		
			if( schachBrett[i] != null){
				Figur neueFigur = new Figur( schachBrett[i].getZahl());	
				neuesBrett.schachBrett[i] = neueFigur;
			}
		}
			
		return neuesBrett;
	}

	@Override
	public Figur getInhalt(int x, int y) {
		
		return schachBrett[getPosition(x,y)];
	}

	@Override
	public boolean getIsEmpty(int x, int y) {
		
		return getInhalt(x,y) == null;
	}

	@Override
	public void setEmpty(int x, int y) {
		setInhalt(x,y, null);
		
	}

	@Override
	public void setInhalt(int x, int y, Figur inhalt) {
		
		
		schachBrett[getPosition(x,y)] = inhalt;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int getPosition(int x, int y){
		return x + (y * 16);
	}
	
	
	
	
}
