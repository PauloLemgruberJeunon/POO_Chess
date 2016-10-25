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
public class Bishop extends Piece implements java.io.Serializable{
    
    public Bishop(Color color, Square square, String direction, Board board) {
        super(color, square, direction, board);
    }
    
    @Override
    public String getPieceName(){
        return "bishop";
    }
    
    @Override
    protected void refreshMovablePositions(Board board){
        
        // Stores the current Pos of the Knight 
        int myVerticalPos = this.getSquare().getMyPosition().getVerticalCoordinate();
        int myHorizontalPos = this.getSquare().getMyPosition().getHorizontalCoordinate();
        
        // General counter that orients the movement;
        int counter = 0;
        
        // Specific counters that orient the direction of the diagonal
        int counterUpDown = 0;
        int counterLeftRight = 0;
        
        // To switch between the diagonals that the bishop can move: (0: botton[+] right[+]) , 
        //                        (1: botton[+] left[-]) , (2: upper[-] right[+]) , (3: upper[-] left[-]); 
        int switcher = 0;
        
        List<Position> tmpList = new ArrayList<>();
        
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
            if(pos.getIsValid() && this.board.getSquare(pos).getPieceAbovaMe() == null){
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
    
    @Override
    public void getImgPath() {
        BoardUpdater.setCurrImgPath(this);
    }
    
}
