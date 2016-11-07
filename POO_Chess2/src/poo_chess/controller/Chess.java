/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import exception.InvalidMovementException;
import exception.InvalidPlayerException;
import java.util.List;
import poo_chess.Color;

import poo_chess.ChessSing;
import poo_chess.controller.field.Board;
import poo_chess.Position;
//import poo_chess.View.*;

/**
 *
 * @author paulojeunon
 */
public class Chess implements java.io.Serializable {
    
    private final Player player1;
    private final Player player2;
    private final Board board;
    private String str1, str = "", stri, Checkmate;
    private int logState; 
    private Piece selectedPiece;
    private Player currPlayer;
    private Player advPlayer;
    private Position goToPos;
    private List<Position> movablePos;
    private boolean isPieceSelected;
    
    public Chess(Board board){                        
        this.board = board;
        this.player1 = new Player(ChessSing.getPlayerName(1), "up", Color.BLACK, board);
        this.player2 = new Player(ChessSing.getPlayerName(2), "down", Color.WHITE, board);
        this.isPieceSelected = false;
        this.currPlayer = this.player1;
        this.advPlayer = this.player2;
    }
    
    public void selectPiece(Position clickPos) {
        logState = 1;
        try {
            selectedPiece = currPlayer.selectPiece(clickPos);
        } catch (InvalidPlayerException ex) {
            this.isPieceSelected = false;
            logState = 4;
            return;
        }
         
        System.out.printf(selectedPiece.toString());
        
        str1 = (selectedPiece.getPieceName() + " " + selectedPiece.getColorString() + " || " + selectedPiece.getSquare().getMyPosition());
        movablePos = selectedPiece.getMovablePositionsWithRefresh();
        
        str = "";
        for(Position i : movablePos) {            
            System.out.printf("\n" + i.toString());
            str = (str + "\n" + i.toString());
        }
        
        currPlayer.simulateMovement(selectedPiece, movablePos, advPlayer.getMyArmy());
                
        this.isPieceSelected = true;
                
        selectedPiece.setMovablePosHighlightValue(true);
    }
    
    public void selectGoToPosAndMove(Position clickGoToPos) {
        try {
            goToPos = currPlayer.selectGoToPos(selectedPiece, movablePos, clickGoToPos);
                        
            if(selectedPiece.getSquare().getMyPosition().equals(currPlayer.getKingPos())){
                currPlayer.setKingPos(goToPos);
            }
            
            selectedPiece.setMovablePosHighlightValue(false);
            currPlayer.movePieceToPosition(selectedPiece, goToPos, false);
            
            boolean isCheckMate = advPlayer.isCheckMate(selectedPiece, currPlayer.getMyArmy());
            
            Player changed = currPlayer;
            currPlayer = advPlayer;
            advPlayer = changed;
                        
            if(isCheckMate) {
                this.Checkmate = ("\n\n Checkmate: " + currPlayer.getName() + " has won the game!!!");
                System.out.printf(stri);
                ChessSing.isCheck(false, true);
                System.exit(0);
                logState = 3;
                
            }         
        } catch (InvalidMovementException ex) {
            logState = 2;        
        }

        this.isPieceSelected = false;        
    }

    public String toLog(){
        switch (logState) {
            case 1:
                stri = str1 + str;
                break;
            case 2:
                stri = new InvalidMovementException().getMessage();
                break;
            case 3:
                stri = Checkmate;
                break;
            case 4:
                stri = new InvalidPlayerException().getMessage();
            default:
                break;
        }
        return stri;
    }
    
    public boolean isPieceSelected() {
        return this.isPieceSelected;
    }
    
    public Board board() {
        return this.board;
    }
    
    public Player getCurrPlayer() {
        return currPlayer;
    }
    
}
