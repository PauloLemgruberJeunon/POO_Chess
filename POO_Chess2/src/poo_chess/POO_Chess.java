/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess;
import poo_chess.controller.Chess;
import poo_chess.controller.Controller;
import poo_chess.View.*;
import poo_chess.controller.field.Board;


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
                Board board = new Board();
                BoardUpdater boardUpdater = new BoardUpdater(board);
                DeskChessFrame view = new DeskChessFrame(boardUpdater);
                boardUpdater.registerObserver(view);
                Controller controller = new Controller();
                controller.addLogicalChess(new Chess(board));
                controller.addView(view);
                view.addController(controller);
                controller.startMainWindow();
            }
        });
    }
    
}
