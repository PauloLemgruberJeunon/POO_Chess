/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
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
            
            if(counter % 2 == 0){
                currPlayer = this.player1;
                advPlayer = this.player2;
            } else {
                currPlayer = this.player2;
                advPlayer = this.player1;
            }
            
            selectedPiece = currPlayer.selectPiece();
            goToPos = currPlayer.selectGoToPos(selectedPiece);
            
            boolean moveSuccessful = currPlayer.simulateMovement(selectedPiece, goToPos, advPlayer.getMyArmy());
            
            if(moveSuccessful){
                counter++;
            } else {
                System.out.printf("\n\n Your move will let the piece in check, try again");
            }
            
            if(counter > 10){
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
