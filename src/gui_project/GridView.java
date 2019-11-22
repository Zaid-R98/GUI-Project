/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 *
 * @author azada
 */
public class GridView extends JComponent{

    
    private Cell[][] portionOfCellsVisible;
    private Rectangle2D.Double[][] portionOfCellsVisibleAsRects;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < portionOfCellsVisibleAsRects.length; ++i) {
            for (int j = 0; j < portionOfCellsVisibleAsRects[i].length; ++j) { 
                //Find and set color of cell
                if (portionOfCellsVisible[i][j].getIsAlive()){
                    g2d.setColor(Color.YELLOW);
                }
                else{
                    g2d.setColor(Color.DARK_GRAY);
                }
                //Draw Cell
                g2d.fill(portionOfCellsVisibleAsRects[i][j]);
                
                //Draw border
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.draw(portionOfCellsVisibleAsRects[i][j]);              
            }
        }     
    }
    
    
    public void setPortionOfCellsVisible(Cell[][] portionOfCellsVisible) {
        this.portionOfCellsVisible = portionOfCellsVisible;
    }

    public void setPortionOfCellsVisibleAsRects(Rectangle2D.Double[][] portionOfCellsVisibleAsRects) {
        this.portionOfCellsVisibleAsRects = portionOfCellsVisibleAsRects;
    }
    
}
