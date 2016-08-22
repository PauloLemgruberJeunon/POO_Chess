/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;

import java.util.List;
import java.util.ArrayList;
import poo_chess.Position;

/**
 *
 * @author paulojeunon
 */
public final class ChessUtils { 
    
    public static List<Piece> isKingInCheck(Position kingPos, List<Piece> enemyArmy){
        
        List<Piece> threats = new ArrayList<>();
        
        for(Piece enemy : enemyArmy){
            for(Position pos : enemy.getMovablePositions()){
                if(pos.equals(kingPos)){
                    threats.add(enemy);
                }
            }
        }
        
        return threats;
    }
    
    //public static boolean kingHasToMoveAnyway(List<Piece> threats){
        
    //    for(Piece enemy : threats){
            
    //    }
    //}
}
