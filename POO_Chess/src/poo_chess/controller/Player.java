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
import poo_chess.controller.ChessUtils;

/**
 *
 * @author paulojeunon
 */
public class Player {
    private final Color color;
    private final String side;
    private final Board board;
    private final String name;
    private final List<Piece> myArmy;
    
    public Player(String name, String side, Color color, Board board){
        this.name = name;
        this.side = side;
        this.color = color;
        this.board = board;
        this.myArmy = PlayerUtils.placeMyArmyOnBoard(side, color, board);
    }
    
    protected void myTurn(){
        
        
        
        System.out.println("\n\n Turn of player: " + this.name);
        
        while(true){
            
            String message = "\n\n Enter the vertical coordinate and after the horizontal coordinate of the piece \n     "
                    + "that you want to move: ";
            Position piecePos = PlayerUtils.enterCoordinates(message);
            
            message = "\n\n Enter the vertical coordinate and after the horizontal coordinate of the position \n     "
                    + "that you want to move to: ";
            Position posToMove = PlayerUtils.enterCoordinates(message);
            
            if(piecePos.isValid() == false && posToMove.isValid() == false){
                System.out.println("\n\n Invalid coordinates, both coordinates can go from 0 up to 7... try again... ");
            } else {
                Piece piece = board.getSquare(piecePos).getPieceAbovaMe();
                if(piece == null || piece.getColor() != this.color){
                    System.out.println("[WARNING] you have not selected a piece");
                } else {
                    if(piece.getMovablePositions().contains(posToMove)){
                        this.movePieceToPosition(piece, posToMove);
                        break;
                    } else {
                        System.out.println("\n\n [WARNING] the position is not available to move to that position");
                    }
                }
            }
        }
    }
    
    protected void movePieceToPosition(Piece piece, Position pos){
        Square squareToMove = this.board.getSquare(pos);
        piece.movePiece(squareToMove);
    }
    
}
