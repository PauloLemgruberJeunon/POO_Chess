/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller.field;
import java.util.*;
import poo_chess.controller.Piece;
import poo_chess.Position;
import poo_chess.Color;

/**
 *
 * @author paulojeunon
 */
public class Board {
    
    private static final int BOARDSIZE = 8;
    private final List<Square> boardSquares;
    
    public Board(){
        this.boardSquares = this.makeBoard();
    }
    
    public Board(Board bd){
        this.boardSquares = new ArrayList<>(bd.boardSquares);
    }
    
    // Construct the board with all of its squares 
    private List<Square> makeBoard(){
        List<Square> squareList = new ArrayList<>();
        int colorCounter = 0;
        Color color;
        for(int i = 0; i < BOARDSIZE; i++){
            for(int j = 0; j < BOARDSIZE; j++, colorCounter++){
                color = colorCounter % 2 == 0 ? Color.WHITE : Color.BLACK;
                squareList.add(new Square(color, new Position(i, j)));
            }
        }
        
        return squareList;
    }
    
    // Get the square based on its position
    // TODO: make a Hash table for direct search since the coordinates of each square are unique
    public Square getSquare(Position position){
        if(position.getIsValid() == false){
            System.out.printf("[WARNING] You have sent an invalid Position: ");
            position.printPos();
        }
        for(Square sqr : this.boardSquares){
            if(sqr.getMyPosition().equals(position)){
                return sqr;
            }
        }
        return null;
    }
    
    public List<Square> getSquareList(){
        return this.boardSquares;
    }
    
    
    // Print the board with the pieces above it for debbug and play without the GUI
    public void printBoard(){
        
        for(int i = 0; i < 8; i++){
            if(i == 0){
                System.out.printf("   *" + i);
            } else {
                System.out.printf(" *" + i);
            }
        }
        
        System.out.printf("\n");
        
        for(int i = 0; i < 8; i++){
            System.out.printf("*" + i);
            for(int j = 0; j < 8; j++){
                Piece piece = this.getSquare(new Position(i ,j)).getPieceAbovaMe();
                if(piece != null && piece.isKilled() == false){
                    String pieceName = piece.getPieceName();
                    System.out.printf(" " + pieceName);
                } else {
                    System.out.printf(" XX");
                }
                
                if(j == 7){
                    System.out.printf("\n");
                }
                
            }
        }
    }
    
}