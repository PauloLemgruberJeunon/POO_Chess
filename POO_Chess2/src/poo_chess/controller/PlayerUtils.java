/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import poo_chess.Color;
import poo_chess.Position;
import poo_chess.controller.field.Board;

/**
 *
 * @author paulojeunon
 */
public class PlayerUtils implements java.io.Serializable{
    
    static List<Piece> placeArmyOnBoard(String side, Color color, Board board){
        
        List<Piece> pieces = new ArrayList<>();
        int i, j, upperLimit;
        
        String direction;
        if(side.equals("up")){
            i = 0;
            upperLimit = 2;
            direction = "down";
        } else {
            i = 6;
            upperLimit = 8;
            direction = "up";
        }

        for(; i < upperLimit; i++){
            for(j = 0; j < 8; j++){
                if(i == 1 || i == 6){
                    pieces.add(new Pawn(color, board.getSquare(new Position(i, j)), direction, board));
                } else if(j == 0 || j == 7){
                    pieces.add(new Rook(color, board.getSquare(new Position(i, j)), direction, board));
                } else if(j == 1 || j == 6){
                    pieces.add(new Knight(color, board.getSquare(new Position(i, j)), direction, board));
                } else if(j == 2 || j == 5){
                    pieces.add(new Bishop(color, board.getSquare(new Position(i, j)), direction, board));
                }  else if(j == 3){
                    pieces.add(new Queen(color, board.getSquare(new Position(i, j)), direction, board));
                } else if(j == 4){
                    pieces.add(new King(color, board.getSquare(new Position(i, j)), direction, board));
                }
            }
        }
        
        return pieces;
    }
    
    static Position enterCoordinates(String message){
        
        Scanner reader = new Scanner(System.in);
        
        System.out.println(message);
        int i = reader.nextInt();
        int j = reader.nextInt();
        
        return (new Position(i, j));
    }
    
    public static boolean isKingInCheck(Position kingPos, List<Piece> enemyArmy){
                
        // iterates over all the enemy pieces
        for(Piece enemy : enemyArmy){
            if(enemy.isKilled()) {
                continue;
            }
            // for each enemy piece iterates over all it's movable positions
            for(Position pos : enemy.getMovablePositionsWithRefresh()){
                if(pos.equals(kingPos)){
                    return true;
                }
            }
        } 
        
        return false;
    }
    
}
