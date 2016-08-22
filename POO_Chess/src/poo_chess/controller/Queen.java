/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;

import java.util.ArrayList;
import java.util.List;
import poo_chess.Color;
import poo_chess.Position;
import poo_chess.controller.field.Board;
import poo_chess.controller.field.Square;

/**
 *
 * @author paulojeunon
 */
public class Queen extends Piece {
    
    public Queen(Color color, Square square, String direction, Board board) {
        super(color, square, direction, board);
    }
    
    @Override
    public String getPieceName(){
        return "QU";
    }
    
    @Override
    protected void refreshMovablePositions(Board board){
        
        // The movements of bishop and Rook combined
        
        // empty list
        List<Position> tmpList = new ArrayList<>();
        
        int myVerticalPos = this.mySquare.getMyPosition().getVerticalCoordinate();
        int myHorizontalPos = this.mySquare.getMyPosition().getHorizontalCoordinate();
        
        // This loop starts from the right side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myHorizontalPos+1; i < 8; i++){
            Position tmp = new Position(myVerticalPos, i);
            if(tmp.isValid() && board.getSquare(tmp).getPieceAbovaMe() == null){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        // This loop starts from the left side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myHorizontalPos-1; i > -1; i--){
            Position tmp = new Position(myVerticalPos, i);
            if(tmp.isValid() && board.getSquare(tmp).getPieceAbovaMe() == null){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        // This loop starts from the upper side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myVerticalPos-1; i > -1; i--){
            Position tmp = new Position(i, myHorizontalPos);
            if(tmp.isValid() && board.getSquare(tmp).getPieceAbovaMe() == null){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        // This loop starts from the bottom side of the piece and goes adding the squares util it hits a occupied square
        for(int i = myVerticalPos+1; i < 8; i++){
            Position tmp = new Position(i, myHorizontalPos);
            if(tmp.isValid() && board.getSquare(tmp).getPieceAbovaMe() == null){
                tmpList.add(tmp);
            } else {
                break;
            }
        }
        
        // General counter that orients the movement;
        int counter = 0;
        
        // Specific counters that orient the direction of the diagonal
        int counterUpDown = 0;
        int counterLeftRight = 0;
        
        // To switch between the diagonals that the bishop can move: (0: botton[+] right[+]) , 
        //                        (1: botton[+] left[-]) , (2: upper[-] right[+]) , (3: upper[-] left[-]); 
        int switcher = 0;
                
        while(true){
            counter++;
            counterUpDown = counterLeftRight = counter ;
            
            // Switch between the diagonals
            switch(switcher){
                case 1:{
                    counterLeftRight *= -1;
                    break;
                }
                
                case 2:{
                    counterUpDown *= -1;
                    break;
                }
                
                case 3:{
                    counterUpDown = counterLeftRight *= -1;
                    break;
                }
            }
            
            Position pos = new Position(myVerticalPos+counterUpDown, myHorizontalPos+counterLeftRight);
            if(pos.isValid() && this.board.getSquare(pos).getPieceAbovaMe() == null){
                tmpList.add(pos);
            } else { // Resets the counters 
                
                counter = 0;
                counterUpDown = 0;
                counterLeftRight = 0;
                
                switcher++;
                
                if(switcher > 3){
                    break;
                }
            }
        }
        
        this.movablePositions = tmpList;
    }
}
