/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import poo_chess.Color;
import java.util.Scanner;
import poo_chess.controller.field.Board;
import poo_chess.Position;
import poo_chess.View.*;

/**
 *
 * @author paulojeunon
 */
public class Chess {
    
    private final Player player1;
    private final Player player2;
    private final Board board;
    private DeskChessFrame view;
    private String str1, str="", stri , error = "\n Invalid position selected", Checkmate;
    private int counter, teste; 
    private Piece selectedPiece;
    private Player currPlayer;
    private Player advPlayer;
    private Position goToPos;
    private List<Position> movablePos;
    private boolean isPieceSelected;
    
    public Chess(Board board){
        this.board = board;
        this.player1 = new Player(this.setPlayerName(1), "up", Color.BLACK, board);
        this.player2 = new Player(this.setPlayerName(2), "down", Color.WHITE, board); 
        this.counter = 0;
        this.isPieceSelected = false;
        this.currPlayer = this.player1;
        this.advPlayer = this.player2;
    }
    
    public void selectPiece(Position clickPos) {
        teste = 1;
        selectedPiece = currPlayer.selectPiece(clickPos);
        
        if(selectedPiece == null) {
            this.isPieceSelected = false;
            return;
        } 
        System.out.printf(selectedPiece.toString());
        
        str1 = (selectedPiece.getPieceName() + " " + selectedPiece.getColorString() + " || " + selectedPiece.getSquare().getMyPosition());
        movablePos = selectedPiece.getMovablePositionsWithRefresh();
        
        for(Position i : movablePos) {
            System.out.printf("\n" + i.toString());
            str = (str + "\n" + i.toString());
        }
        
        //str = str1 + "\n" +str;
        currPlayer.simulateMovement(selectedPiece, movablePos, advPlayer.getMyArmy());
                
        this.isPieceSelected = true;
                
        selectedPiece.setMovablePosHighlightValue(true);
    }

    public void selectGoToPosAndMove(Position clickGoToPos) {
        goToPos = currPlayer.selectGoToPos(selectedPiece, movablePos, clickGoToPos);
        
        if(goToPos != null) {
            counter++;
            
            if(selectedPiece.getSquare().getMyPosition().equals(currPlayer.getKingPos())){
                currPlayer.setKingPos(goToPos);
            }
            
            currPlayer.movePieceToPosition(selectedPiece, goToPos, false);
            
            boolean isCheckMate = advPlayer.isCheckMate(selectedPiece, currPlayer.getMyArmy());
            
            Player changed = currPlayer;
            currPlayer = advPlayer;
            advPlayer = changed;
                        
            if(isCheckMate) {
                this.Checkmate = ("\n\n Checkmate: " + currPlayer.getName() + " has won the game!!!");
                System.out.printf(stri);
                //view.logArea(stri);
                System.exit(0);
                teste = 3;
                
            }
        } else {
            System.out.printf(error);
            //view.logArea(error);
            teste = 2;
        }
        
        this.isPieceSelected = false;
    }

public String toLog(){
        switch (teste) {
            case 1:
                stri = str1 + str;
                break;
            case 2:
                stri = error;
                break;
            case 3:
                stri = Checkmate;
                break;
            default:
                break;
        }
        return stri;
    }    
    
//    public void startChess(){
//        
//        counter = 0;
//        
//        while(true){
//            this.board.printBoard();
//            
//            // calculates the turns
//            if(counter % 2 == 0){
//                currPlayer = this.player1;
//                advPlayer = this.player2;
//            } else {
//                currPlayer = this.player2;
//                advPlayer = this.player1;
//            }
//            
//            System.out.printf("\n\n Turn of player: " + currPlayer.getName());
//                        
//            while(true){
//                selectedPiece = currPlayer.selectPiece();
//                movablePos = selectedPiece.getMovablePositions();
//                                
//                currPlayer.simulateMovement(selectedPiece, movablePos, advPlayer.getMyArmy());
//                
//                selectedPiece.setMovablePosHighlightValue(true);
//                
//                goToPos = currPlayer.selectGoToPos(selectedPiece, movablePos);
//                
//                if(goToPos != null){                    
//                    break;
//                }
//            }
//            
//            if(selectedPiece.getSquare().getMyPosition().equals(currPlayer.getKingPos())){
//                currPlayer.setKingPos(goToPos);
//            }
//            
//            currPlayer.movePieceToPosition(selectedPiece, goToPos, false);
//                            
//            long startTime = System.nanoTime();
//            boolean isCheckMate = advPlayer.isCheckMate(selectedPiece, currPlayer.getMyArmy());
//            long stopTime = System.nanoTime();
//            
//            System.out.printf("\n\n execTime = " + ((stopTime - startTime)/1000000));
//            
//            counter++;
//            
//            if(isCheckMate) {
//                System.out.printf("\n\n Checkmate: " + currPlayer.getName() + " has won the game!!!");
//                break;
//            }
//        }
//    }
    
    private String setPlayerName(int i){
        
        Scanner reader = new Scanner(System.in);
        
        System.out.println("\n\n Type the name of player " + i + ": ");
        return reader.nextLine();
    }
    
    public boolean isPieceSelected() {
        return this.isPieceSelected;
    }
    
}
