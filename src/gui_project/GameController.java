/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author azada
 */
public class GameController {

    private GameModel gameModel;
    private GridView gridView;
    private GamePanels panel; 
    
    public GameController(GameModel gameModel, GridView gridView, GamePanels panel) {
        this.gameModel = gameModel;
        this.gridView = gridView;
        this.panel = panel;
        updateGridViewDisplay();
        panel.addNextButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CellLife();
            }
        });
    }
    
    private void updateGridViewDisplay(){
        int gridViewWidth = gridView.getWidth();
        int gridViewHeight = gridView.getHeight();

        int currentCellWidth = gameModel.getCurrentCellWidth();
        
        int numOfCellsRows = gridViewWidth /currentCellWidth;
        int numOfCellsCols = gridViewHeight / currentCellWidth;
        
        Cell[][] portionOfCellsVisible = new Cell[numOfCellsCols][numOfCellsRows];
        
        //Initialize visible portion
        for (int i = 0; i < portionOfCellsVisible.length; ++i) {
            int currentYPos = i * currentCellWidth;
            for (int j = 0; j < portionOfCellsVisible[i].length; ++j) {
                int currentXPos = j * currentCellWidth;
                
                //Get cell status by top-left corner,  offset by current position
                boolean currentAliveStatus
                        = gameModel.getAliveStatusAt(
                                i + gameModel.getTopLeftCellVisible().x,
                                j + gameModel.getTopLeftCellVisible().y);
                
                //Create a rectangle with correct xpos, ypos
                portionOfCellsVisible[i][j] = new Cell(currentAliveStatus);
                    
                //Set Rect information
                portionOfCellsVisible[i][j].setRect(
                        currentXPos, 
                        currentYPos, 
                        currentCellWidth,
                        currentCellWidth);
            }
        }
        
        //Update Grid View information
        gridView.setPortionOfCellsVisible(portionOfCellsVisible);
        //Request Grid View to refresh UI
        gridView.repaint();
        
    }
    
    //this is the most basic algorithm, please dont kill me xD
    private void CellLife()
    {
        Cell next[][] = new Cell[gridView.getPortionOfCellsVisible().length][gridView.getPortionOfCellsVisible()[0].length];
        for(int i = 0; i < gridView.getPortionOfCellsVisible().length; ++i)
            for(int j = 0; j < gridView.getPortionOfCellsVisible()[i].length; ++j)
            {
                int ctr = 0; //to count alive cells
                if(i == 0 && j == 0)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0);
                }
                else if(i == 0 && j == gridView.getPortionOfCellsVisible()[i].length - 1)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0);
                }
                else if(i == gridView.getPortionOfCellsVisible().length - 1 && j == 0)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0);
                }
                else if(i == gridView.getPortionOfCellsVisible().length - 1  && j == gridView.getPortionOfCellsVisible()[i].length - 1)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0);
                }
                else if(i == 0)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0);
                }
                else if(i == gridView.getPortionOfCellsVisible().length - 1)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0);
                }
                else if(j == 0)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0);
                }
                else if(j == gridView.getPortionOfCellsVisible()[i].length - 1)
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0);
                }
                else
                {
                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0);
                }
                
                if(gridView.getPortionOfCellsVisible()[i][j].getIsAlive() && (ctr <= 1 || ctr >= 4))
                {
                    next[i][j] = gridView.getPortionOfCellsVisible()[i][j];
                    next[i][j].setIsAlive(false);
                }
                else if(!gridView.getPortionOfCellsVisible()[i][j].getIsAlive() && ctr == 3)
                {
                    next[i][j] = gridView.getPortionOfCellsVisible()[i][j];
                    next[i][j].setIsAlive(true);
                }
                else
                    next[i][j] = gridView.getPortionOfCellsVisible()[i][j];
            }
        gridView.setPortionOfCellsVisible(next);
        gridView.repaint();
    }    
}
