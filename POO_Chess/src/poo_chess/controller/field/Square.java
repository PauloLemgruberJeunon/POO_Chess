/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller.field;
import poo_chess.Color;
import poo_chess.controller.Piece;
import poo_chess.Position;

/**
 *
 * @author paulojeunon
 */
public class Square { // TODO: Implementar equals...
    
    private final Color color;
    private final Position position;
    private boolean highlighted;
    private Piece pieceAboveMe;
    
    protected Square(Color color, Position position){
        this.color = color;
        this.position = position;
        this.highlighted = false;
    }
    
    public void setHighlighted(boolean highlight){
        this.highlighted = highlight;
    }
    
    public boolean getHighlighted(){
        return this.highlighted;
    }
    
    public Piece getPieceAbovaMe(){
        return this.pieceAboveMe;
    }
    
    public void clearPieceAboveMe(){
        this.pieceAboveMe = null;
    }
    
    public Position getMyPosition(){
        return this.position;
    }
    
        public void setPieceAboveMe(Piece piece){
        this.pieceAboveMe = piece;
    }
        
    // Overrides the method equals to compare the objects by their contents 
    @Override
    public boolean equals(Object square){
        
        if((square instanceof Square) == false) {
            return false;
        }
        
        Square sqr = (Square) square;
        
        boolean isEqual;
        isEqual = ((sqr.highlighted == this.highlighted) && 
                (sqr.color == this.color) && 
                (sqr.pieceAboveMe == this.pieceAboveMe) && 
                (sqr.position == this.position));

        return isEqual;
    }
}
