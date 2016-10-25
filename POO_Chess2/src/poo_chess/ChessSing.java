/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess;

import poo_chess.View.BoardUpdater;
import poo_chess.View.DeskChessFrame;
import poo_chess.controller.Chess;
import poo_chess.controller.Controller;
import poo_chess.controller.field.Board;

/**
 *
 * @author paulojeunon
 */
public class ChessSing {        
        
    private final static Controller controller = new Controller();
    private static Chess chess = new Chess(new Board()); 
    private static BoardUpdater boardUpdater = new BoardUpdater(chess.board());
    private final static DeskChessFrame view  = new DeskChessFrame(boardUpdater);
    
    private final static Thread autoSave = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    controller.save();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    
    private static final ChessSing chessSing = new ChessSing();
    
    private ChessSing() {
        controller.addView(view);
        view.addController(controller);
        controller.addLogicalChess(chess);
        boardUpdater.registerObserver(view);
        controller.startMainWindow();
    }
    
    public static void startAutoSave() {
        autoSave.start();
    }
    
    public static ChessSing chessSig() {
        return chessSing;
    }
    
    public static void updateChess(Chess newChess) {
        chess = newChess;
        boardUpdater.updateBoard(chess.board());
        controller.updateLogicalChess(chess);
    }
    
}
