/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess;

/**
 *
 * @author paulojeunon
 */
public class POO_Chess {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Chess chess = new Chess();
//        chess.startChess();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                ChessSing chessSing = ChessSing.chessSig();

            }
        });
    }    
}
