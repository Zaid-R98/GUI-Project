/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 *
 * @author azada
 */
public class GridView extends JComponent{

    private Cell[][] portionOfCellsVisible;

    public GridView() {
       addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectCellByPoint(e.getPoint());
                repaint();
            }         
        });
    
  }
    
    private void selectCellByPoint(Point point) {
         for (int i = 0; i < portionOfCellsVisible.length; ++i) {
            for (int j = 0; j < portionOfCellsVisible[i].length; ++j) { 
                //Find and set color of cell
                if (portionOfCellsVisible[i][j].getRect().contains(point)){
                    if (portionOfCellsVisible[i][j].getIsAlive()) {
                         portionOfCellsVisible[i][j].setIsAlive(false);
                    }
                    else{ portionOfCellsVisible[i][j].setIsAlive(true);}
                    repaint();
                    return;
                }
                
                
                
            }
    }}


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < portionOfCellsVisible.length; ++i) {
            for (int j = 0; j < portionOfCellsVisible[i].length; ++j) { 
                //Find and set color of cell
                if (portionOfCellsVisible[i][j].getIsAlive()){
                    g2d.setColor(Color.YELLOW);
                }
                else{
                    g2d.setColor(Color.DARK_GRAY);
                }
                //Draw Cell
                g2d.fill(portionOfCellsVisible[i][j].getRect());
                
                //Draw border
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.draw(portionOfCellsVisible[i][j].getRect());              
            }
        }     
    }
    
      
    
    public void setPortionOfCellsVisible(Cell[][] portionOfCellsVisible) {
        this.portionOfCellsVisible = portionOfCellsVisible;
    }

    public Cell[][] getPortionOfCellsVisible() {
        return portionOfCellsVisible;
    }
    
 
    
}
