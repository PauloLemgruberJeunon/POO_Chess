/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poo_chess.controller;

/**
 *
 * @author KOUAO JEAN VINCENT M
 */
public class MovimentoInvalidoInterrupt extends Exception{
    private Piece peca;
    
    MovimentoInvalidoInterrupt(Piece peca){
        this.peca = peca;
    }
    
    Piece getPeca(){
        return this.peca;
    }
    
    
}
