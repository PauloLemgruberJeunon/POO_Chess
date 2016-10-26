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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import poo_chess.ChessSing;
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
    private boolean firstMoveDone = false;
    
    public Controller() {
        mouseCoord = new Point(0, 0);
    }
    
    public void addView(Observer view){
        this.view = (DeskChessFrame)view;
    }
    
    public void addLogicalChess(Chess chess) {
        this.chess = chess;                               
    }
    
    public void updateLogicalChess(Chess chess) {
        this.chess = chess;
    }
    
    public Chess chess() {
        return chess;
    }
    
    public void save() {
        Path path = Paths.get("save.ser");
        if(Files.exists(path) == false) {
            File file = new File("save.ser");
        }
                
        try{
            FileOutputStream fout = new FileOutputStream("save.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this.chess);
            oos.close();
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void load() {
        try {
            FileInputStream fileIn = new FileInputStream("save.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.chess = (Chess) in.readObject();
            in.close();
            fileIn.close();
         }catch(IOException i) {
            i.printStackTrace();
         }catch(ClassNotFoundException c) {
            c.printStackTrace();
        }
        
        ChessSing.updateChess(chess);
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
        
        if(this.firstMoveDone == false) {
                ChessSing.startAutoSave();
                this.firstMoveDone = true;
        }
        
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
    
    public Player currPlayer() {
        return chess.getCurrPlayer();
    }
    
    public void startMainWindow(){
        view.setVisible(true);
    }
}
