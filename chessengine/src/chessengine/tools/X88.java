package chessengine.tools;

public class X88 extends Brett{

	Figur[] schachBrett;
	
	public X88 (){
		super();
		schachBrett = new Figur[128];
	}

	@Override
	public Brett copy() {
		/*X88 neuesBrett = new X88();
		for(int i =0 ;i <128;i++)
		{
		
			if( schachBrett[i] != null){
				Figur neueFigur = new Figur( schachBrett[i].getZahl());	
				neuesBrett.schachBrett[i] = neueFigur;
			}
		}
			*/
		X88 neuesBrett = new X88();
		System.arraycopy(schachBrett,0,neuesBrett.schachBrett,0,schachBrett.length);
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
		StringBuffer buffer= new StringBuffer();
		buffer.append("X|0|1|2|3|4|5|6|7|8|9|A|B|C|D|E|F \n");
		buffer.append("-------------------------------- \n");
		for(int i = 7; i >= 0 ;i--){
			buffer.append(i+"||");
			for(int k = 0 ; k < 16; k++){
				
				if(this.getIsEmpty(k, i) == false ){
					buffer.append( this.getInhalt(k, i) + "|");//\t
				}else{
				
					buffer.append(" |");
				
				}
			
			}
			buffer.append("\n");
		}
		buffer.append("--------------------------------\n ");
		return buffer.toString();
	}
	
	private int getPosition(int x, int y){
		return x + (y * 16);
	}
	
	
	
	
}
