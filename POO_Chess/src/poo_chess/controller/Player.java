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
    
    public String getName(){
        return this.name;
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
                pieceAbove.refreshMovablePositions(board);
                break;
            }
        }
        return pieceAbove;
    }
    
    protected Position selectGoToPos(Piece piece, List<Position> movablePos){
        Position goToPos;
        
        String message = "\n\n Enter the wanted goTo position (verticalCoord, horizontalCoord):  ";
        goToPos = PlayerUtils.enterCoordinates(message);
        if((goToPos.getIsValid() && movablePos.contains(goToPos)) == false){
            System.out.printf("\n\n You enter an invalid position or a not movable position, try again...");
            goToPos = null;
            piece.setMovablePosHighlightValue(false);
        }
        
        return goToPos;
    }
    
    protected void movePieceToPosition(Piece piece, Position pos, boolean simulatedMove){
        Square squareToMove = this.board.getSquare(pos);
        piece.movePiece(squareToMove, simulatedMove);
    }
    
    /**
     *
     * @param myPiece
     * @param enemyArmy
     * @param movablePos
     * @return 
     *  This function has the objective to make the movements and then calculate if this will let the king in a
     * check position
     **/    
    protected boolean simulateMovement(Piece myPiece, List<Position> movablePos, List<Piece> enemyArmy){
        
        boolean hasNonCheckMove = false;
        
        Position originalPos = myPiece.getSquare().getMyPosition();
        
        // See if the king is the selected piece (little "gambiarra")
        boolean kingSelected = originalPos.equals(this.myKingPos);
        
        for(Position goToPos : movablePos){
            
            // Stores the current state
            Piece killedPiece = board.getSquare(goToPos).getPieceAbovaMe();

            // make the simulated move to do the verification
            this.movePieceToPosition(myPiece, goToPos, true);

            // Check if the king is in check
            boolean isKingInCheck = PlayerUtils.isKingInCheck(kingSelected? goToPos : this.myKingPos, enemyArmy);

            this.undoPlay(myPiece, killedPiece, originalPos);
                                    
            if(isKingInCheck){
                movablePos.remove(goToPos);
            } else {
                hasNonCheckMove = true;
            }
        }
        
        return hasNonCheckMove;
    }
    
    protected boolean isCheckMate(Piece selectedPiece, List<Piece> enemyArmy){
        for(Piece currPiece : this.myArmy){
            if(selectedPiece != currPiece && 
                    this.simulateMovement(currPiece, currPiece.getMovablePositionsWithRefresh(), enemyArmy)){
                return false;
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
    
    protected Position getKingPos(){
        return this.myKingPos;
    }
    
    protected void setKingPos(Position pos){
        this.myKingPos = pos;
    }
    
}