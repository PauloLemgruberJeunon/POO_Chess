/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import poo_chess.Color;
import java.util.Scanner;
import poo_chess.controller.field.Board;

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
            
            if(counter % 2 == 0){
                this.player1.myTurn();
            } else {
                this.player2.myTurn();
            }
            
            counter++;
            
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
