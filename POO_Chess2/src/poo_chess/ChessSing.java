/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess;

import javax.swing.JOptionPane;
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
    
    private static Controller controller;
    private static Chess chess; 
    private static BoardUpdater boardUpdater;
    private static DeskChessFrame chessView;
    private static SettingsFrame settingsView;
    private static long autoSaveTime;
    
    private final static Thread autoSave = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    controller.save();
                    try {
                        Thread.sleep(autoSaveTime);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
    
    private static final ChessSing chessSing = new ChessSing();
    
    private ChessSing() {                
        
        controller = new Controller();        
        settingsView = new SettingsFrame();
        autoSaveTime = 15000;
        
        String message = "'y' to load and 'n' to start a new game";
        String loadAnswer = JOptionPane.showInputDialog(null, message, "Chess game", JOptionPane.QUESTION_MESSAGE);
        
        if(loadAnswer != null && "y".equals(loadAnswer)) {
            boardUpdater = new BoardUpdater(null);
            controller.load();
        } else {
            chess = new Chess(new Board());
            boardUpdater = new BoardUpdater(chess.board());
        }
                
        chessView = new DeskChessFrame(boardUpdater);
        
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
    
    public static String getPlayerName(int playerNum) {
        String answer = JOptionPane.showInputDialog(null, "Enter with the name of the player", "Chess game", JOptionPane.QUESTION_MESSAGE);
        if(answer == null || "".equals(answer) || " ".equals(answer)) {
            answer = "Player " + playerNum;
        }
        
        return answer;
    }
}
