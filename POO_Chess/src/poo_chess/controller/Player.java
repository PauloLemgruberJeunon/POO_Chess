/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import java.util.*;
import poo_chess.Color;
import poo_chess.controller.field.Board;
import poo_chess.Position;
import poo_chess.controller.field.Square;

/**
 *
 * @author paulojeunon
 */
public class Player {
    private final Color color;
    private final String side;
    private final Board board;
    private final String name;
    private Position myKingPos;
    private final List<Piece> myArmy;
    
    public Player(String name, String side, Color color, Board board){
        this.name = name;
        this.side = side;
        this.color = color;
        this.board = board;
        this.myKingPos = new Position((side.equals("up"))? 0 : 7, 3);
        this.myArmy = PlayerUtils.placeArmyOnBoard(side, color, board);
    }
    
    protected List<Piece> getMyArmy(){
        return this.myArmy;
    }
    
    protected Color getMyColor(){
        return color;
    }
        
    protected Piece selectPiece(){
        
        Piece pieceAbove;
        while(true){
            String message = "\n\n Enter the coordinates of the piece that you want to move";
            Position pos = PlayerUtils.enterCoordinates(message);
            pieceAbove = board.getSquare(pos).getPieceAbovaMe();
            if(pos.getIsValid() == false || pieceAbove == null || pieceAbove.getColor() != this.color){
                System.out.printf("\n\n Invalid pos selection");
            } else {
                break;
            }
        }
        return pieceAbove;
    }
    
    protected Position selectGoToPos(Piece piece){
        Position goToPos;
        while(true){
            String message = "\n\n Enter the wanted goTo position (verticalCoord, horizontalCoord):  ";
            goToPos = PlayerUtils.enterCoordinates(message);
            if((goToPos.getIsValid() && piece.getMovablePositions().contains(goToPos)) == false){
                System.out.printf("\n\n You enter an invalid position or a not movable position, try again...");
            } else {
                break;
            }
        }
        
        return goToPos;
    }
    
    protected void movePieceToPosition(Piece piece, Position pos){
        Square squareToMove = this.board.getSquare(pos);
        piece.movePiece(squareToMove);
    }
    
    protected boolean simulateMovement(Piece myPiece, Position goToPos, List<Piece> enemyArmy){
        Position originalPos = myPiece.mySquare.getMyPosition();
        Piece killedPiece = board.getSquare(goToPos).getPieceAbovaMe();
        
        this.movePieceToPosition(myPiece, goToPos);
        
        boolean isKingInCheck = PlayerUtils.isKingInCheck(this.myKingPos, enemyArmy);
        
        if(isKingInCheck){
            this.undoPlay(myPiece, killedPiece, originalPos);
        } else {
            if(originalPos.equals(this.myKingPos)){
                this.myKingPos = goToPos;
            }
        }
        
        return (isKingInCheck == false);
    }
    
    protected boolean isCheckMate(List<Piece> enemyArmy){
        for(Piece currPiece : this.myArmy){
            for(Position goToPos : currPiece.getMovablePositions()){
                if(this.simulateMovement(currPiece, goToPos, enemyArmy)){
                    return false;
                }
            }
        }
        return true;
    }
    
    private void undoPlay(Piece myPiece, Piece killedPiece, Position originalPos){
        myPiece.forceMovePiece(board.getSquare(originalPos), this);
        
        if(killedPiece != null){
            killedPiece.revivePiece(killedPiece, this);
        }
    }
    
}