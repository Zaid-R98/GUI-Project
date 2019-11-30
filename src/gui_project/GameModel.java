/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author azada
 */
public class GameModel {

    private static final int TOTAL_GRID_SIZE = 400;
    
    //Full game grid
    private Cell[][] cellGrid = new Cell[TOTAL_GRID_SIZE][TOTAL_GRID_SIZE];
    private ZoomLevel zoomLevel = ZoomLevel.MEDIUM;
    //Changes based on Scrolling
    private Point topLeftCellVisible = new Point(TOTAL_GRID_SIZE/3, TOTAL_GRID_SIZE/3);
    private int generation=0;
    private boolean isAutomaticMode = false;
    private GameSpeed gameSpeed = GameSpeed.NORMAL;

   
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
    
    void setAliveStatusAt(int x, int y, boolean aliveStatus) {
        cellGrid[x][y].setIsAlive(aliveStatus);
    }
    
    public Point getTopLeftCellVisible() {
        return topLeftCellVisible;
    }
    
    public ZoomLevel getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(ZoomLevel zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public GameSpeed getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(GameSpeed gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
    
    public int incrementGen()
    {  generation += 1;
        return generation;}
    
    public boolean getIsAutomaticMode() {
        return isAutomaticMode;
    }

    public void setIsAutomaticMode(boolean isAutomaticMode) {
        this.isAutomaticMode = isAutomaticMode;
    
    }
    
    public int sizeMod(int x) {
        if(x < 0)
            return TOTAL_GRID_SIZE - 1;
        return x % TOTAL_GRID_SIZE;
    }
    
    //Abdullah's code V3
    public void performCellLifeCalculation()
    {
        Cell nextVersionOfCellGrid[][] = new Cell[TOTAL_GRID_SIZE][TOTAL_GRID_SIZE];
        for(int i = 0; i < cellGrid.length; ++i){
            for(int j = 0; j < cellGrid[i].length; ++j)
            {
                int alivecells = 0;
                //counting if the cell and all other cells around it are alive
                for(int x = -1; x <= 1; ++x)
                    for(int y = -1; y <=1; ++y)
                        alivecells+=((cellGrid[sizeMod(i+x)][sizeMod(j+y)].getIsAlive())? 1:0);
                //we dont need to check if main cell is alive, so remove it from the alivecells
                alivecells-=((cellGrid[i][j].getIsAlive())? 1:0);
                
                if(cellGrid[i][j].getIsAlive() && (alivecells <= 1 || alivecells >= 4))
                    nextVersionOfCellGrid[i][j] = new Cell(false);
                else if(!cellGrid[i][j].getIsAlive() && alivecells == 3)
                    nextVersionOfCellGrid[i][j] = new Cell(true);
                else
                    nextVersionOfCellGrid[i][j] = new Cell(cellGrid[i][j].getIsAlive());
            }
        }
        cellGrid = nextVersionOfCellGrid;
    }
    
    //Abdullah's code V2
//    public void performCellLifeCalculation()
//    {
//        Cell nextVersionOfCellGrid[][] = new Cell[TOTAL_GRID_SIZE][TOTAL_GRID_SIZE];
//        for(int i = 0; i < cellGrid.length; ++i){
//            for(int j = 0; j < cellGrid[i].length; ++j)
//            {
//                int top, left, bottom, right, alivecells;
//                top = left = bottom = right = alivecells = 0;
//                if(i < cellGrid.length -1)
//                {
//                    if(j < cellGrid[i].length -1)
//                    {
//                        alivecells+=((cellGrid[i+1][j+1].getIsAlive())? 1:0);
//                        right = ((cellGrid[i][j+1].getIsAlive())? 1:0);
//                    }
//                    if(j > 0)
//                    {
//                        alivecells+=((cellGrid[i+1][j-1].getIsAlive())? 1:0);
//                        left = ((cellGrid[i][j-1].getIsAlive())? 1:0);
//                    }
//                    bottom = ((cellGrid[i+1][j].getIsAlive())? 1:0);
//                }
//                if(i > 0)
//                {
//                    if(j < cellGrid[i].length -1 )
//                    {
//                        alivecells+=((cellGrid[i-1][j+1].getIsAlive())? 1:0);
//                        right = ((cellGrid[i][j+1].getIsAlive())? 1:0);
//                    }
//                    if(j > 0)
//                    {
//                        alivecells+=((cellGrid[i-1][j-1].getIsAlive())? 1:0);
//                        left = ((cellGrid[i][j-1].getIsAlive())? 1:0);
//                    }
//                    top = ((cellGrid[i-1][j].getIsAlive())? 1:0);
//                }
//                alivecells+= top + left + bottom + right;
//                if(cellGrid[i][j].getIsAlive() && (alivecells <= 1 || alivecells >= 4))
//                {
//                    nextVersionOfCellGrid[i][j] = new Cell(false);
//                }
//                else if(!cellGrid[i][j].getIsAlive() && alivecells == 3)
//                {
//                    nextVersionOfCellGrid[i][j] = new Cell(true);
//                }
//                else
//                {
//                    nextVersionOfCellGrid[i][j] = new Cell(cellGrid[i][j].getIsAlive());
//                }
//            }
//        }
//        cellGrid = nextVersionOfCellGrid;
//    }
    
    public int getNumericDelayOfGameSpeed(){
        switch (gameSpeed) {
            case SLOW:
                return 1000;
            case FAST:
                return 50;
            default:
                //if NORMAL
                return 500;
        }
    }
    
    public int getCurrentCellWidthFromZoomLevel() {
        switch(zoomLevel){
            case SMALL:
                return 30;
            case MEDIUM:
                return 20;
            default:
                //if LARGE
                return 10;
        }
    }
}

enum GameSpeed{
    SLOW, NORMAL, FAST
}

enum ZoomLevel{
    SMALL, MEDIUM, BIG
}