/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;

//import Model.Chess;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observer;
import java.awt.Point;
//import pecas.Peca;
import poo_chess.View.DeskChessFrame;
import poo_chess.Position;

/**
 *
 * @author Kouao Jean Vincent M
 */

public class Controller implements MouseListener, MouseMotionListener, ActionListener{

    private DeskChessFrame view;
    private Point mouseCoord;
    private Chess chess;
    private String str , s;
    
    public Controller() {
        mouseCoord = new Point(0, 0);
    }
    
    public void addView(Observer view){
        this.view = (DeskChessFrame)view;
    }
    
    public void addLogicalChess(Chess chess) {
        this.chess = chess;
    }
    
    //Efeito mouse nas pecas
    public void drawMouseQuadrante(Graphics2D g) {
        
        int width = view.getBoardPanel().getWidth()/8;
        int height = view.getBoardPanel().getHeight()/8;
        
        int squareWidth = g.getClip().getBounds().width / 8;
        int squareHeight = g.getClip().getBounds().height / 8;
        
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(4));
        g.drawRect(this.mouseCoord.x/width * squareWidth, this.mouseCoord.y/height * squareHeight, squareWidth, squareHeight);
        g.setColor(Color.BLACK);
    }
    
    public void startGame(){
    //PRECISA PROGRAMAR ISSO
    }
   
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {        
        int width = view.getBoardPanel().getWidth()/8;
        int height = view.getBoardPanel().getHeight()/8;
                
        Position clickPos = new Position(e.getY()/height, e.getX()/width);
        
        if(this.chess.isPieceSelected()) {
            this.chess.selectGoToPosAndMove(clickPos);
        } else {
            this.chess.selectPiece(clickPos);
        }
        s = this.chess.toLog();
        view.logArea(s);
        view.repaint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseCoord.setLocation(e.getX(), e.getY());
        view.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.sair();
    }
    
    
    public void startMainWindow(){
        view.setVisible(true);
    }
}
