/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess;

/**
 *
 * @author paulojeunon
 * This class stores the coordinates of a chess board
 */
public class Position {
    private final int verticalPos;
    private final int horizontalPos;
    private final boolean isValid;
    
    public Position(int verticalPos, int horizontalPos){
        this.verticalPos = verticalPos;
        this.horizontalPos = horizontalPos;
        this.isValid = this.thisPositionExists();
    }

    public int getVerticalCoordinate(){
        return verticalPos;
    }
    
    public int getHorizontalCoordinate(){
        return horizontalPos;
    }
    
    public boolean isValid(){
        return this.isValid;
    }
    
    // Overrides the equals method to check if the content of the positions are equals
    @Override
    public boolean equals(Object position){
        if((position instanceof Position) == false) {
            return false;
        }
        
        boolean isEqual;
        Position pos = (Position) position;
        
        isEqual = ((pos.horizontalPos == this.horizontalPos) && (pos.verticalPos == this.verticalPos));
        
        return isEqual;
    }
    
    // Checks if the position exists
    private boolean thisPositionExists(){
        boolean verticalCoordinate;
        boolean horizontalCoordinate;
        
        verticalCoordinate = ((this.verticalPos >= 0) &&
                (this.verticalPos <= 7));
        
        horizontalCoordinate = ((this.horizontalPos >= 0) &&
                (this.horizontalPos <= 7));
        
        return (verticalCoordinate && horizontalCoordinate);
    }
    
    // Prints the position
    public void printPos(){
        System.out.printf("(" + this.horizontalPos + ", " + this.verticalPos + ")");
    }
}
