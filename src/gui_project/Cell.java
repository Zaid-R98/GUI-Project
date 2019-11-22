/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author azada
 */
public class Cell {
    private boolean isAlive = false;
    private Rectangle2D.Double rect = new Rectangle2D.Double();
    
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
    
    public void setRect(double x, double y, double w, double h){
        rect.setRect(x, y, w, h);
    }
    
    //For drawing purposes
    public Rectangle2D getRect(){
        return rect;
    }
    
    //added this comment to check for connectivity- zaid
    
}
