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
public final class Position {
    private final int verticalPos;
    private final int horizontalPos;
    private final float hashKey;
    private final boolean isValid;
    
    public Position(int verticalPos, int horizontalPos){
        this.verticalPos = verticalPos;
        this.horizontalPos = horizontalPos;
        this.hashKey = this.getPosKey();
        this.isValid = this.thisPositionExists();
    }
    
    @Override
    public String toString() {
        return ("x = " + horizontalPos + " y = " + verticalPos);
    }
    
    public Position(Position pos){
        this.verticalPos = pos.getVerticalCoordinate();
        this.horizontalPos = pos.getHorizontalCoordinate();
        this.isValid = pos.getIsValid();
        this.hashKey = pos.hashKey;
    }
    
    public float getKey(){
        return this.hashKey;
    }
    
    public static float getKeyByCoords(float vertical, float horizontal){
        return ((vertical*1.7f)+(horizontal*2.3f));
    }

    public int getVerticalCoordinate(){
        return verticalPos;
    }
    
    private float getPosKey(){
        return (((float)this.verticalPos*1.7f)+((float)this.horizontalPos*2.3f));
    }
    
    public int getHorizontalCoordinate(){
        return horizontalPos;
    }
    
    public boolean getIsValid(){
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
        System.out.printf("\n\n (" + this.verticalPos + ", " + this.horizontalPos + ")");
    }
}
