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
abstract public class Piece implements java.io.Serializable{
    protected final String direction;
    protected final Color color;
    private boolean killed;
    private Square mySquare;
    protected List<Position> movablePositions;
    protected Board board;
    
    protected Piece(Color color, Square mySquare, String direction, Board board){
        this.killed = false;
        this.color = color;
        this.direction = direction;
        this.mySquare = mySquare;
        this.mySquare.setPieceAboveMe(this);
        this.board = board;
        this.refreshAndFilterMovablePos();
    }
    
    protected void killEnemy(Piece enemy){
        enemy.killedInCombat();
    }
    
    protected void killedInCombat(){
        this.killed = true;
    }
    
    protected Square getSquare(){
        return this.mySquare;
    }
    
    public void refreshAndFilterMovablePos() {
        this.refreshMovablePositions(board);
        for(int i=0; i<movablePositions.size(); i++) {
            Piece currPiece = board.getSquare(movablePositions.get(i)).getPieceAbovaMe(); 
            if(currPiece != null && currPiece.getColor() == this.color) {
                movablePositions.remove(i);
                i--;
            }
        }
    }
    
    public List<Position> getMovablePositionsWithRefresh(){
        this.refreshAndFilterMovablePos();
        return this.movablePositions;
    }
    
    // Method to move a peice to some position
    protected void movePiece(Square mvToThisSquare, boolean simulatedMove){
        // Checks if the movement is valid
        if(this.movablePositions.contains(mvToThisSquare.getMyPosition()) == false){
            System.out.println("\n\n [WARNING] This Piece can not move to the asked position, this should be checked before... ");
        } else {
            // if the piece is a pawn will set it's variable "first move" to false, if it's not, wont do nothing
            if(simulatedMove == false){
                pieceHasMoved();
            }
            
            // clear the pointer of the previus square to this peice 
            this.mySquare.clearPieceAboveMe();
            
            // Refresh the position of the piece
            this.mySquare = mvToThisSquare;
            
            Piece pieceAboveMe = mvToThisSquare.getPieceAbovaMe();
            // Checks if there is an enemy on the position that you moved to
            if(pieceAboveMe != null && pieceAboveMe.getColor() != this.color){
                this.killEnemy(pieceAboveMe);
            }
            
            this.mySquare.setPieceAboveMe(this);
        }
    }
    
    protected void forceMovePiece(Square previusSquare, Player player){
        
        if(player.getMyColor() != this.color){
            System.out.printf("\n\n [ACCESS DENIED] Only the owner of the piece can force move it...");
            return;
        } 
        
        this.mySquare.clearPieceAboveMe();
        this.mySquare = previusSquare;
        this.mySquare.setPieceAboveMe(this);
    }
    
    protected void revivePiece(Piece killedEnemy, Player player){
        
        if(player.getMyColor() == this.color){
            System.out.printf("\n\n [ACCESS DENIED] Only the enemy of the piece can revive it...");
            return;
        }
        
        this.revive();
        this.mySquare.setPieceAboveMe(this);
    }
    
    public void setMovablePosHighlightValue(boolean value){
        for(Position pos : this.movablePositions){
            board.getSquare(pos).setHighlighted(value);
        }
    }
    
    private void revive() {
        this.killed = false;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public String getColorString() {
        return (this.color == Color.BLACK)? "BLACK": "WHITE";
    }
    
    public boolean isKilled(){
        return this.killed;
    }
    
    @Override
    public String toString() {
        return ("\n " + this.getPieceName() + " " + this.getColorString() + " || " + this.mySquare.getMyPosition());
    }
    
    public void pieceHasMoved(){}
        
    protected abstract void refreshMovablePositions(Board board);
    
    public abstract String getPieceName();
    
    public abstract void getImgPath();
}
