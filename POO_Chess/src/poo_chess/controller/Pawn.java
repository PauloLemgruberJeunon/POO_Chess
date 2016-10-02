/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import poo_chess.Color;
import poo_chess.controller.field.Square;
import java.util.*;
import poo_chess.Position;
import poo_chess.controller.field.Board;
import poo_chess.View.BoardUpdater;


/**
 *
 * @author paulojeunon
 */
public class Pawn extends Piece {
    private boolean firstMove;
    
    public Pawn(Color color, Square square, String direction, Board board) {
        super(color, square, direction, board);
        this.firstMove = true;
    }
    
    @Override
    public void pieceHasMoved(){
        this.firstMove = false;
    }
    
    @Override
    public String getPieceName(){
        return "pawn";
    }
    
    @Override
    protected void refreshMovablePositions(Board board){
                
        // empty list
        List<Position> tmpList = new ArrayList<>();
        
        // int that will save the position that the VerticalCoordinate of the piece inside the vertex verticalVec
        int myVerticalPos = this.getSquare().getMyPosition().getVerticalCoordinate();
        int myHorizontalPos = this.getSquare().getMyPosition().getHorizontalCoordinate();
        
        // this variable saves the orientation that the piece will move, if its up you can assume that the piece will start at the botton
        boolean isUp = this.direction.equals("up");
        
        int verticalMoves;
        // stores the number of foward moves that the piece can make
        if(this.firstMove){
            verticalMoves = 2;
        } else {
            verticalMoves = 1;
        }
        
        
         // This loop adds the possible foward positions for the Pawn to go
        for(int i = 1; i < verticalMoves+1; i++){
            Position tmp = new Position((isUp? myVerticalPos - i : myVerticalPos + i), myHorizontalPos);
            if(tmp.getIsValid() && board.getSquare(tmp).getPieceAbovaMe() == null){
                tmpList.add(tmp);
            }
        }
        
        // This variable stores the dislocated vertical position and adapts for the pawns going up and the others going down
        int newVerticalPos = isUp? myVerticalPos-1 : myVerticalPos+1;
        
        Position tmpTopRight = new Position(newVerticalPos, myHorizontalPos+1);
        if(tmpTopRight.getIsValid()){
            Piece topRightPiece = board.getSquare(tmpTopRight).getPieceAbovaMe();
            
            // The logic is, if this position is valid, if it does not contain a piece above it and
            //  if this piece is an enemy, this position is valid to move
            if(topRightPiece != null && topRightPiece.color != this.color){
                tmpList.add(tmpTopRight);
            }
        }
        
        Position tmpTopLeft = new Position(newVerticalPos, myHorizontalPos-1);
        if(tmpTopLeft.getIsValid()){
            Piece topLeftPiece = board.getSquare(tmpTopLeft).getPieceAbovaMe();
            
            // The same thing as above, but for the left side in this case
            if(topLeftPiece != null && topLeftPiece.color != this.color){
                tmpList.add(tmpTopLeft);
            }
        }

        // updates the movable Positions of the Pawn
        this.movablePositions = tmpList;
    }    
    
    @Override
    public void getImgPath() {
        BoardUpdater.setCurrImgPath(this);
    } 
    
}
            
