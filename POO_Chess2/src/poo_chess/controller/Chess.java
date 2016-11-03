/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;
import java.sql.Time;
import java.util.List;
import poo_chess.Color;
import java.util.Scanner;
import javax.swing.JOptionPane;
import poo_chess.ChessSing;
import poo_chess.controller.field.Board;
import poo_chess.Position;
//import poo_chess.View.*;

/**
 *
 * @author paulojeunon
 */
public class Chess implements java.io.Serializable {
    
    private final Player player1;
    private final Player player2;
    private final Board board;
//    private DeskChessFrame view;
    private String str1, str="", stri , error = "\n Invalid position selected", Checkmate, resposta1, resposta2;
    private int counter, logState; 
    private Piece selectedPiece;
    private Player currPlayer;
    private Player advPlayer;
    private Position goToPos;
    private List<Position> movablePos;
    private boolean isPieceSelected;
    
    public Chess(Board board){
        this.board = board;
        //this.player1 = new Player(this.setPlayerName(1), "up", Color.BLACK, board);
        resposta1 = JOptionPane.showInputDialog(null,"Informe o nome do Jogador 1","Xadrez",JOptionPane.QUESTION_MESSAGE);
        if(resposta1 == null || resposta1 == "")
            resposta1 = "Jogador 1";
        this.player1 = new Player(resposta1,"up",Color.BLACK, board);
        //this.player2 = new Player(this.setPlayerName(2), "down", Color.WHITE, board); 
        resposta2 = JOptionPane.showInputDialog(null,"Informe o nome do Jogador 2","Xadrez",JOptionPane.QUESTION_MESSAGE);
        if (resposta2 == null || resposta2 == ""){ 
            resposta2 = "Jogador 2";
            System.out.println("resposta = " + resposta2);
        }
        
        this.player2 = new Player(resposta2,"down",Color.WHITE, board);
        this.counter = 0;
        this.isPieceSelected = false;
        this.currPlayer = this.player1;
        this.advPlayer = this.player2;
    }
    
    public void selectPiece(Position clickPos) {
        logState = 1;
        selectedPiece = currPlayer.selectPiece(clickPos);
        
        if(selectedPiece == null) {
            this.isPieceSelected = false;
            return;
        } 
        System.out.printf(selectedPiece.toString());
        
        str1 = (selectedPiece.getPieceName() + " " + selectedPiece.getColorString() + " || " + selectedPiece.getSquare().getMyPosition());
        movablePos = selectedPiece.getMovablePositionsWithRefresh();
        
        str = "";
        for(Position i : movablePos) {            
            System.out.printf("\n" + i.toString());
            str = (str + "\n" + i.toString());
        }
        
        //str = str1 + "\n" +str;
        currPlayer.simulateMovement(selectedPiece, movablePos, advPlayer.getMyArmy());
                
        this.isPieceSelected = true;
                
        selectedPiece.setMovablePosHighlightValue(true);
    }
//LUGAR PARA INSERIR INTERUPCAO
    public void selectGoToPosAndMove(Position clickGoToPos) {
        goToPos = currPlayer.selectGoToPos(selectedPiece, movablePos, clickGoToPos);
        
        if(goToPos != null) {
            counter++;
            
            if(selectedPiece.getSquare().getMyPosition().equals(currPlayer.getKingPos())){
                currPlayer.setKingPos(goToPos);
            }
            
            selectedPiece.setMovablePosHighlightValue(false);
            currPlayer.movePieceToPosition(selectedPiece, goToPos, false);
            
            boolean isCheckMate = advPlayer.isCheckMate(selectedPiece, currPlayer.getMyArmy());
            
            Player changed = currPlayer;
            currPlayer = advPlayer;
            advPlayer = changed;
                        
            if(isCheckMate) {
                this.Checkmate = ("\n\n Checkmate: " + currPlayer.getName() + " has won the game!!!");
                System.out.printf(stri);
                ChessSing.isCheck(false, true);
                System.exit(0);
                logState = 3;
                
            }
        } else {
            System.out.printf(error);
            //view.logArea(error);
            logState = 2;
        }
        
        this.isPieceSelected = false;        
    }

    public String toLog(){
        switch (logState) {
            case 1:
                stri = str1 + str;
                break;
            case 2:
                stri = error;
                break;
            case 3:
                stri = Checkmate;
                break;
            default:
                break;
        }
        return stri;
    }    
    //Inserir nome
   /*
    private String setPlayerName(int i){
        
        Scanner reader = new Scanner(System.in);
        
        System.out.println("\n\n Type the name of player " + i + ": ");
        return reader.nextLine();
    }*/
    
    public boolean isPieceSelected() {
        return this.isPieceSelected;
    }
    
    public Board board() {
        return this.board;
    }
    
    public Player getCurrPlayer() {
        return currPlayer;
    }
    
}
