/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;

import java.util.ArrayList;
import java.util.List;
import poo_chess.Color;
import poo_chess.controller.field.Board;
import poo_chess.controller.field.Square;
import poo_chess.Position;
import poo_chess.View.BoardUpdater;

/**
 *
 * @author paulojeunon
 */
public class Knight extends Piece {
    
    public Knight(Color color, Square square, String direction, Board board) {
        super(color, square, direction, board);
    }
    
    @Override
    public String getPieceName(){
        return "knight";
    }
    
    @Override 
    protected void refreshMovablePositions(Board board){
        
        // Stores the current Pos of the Knight 
        int myVerticalPos = this.getSquare().getMyPosition().getVerticalCoordinate();
        int myHorizontalPos = this.getSquare().getMyPosition().getHorizontalCoordinate();
        
        // Stores the possible positions that the knight can go
        Position [] posVec = new Position[8];
        List<Position> tmpList = new ArrayList<>();
        
        // Top right
        posVec[0] = new Position(myVerticalPos+2, myHorizontalPos+1);
        
        // Top left
        posVec[1] = new Position(myVerticalPos+2, myHorizontalPos-1);
        
        // botton right
        posVec[2] = new Position(myVerticalPos-2, myHorizontalPos+1);
        
        // botton left
        posVec[3] = new Position(myVerticalPos-2, myHorizontalPos-1);
        
        // Left top
        posVec[4] = new Position(myVerticalPos+1, myHorizontalPos-2);
        
        // Left botton
        posVec[5] = new Position(myVerticalPos-1, myHorizontalPos-2);
        
        // Right top
        posVec[6] = new Position(myVerticalPos+1, myHorizontalPos+2);
        
        // Right botton
        posVec[7] = new Position(myVerticalPos-1, myHorizontalPos+2);
        
        // goes throught the possible positions vectors adding them to the tmpList that stores the valids posistions that this knight can go
        for(int i = 0; i < 8; i++){
            if(posVec[i].getIsValid()){
                tmpList.add(posVec[i]);
            }
        }
        
        this.movablePositions = tmpList;
    }
    
    @Override
    public void getImgPath() {
        BoardUpdater.setCurrImgPath(this);
    }
    
}
