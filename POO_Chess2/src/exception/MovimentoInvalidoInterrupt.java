/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author KOUAO JEAN VINCENT M
 */
public class MovimentoInvalidoInterrupt extends Exception{
    private String message ="Invalid Position";
    public MovimentoInvalidoInterrupt(){
        System.out.println("Invalid Position");
    }
    public String getMessage(){
        return message;
    } 
    
    
    
}
