/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import java.util.List;
import poo_chess.Color;
import java.util.Scanner;
import poo_chess.controller.field.Board;
import poo_chess.Position;

/**
 *
 * @author paulojeunon
 */
public class Chess {
    
    private final Player player1;
    private final Player player2;
    private final Board board;
    
    private int counter; 
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
        selectedPiece = currPlayer.selectPiece(clickPos);
        
        if(selectedPiece == null) {
            this.isPieceSelected = false;
            return;
        } 
        
        System.out.printf(selectedPiece.toString());
        
        movablePos = selectedPiece.getMovablePositionsWithRefresh();
        
        for(Position i : movablePos) {
            System.out.printf("\n" + i.toString());
        }
                                
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
                System.out.printf("\n\n Checkmate: " + currPlayer.getName() + " has won the game!!!");
                System.exit(0);
            }
        } else {
            System.out.printf("\n Invalid position selected");
        }
        
        this.isPieceSelected = false;
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
