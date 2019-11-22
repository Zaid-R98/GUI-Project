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
public class GameController {

    private GameModel gameModel;
    private GridView gridView;
    
    
    public GameController(GameModel gameModel, GridView gridView) {
        this.gameModel = gameModel;
        this.gridView = gridView;
        updateGridViewDisplay();
    }
    
    private void updateGridViewDisplay(){
        int gridViewWidth = gridView.getWidth();
        int gridViewHeight = gridView.getHeight();

        int currentCellWidth = gameModel.getCurrentCellWidth();
        
        int numOfCellsRows = gridViewWidth /currentCellWidth;
        int numOfCellsCols = gridViewHeight / currentCellWidth;
        
        Cell[][] portionOfCellsVisible = new Cell[numOfCellsCols][numOfCellsRows];
        Rectangle2D.Double[][] portionOfCellsVisibleAsRects = new Rectangle2D.Double[numOfCellsCols][numOfCellsRows];
        
        //Initialize visible portion
        for (int i = 0; i < portionOfCellsVisibleAsRects.length; ++i) {
            int currentYPos = i * currentCellWidth;
            for (int j = 0; j < portionOfCellsVisibleAsRects[i].length; ++j) {
                int currentXPos = j * currentCellWidth;
                
                //Create a rectangle with correct xpos, ypos
                portionOfCellsVisibleAsRects[i][j] = new Rectangle2D.Double(
                        currentXPos, 
                        currentYPos, 
                        currentCellWidth,
                        currentCellWidth);
                
                //Get cell status by top-left corner,  offset by current position
                boolean currentAliveStatus = 
                        gameModel.getAliveStatusAt(
                                i + gameModel.getTopLeftCellVisible().x, 
                                j + gameModel.getTopLeftCellVisible().y);
                
                portionOfCellsVisible[i][j] = new Cell(currentAliveStatus);      
            }
        }
        
        //Update Grid View information
        gridView.setPortionOfCellsVisible(portionOfCellsVisible);
        gridView.setPortionOfCellsVisibleAsRects(portionOfCellsVisibleAsRects);
        //Request Grid View to refresh UI
        gridView.repaint();
        
    }
    
    
    
}
