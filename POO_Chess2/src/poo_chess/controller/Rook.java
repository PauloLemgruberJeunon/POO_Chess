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
public class Rook extends Piece{
    
    public Rook(Color color, Square square, String direction, Board board) {
        super(color, square, direction, board);
    }
    
    @Override
    public String getPieceName(){
        return "rook";
    }
    
    @Override
    protected void refreshMovablePositions(Board board){
        // empty list
        List<Position> tmpList = new ArrayList<>();
        
        int myVerticalPos = this.getSquare().getMyPosition().getVerticalCoordinate();
        int myHorizontalPos = this.getSquare().getMyPosition().getHorizontalCoordinate();
        
        // This loop starts from the right side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myHorizontalPos+1; i < 8; i++){
            Position tmp = new Position(myVerticalPos, i);
            if(tmp.getIsValid() && (board.getSquare(tmp).getPieceAbovaMe() == null || 
                    board.getSquare(tmp).getPieceAbovaMe().getColor() != this.color)){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        // This loop starts from the left side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myHorizontalPos-1; i > -1; i--){
            Position tmp = new Position(myVerticalPos, i);
            if(tmp.getIsValid() && (board.getSquare(tmp).getPieceAbovaMe() == null || 
                    board.getSquare(tmp).getPieceAbovaMe().getColor() != this.color)){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        // This loop starts from the upper side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myVerticalPos-1; i > -1; i--){
            Position tmp = new Position(i, myHorizontalPos);
            if(tmp.getIsValid() && (board.getSquare(tmp).getPieceAbovaMe() == null || 
                    board.getSquare(tmp).getPieceAbovaMe().getColor() != this.color)){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        // This loop starts from the bottom side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myVerticalPos+1; i < 8; i++){
            Position tmp = new Position(i, myHorizontalPos);
            if(tmp.getIsValid() && (board.getSquare(tmp).getPieceAbovaMe() == null || 
                    board.getSquare(tmp).getPieceAbovaMe().getColor() != this.color)){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        this.movablePositions = tmpList;
    }
    
    @Override
    public void getImgPath() {
        BoardUpdater.setCurrImgPath(this);
    }
    
}
