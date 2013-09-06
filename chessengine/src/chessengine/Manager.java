package chessengine;

/**
 *
 * @author Alexander Kessler
 */
public class Manager {

    private UCI newUCI;
    private boolean stop = false;

    public void main(String[] Args) {
        try {
            newUCI = new UCI();
            //muss noch in einen extra Thread
            newUCI.input();
            stop = newUCI.stop();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
