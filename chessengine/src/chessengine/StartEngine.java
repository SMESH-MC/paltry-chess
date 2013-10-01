/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

/**
 * Klasse zum starten der Schachengine.
 * @author Thorsten Jakobs, Alexander Kessler
 */
public class StartEngine {
    /**
     * Startpunkt fuer die Schachengine.
     * @param args 
     */
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.initialize();
    }
}
