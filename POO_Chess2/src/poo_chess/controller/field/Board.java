/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller.field;
import java.util.*;
import poo_chess.Position;
import poo_chess.Color;

/**
 *
 * @author paulojeunon
 */
public class Board implements java.io.Serializable{
    
    private static final int BOARDSIZE = 8;
    //has a hashMap to store all of the board squares for direct access
    private final HashMap boardSquares;
    
    public Board(){
        this.boardSquares = this.makeBoard();
    }
    
    // Construct the board with all of its squares 
    private HashMap makeBoard(){
        HashMap squareMap = new HashMap();
        int colorCounter = 0;
        Color color;
        for(int i = 0; i < BOARDSIZE; i++){
            for(int j = 0; j < BOARDSIZE; j++, colorCounter++){
                color = colorCounter % 2 == 0 ? Color.WHITE : Color.BLACK;
                Position pos = new Position(i, j);
                squareMap.put(pos.getKey(), new Square(color, pos));
            }
        }
        
        return squareMap;
    }
    
    // Get the square based on its position
    // TODO: make a Hash table for direct search since the coordinates of each square are unique
    public Square getSquare(Position position){
        float key = position.getKey();
        Square tmp = (Square)this.boardSquares.get(key);
        if(tmp == null){
            System.out.printf("\n\n Did not find the piece returning null...");
            return null;
        }
        
        return tmp;
    }
    
    // Get a square from the board by inserting the coordinates from the board 
    public Square getSquare(float vertical, float horizontal){
        
        float key = Position.getKeyByCoords(vertical, horizontal);
        Square tmp = (Square)this.boardSquares.get(key);
        if(tmp == null){
            System.out.printf("\n\n Did not find the peice returning null...");
        }
        
        return tmp;
    }
    
    public HashMap getSquareHash(){
        return this.boardSquares;
    }
}