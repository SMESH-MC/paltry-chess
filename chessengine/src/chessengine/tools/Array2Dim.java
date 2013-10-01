package chessengine.tools;
/**
 * 
 * @author Philip Hunsicker
 * datum 30.09.2013
 */
public class Array2Dim extends Brett{

	
	
	
	Figur[][] schachBrett;
	
	public Array2Dim (){
		super();
		schachBrett = new Figur[8][8];
	}
	
	

		@Override
		public Brett copy() {
			Array2Dim neuesBrett = new Array2Dim();
			for(int y =0 ;y <8;y++)
			{
				for (int x =0; x < 8 ; x++ ){
						if( schachBrett[x][y] != null){
							Figur neueFigur = new Figur( schachBrett[x][y].getZahl());	
							neuesBrett.setInhalt(x, y, neueFigur);
						}
					}
				}

			return neuesBrett;
			
		}
		@Override
		public Figur getInhalt(int x, int y) {
			
			return schachBrett[x][y];
		}
		@Override
		public boolean getIsEmpty(int x, int y) {
			return schachBrett[x][y] == null;
		}
		@Override
		public void setEmpty(int x, int y) {
			schachBrett[x][y] = null;
		}
		@Override
		public void setInhalt(int x, int y, Figur inhalt) {
			schachBrett[x][y] = inhalt;
		}
		public String toString(){
			StringBuffer buffer= new StringBuffer();
			buffer.append("X||0|1|2|3|4|5|6|7 \n");
			buffer.append("------------------- \n");
			for(int i = 7; i >= 0 ;i--){
				buffer.append(i+"||");
				for(int k = 0 ; k < 8; k++){
					
					if(this.getIsEmpty(k, i) == false ){
					
					
						buffer.append( this.getInhalt(k, i) + "|");//\t
					}else{
					
						buffer.append(" |");
					
					}
				
				}
				buffer.append("\n");
			}
			buffer.append("-------------------\n ");
			return buffer.toString();
		}
}
