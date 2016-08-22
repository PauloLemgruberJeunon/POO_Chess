/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import poo_chess.Color;
import poo_chess.Position;
import java.util.*;
import poo_chess.controller.field.Square;
import poo_chess.controller.field.Board;

/**
 *
 * @author paulojeunon
 */
abstract public class Piece{
    protected final String direction;
    protected final Color color;
    private boolean killed;
    protected Square mySquare;
    protected List<Position> movablePositions;
    protected Board board;
    
    protected Piece(Color color, Square mySquare, String direction, Board board){
        this.killed = false;
        this.color = color;
        this.direction = direction;
        this.mySquare = mySquare;
        this.mySquare.setPieceAboveMe(this);
        this.board = board;
    }
    
    protected void killEnemy(Piece enemy){
        enemy.killedInCombat();
    }
    
    protected void killedInCombat(){
        this.killed = true;
    }
    
    public List<Position> getMovablePositions(){
        this.refreshMovablePositions(this.board);
        return this.movablePositions;
    }
    
    // Method to move a peice to some position
    protected void movePiece(Square mvToThisSquare){
        // Checks if the movement is valid
        if(this.movablePositions.contains(mvToThisSquare.getMyPosition()) == false){
            System.out.println("\n\n [WARNING] This Piece can not move to the asked position, this should be checked before... ");
        } else {
            // clear the pointer of the previus square to this peice 
            this.mySquare.clearPieceAboveMe();
            
            // Refresh the position of the piece
            this.mySquare = mvToThisSquare;
            
            Piece pieceAboveMe = this.mySquare.getPieceAbovaMe();
            // Checks if there is an enemy on the position that you moved to
            if(pieceAboveMe != null && pieceAboveMe.color != this.color){
                if(pieceAboveMe.color != this.color){
                    this.killEnemy(pieceAboveMe);
                } else {
                    System.out.println("\n\n [WARNING] The movable position can not be one with our piece... ");
                    return;
                }
            }
            this.mySquare.setPieceAboveMe(this);
        }
    }
    
    public Color getColor(){
        return this.color;
    }
    
    public boolean isKilled(){
        return this.killed;
    }
    
    protected abstract void refreshMovablePositions(Board board);
    
    public abstract String getPieceName();
}
