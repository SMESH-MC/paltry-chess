package chessengine;

/**
 *
 * @author Alexander Kessler
 */
public class Manager {

    private UCI newUCI;
    private boolean stop;
    
    public Manager() {
        stop = false;
    }

    /**
     * Initialisiert den Manager fuer den ersten gebrauch.
     */
    public void initialize (){
        try {
            newUCI = new UCI();
            //muss noch in einen extra Thread
            newUCI.input();
            stop = newUCI.getStop();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Setter fuer die stop-Variable.
     * @param stop neuer Wert fuer stop
     */
    public void setStop(boolean stop){
        this.stop = stop;
    }
    
    /**
     * Getter fuer die stop-Variable.
     * @return 
     */
    public boolean getStop(){
        return stop;
    }
}
