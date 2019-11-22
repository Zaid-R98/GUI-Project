/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

/**
 *
 * @author azada
 */
public class Cell {
    private boolean isAlive = false;
    
    public Cell(){ }
    
    public Cell(boolean isAlive){
        this.isAlive = isAlive;
    }
    
    public void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    
    public boolean getIsAlive() {
        return isAlive;
    }
    
}
