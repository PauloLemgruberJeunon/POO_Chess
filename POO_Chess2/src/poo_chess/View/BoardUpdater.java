package poo_chess.View;

import poo_chess.controller.field.Board;
import poo_chess.controller.*;
import poo_chess.controller.field.Square;
        
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Kouao Jean Vincent M
 */

public class BoardUpdater extends JPanel {
    
    private final ArrayList<Observer> observers;
    private Board chessBoard;
    private static String currImgPath;

    public BoardUpdater(Board board) {
        //super();
        observers = new ArrayList<>();
        this.chessBoard = board;
    }
    
    public void registerObserver(Observer ob){
        observers.add(ob);
    }
    
    public void drawBoard(Graphics2D g){
        Color amarelo = new Color(255,200,100);
        Color vermelho = new Color(150,50,30);
        g.setBackground(amarelo);
        g.setColor(vermelho);
                 
        float maxWidth=this.getWidth();
        float maxHeight=this.getHeight();
        float boardSize = (maxWidth < maxHeight) ? maxWidth : maxHeight;
        int spotSize = Math.round(boardSize/8.0f);
                  
        for(int i = 0; i<8; ++i){
            for(int j = 0; j<8; ++j){
                //varia a cor do quadrante
                if(g.getColor() == amarelo) g.setColor(vermelho);
                else g.setColor(amarelo);

                //Desenha o tabuleiro
                g.fillRect(i*spotSize,j*spotSize,(i*spotSize)+spotSize, (j*spotSize)+spotSize);
            }

            if(g.getColor() == amarelo) g.setColor(vermelho);
            else g.setColor(amarelo);
        }
    }
    
    public void printPiece(Graphics2D g) {
        
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Square square = this.chessBoard.getSquare(i, j);
                
                int squareWidth = g.getClip().getBounds().width / 8;
                int squareHeight = g.getClip().getBounds().height / 8;

                int x0 = square.getMyPosition().getHorizontalCoordinate() * squareWidth;
                int y0 = square.getMyPosition().getVerticalCoordinate() * squareHeight;
                int x1 = x0 + squareWidth;
                int y1 = y0 + squareHeight;
                int squareSize = 50;
                Piece piece = square.getPieceAbovaMe();
                if(piece != null) {
                    piece.getImgPath();
                    Image P = new ImageIcon(currImgPath).getImage();
                    ImageObserver observer = null;
                    g.drawImage(P, x0, y0,squareWidth, squareHeight, observer);
                }                
            }
        }        
    }

    @Override //sobrescrita do metodo paintComponent da classe JPanel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
               
        Graphics2D g2 = (Graphics2D)g;
        drawBoard(g2);
        for(Observer ob : observers){
            ob.update(null, g);
        }
    }    
    
    public static void setCurrImgPath(Bishop b) {
        currImgPath = "img/" + b.getColorString() + "/" + b.getPieceName() + ".png";
    }
    
    public static void setCurrImgPath(King k) {
        currImgPath = "img/" + k.getColorString() + "/" + k.getPieceName() + ".png";
    }
    
    public static void setCurrImgPath(Queen q) {
        currImgPath = "img/" + q.getColorString() + "/" + q.getPieceName() + ".png";
    }
    
    public static void setCurrImgPath(Rook r) {
        currImgPath = "img/" + r.getColorString() + "/" + r.getPieceName() + ".png";
    }
    
    public static void setCurrImgPath(Pawn p) {
        currImgPath = "img/" + p.getColorString() + "/" + p.getPieceName() + ".png";
    }
    
    public static void setCurrImgPath(Knight kn) {
        currImgPath = "img/" + kn.getColorString() + "/" + kn.getPieceName() + ".png";
    }
    
    public void updateBoard(Board board) {
        this.chessBoard = board;
    }

}
