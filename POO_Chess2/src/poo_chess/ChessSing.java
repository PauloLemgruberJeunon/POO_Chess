/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess;

import java.util.List;
import poo_chess.View.BoardUpdater;
import poo_chess.View.DeskChessFrame;
import poo_chess.View.SettingsFrame;
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
    private final static DeskChessFrame chessView  = new DeskChessFrame(boardUpdater);
    private final static SettingsFrame settingsView = new SettingsFrame();
    private static long autoSaveTime = 15000;
    
    private final static Thread autoSave = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    controller.save();
                    try {
                        Thread.sleep(autoSaveTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    
    private static final ChessSing chessSing = new ChessSing();
    
    private ChessSing() {
        controller.addView(chessView);
        controller.addLogicalChess(chess);
        chessView.addController(controller);
        boardUpdater.registerObserver(chessView);
        controller.startMainWindow();
    }
    
    public static void startAutoSave() {
        autoSave.start();
    }
    
    public static ChessSing chessSing() {
        return chessSing;
    }
    
    public static void updateChess(Chess newChess) {
        chess = newChess;
        boardUpdater.updateBoard(chess.board());
        controller.updateLogicalChess(chess);
    }
    
    public static void updateAutoSaveTime(float timeSec) {
        autoSaveTime = (long) (1000*timeSec);
    }
    
    public static void settingsViewVisible(boolean on, DeskChessFrame frame) {
        settingsView.setVisible(on);
    }
    
    public static void isCheck(boolean check, boolean checkMate) {
        chessView.isCheck(check, checkMate);        
    }
}
