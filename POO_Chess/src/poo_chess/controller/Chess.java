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
    
    public Chess(){
        this.board = new Board();
        this.player1 = new Player(this.setPlayerName(1), "up", Color.BLACK, board);
        this.player2 = new Player(this.setPlayerName(2), "down", Color.WHITE, board);        
    }
    
    public void startChess(){
        
        int counter = 0;
        
        while(true){
            this.board.printBoard();
            
            Player currPlayer;
            Player advPlayer;
            Piece selectedPiece;
            Position goToPos;
            
            // calculates the turns
            if(counter % 2 == 0){
                currPlayer = this.player1;
                advPlayer = this.player2;
            } else {
                currPlayer = this.player2;
                advPlayer = this.player1;
            }
            
            System.out.printf("\n\n Turn of player: " + currPlayer.getName());
            
            List<Position> movablePos;
            
            while(true){
                selectedPiece = currPlayer.selectPiece();
                movablePos = selectedPiece.getMovablePositions();
                                
                currPlayer.simulateMovement(selectedPiece, movablePos, advPlayer.getMyArmy());
                
                selectedPiece.setMovablePosHighlightValue(true);
                
                goToPos = currPlayer.selectGoToPos(selectedPiece, movablePos);
                
                if(goToPos != null){                    
                    break;
                }
            }
            
            if(selectedPiece.getSquare().getMyPosition().equals(currPlayer.getKingPos())){
                currPlayer.setKingPos(goToPos);
            }
            
            currPlayer.movePieceToPosition(selectedPiece, goToPos, false);
                            
            long startTime = System.nanoTime();
            boolean isCheckMate = advPlayer.isCheckMate(selectedPiece, currPlayer.getMyArmy());
            long stopTime = System.nanoTime();
            
            System.out.printf("\n\n execTime = " + ((stopTime - startTime)/1000000));
            
            counter++;
            
            if(isCheckMate){
                System.out.printf("\n\n Checkmate: " + currPlayer.getName() + " has won the game!!!");
                break;
            }
        }
    }
    
    private String setPlayerName(int i){
        
        Scanner reader = new Scanner(System.in);
        
        System.out.println("\n\n Type the name of player " + i + ": ");
        return reader.nextLine();
    }
    
    
    
}
