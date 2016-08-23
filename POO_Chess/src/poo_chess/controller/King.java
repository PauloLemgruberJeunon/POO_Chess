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
public class King extends Piece{
    
    private final List<Piece> advPieces;
    
    public King(Color color, Square square, String direction, Board board) {
        super(color, square, direction, board);
        
        this.advPieces = this.getAdvPieces();
    }
    
    private List<Piece> getAdvPieces(){
        List<Piece> tmpList = new ArrayList<>();
        
        for(Square sqr : this.board.getSquareList()){
            Piece piece = sqr.getPieceAbovaMe();
            if(piece != null && piece.getColor() != this.color){
                tmpList.add(piece);
            }
        }
        
        return tmpList;
    }
    
    @Override
    public String getPieceName(){
        return "KI";
    }
    
    @Override
    protected void refreshMovablePositions(Board board){
        
        // Stores the current Pos of the Knight 
        int myVerticalPos = this.mySquare.getMyPosition().getVerticalCoordinate();
        int myHorizontalPos = this.mySquare.getMyPosition().getHorizontalCoordinate();
        
        // Stores the possible positions that the knight can go
        List<Position> tmpList = new ArrayList<>();
        
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(i == 0 && j == 0){
                } else {
                    Position pos = new Position(myVerticalPos+i, myHorizontalPos+j);
                    if(pos.getIsValid() && board.getSquare(pos).getPieceAbovaMe() == null){
                        tmpList.add(pos);
                    }
                }
            }
        }

        this.movablePositions = this.KingMovementChecker(this.advPieces, tmpList);
    }
    
    private List<Position> KingMovementChecker(List<Piece> advPieces, List<Position> kingMovablePosList){
        
        for(Piece piece : advPieces){
            for(Position pos : piece.getMovablePositions()){
                if(kingMovablePosList.contains(pos)){
                    kingMovablePosList.remove(pos);
                }
            }
        }
        
        return kingMovablePosList;
    }
}
