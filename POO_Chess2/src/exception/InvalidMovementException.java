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
public class InvalidMovementException extends Exception {
    
    private final String message = "Invalid Position";
    
    public InvalidMovementException() {
        System.out.println("Invalid Position");
    }
    
    @Override
    public String getMessage() {
        return message;
    } 
    
}
