/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.Point;

/**
 *
 * @author azada
 */
public class GameModel {

    private static final int TOTAL_GRID_SIZE = 400;
    
    //Full game grid
    private Cell[][] cellGrid = new Cell[TOTAL_GRID_SIZE][TOTAL_GRID_SIZE];
    //Changes based on Zoom level
    private int currentCellWidth = 20;
    //Changes based on Scrolling
    private Point topLeftCellVisible = new Point(TOTAL_GRID_SIZE/3, TOTAL_GRID_SIZE/3);
    
    private int generation=0; 
    
    public GameModel(){
        //Initialize grid to dead cells
        for (int i = 0; i < cellGrid.length; ++i){
            for (int j = 0; j < cellGrid[i].length; ++j){
                cellGrid[i][j] = new Cell();
            }
        }
    }
    
    boolean getAliveStatusAt(int x, int y){
        return cellGrid[x][y].getIsAlive();
    }
    
    
    public Point getTopLeftCellVisible() {
        return topLeftCellVisible;
    }

    
    public int getCurrentCellWidth() {
        return currentCellWidth;
    }
    
    public int incrementGen()
    {  generation += 1;
        return generation;}
}
