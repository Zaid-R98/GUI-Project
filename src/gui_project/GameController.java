/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Cursor;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author azada
 */
public class GameController {

    private GameModel gameModel;
    private GridView gridView;
    private PanelView panel;
    
    private Timer gameSpeedTimer = null;
    private Point stored = null;
    private JPopupMenu popup;
    private boolean check;//stores the state of the checkbox - Zaid
    public GameController(GameModel gameModel, GridView gridView, PanelView panel) {
        this.gameModel = gameModel;
        this.gridView = gridView;
        this.panel = panel;
        
        //Display initial grid
        updateGridViewDisplay();
 
        //Add all listeners       
        initializeGridViewListeners();
        initializePanelViewListeners();
        
        
        //pop up menu for right click
        popup = new JPopupMenu();
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save"); 
        popup.add(save);
        popup.add(load);
        
        gridView.addMouseListener(new MouseAdapter() {
          public void mouseReleased(MouseEvent me){
                 //for right click pop up menu
                if(SwingUtilities.isRightMouseButton(me))
                {popup.show(me.getComponent(), me.getX(), me.getY());}
                }
        });
        
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                   //have to add code to pause before saving 
                   StopTime();
                    SaveGame();
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    StopTime();
                    LoadGame("User's Saved");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void setcheck(boolean setcheck)//true if enabled checkbox is checked.- Zaid
    {
        check=setcheck;
    }
    
    public boolean getcheckstatus()//tells the controler if the jcheckbox is checked or not.- Zaid
    {
        return check;
    }
    private void updateGridViewDisplay(){
        int gridViewWidth = gridView.getWidth();
        int gridViewHeight = gridView.getHeight();

        int currentCellWidth = gameModel.getCurrentCellWidthFromZoomLevel();
        
        int numOfCellsColumns = gridViewWidth /currentCellWidth;
        int numOfCellsRows = gridViewHeight / currentCellWidth;
        
        Cell[][] portionOfCellsVisible = new Cell[numOfCellsRows][numOfCellsColumns];
        
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
    
    private void performOneCellGeneration(){
        int temp = gameModel.incrementGen();
        panel.setGenLabel(temp);
        gameModel.performCellLifeCalculation();
    }
    
    private void initializeGridViewListeners(){
        
        gridView.addGridClickingListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(getcheckstatus()){//checks whether the JcheckBox is enabled or disabled- Zaid
                for (int i = 0; i < gridView.getPortionOfCellsVisible().length; ++i) {
                    for (int j = 0; j < gridView.getPortionOfCellsVisible()[i].length; ++j) { 
                        //Find and set color of cell
                        if (gridView.getPortionOfCellsVisible()[i][j].getRect().contains(e.getPoint())){
                            //Find location of cell in original model from visible portion
                            int offsetRow = gameModel.getTopLeftCellVisible().x + i;
                            int offsetCol = gameModel.getTopLeftCellVisible().y + j;
                            
                            //Flip alive status in model
                            gameModel.setAliveStatusAt(offsetRow, offsetCol, !gameModel.getAliveStatusAt(offsetRow, offsetCol));  
                        }          
                    }
                }
                //Update grid with new model status
                updateGridViewDisplay();
            }  }       
        });
        
        
        gridView.addGridPressingListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stored = e.getPoint();
                gridView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        
        gridView.addGridReleasedListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gridView.setCursor(Cursor.getDefaultCursor());
                stored = null;
            }
        });
        
        gridView.addGridDraggingListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
//                double x_diff = e.getX() - stored.getX();
//                double y_diff = e.getY() - stored.getY();
//                double currentCellWidth = gameModel.getCurrentCellWidthFromZoomLevel();
////                boolean up, down, left, right;
////                up = down = left = right = false;
////                if(x_diff < 0 && Math.abs(x_diff) >= currentCellWidth)
////                    left = true;
////                if(x_diff >= currentCellWidth)
////                    right = true;
////                if(y_diff < 0 && Math.abs(y_diff) >= currentCellWidth)
////                    down = true;
////                if(y_diff >= currentCellWidth)
////                    up = true;    
//                int x_offset = (int) Math.round(x_diff/currentCellWidth);
//                int y_offset = (int) Math.round(y_diff/currentCellWidth);

//                int offsetCol = gameModel.getTopLeftCellVisible().x-x_offset;
//                int offsetRow = gameModel.getTopLeftCellVisible().y-y_offset;
                
               //// int offsetCol = gameModel.getTopLeftCellVisible().x 
////                                + ((x_diff < 0 && Math.abs(x_diff) >= currentCellWidth)? -1:0)
////                                + ((x_diff >= currentCellWidth)? 1:0);
////                int offsetRow = gameModel.getTopLeftCellVisible().y 
////                                + ((y_diff < 0 && Math.abs(y_diff) >= currentCellWidth)? -1:0)
////                                + ((y_diff >= currentCellWidth)? 1:0);
//                gameModel.setTopLeftCellVisible(new Point(offsetRow, offsetCol));
//                updateGridViewDisplay();
                
              
//               
//                stored.x -= (Math.abs(x_diff) >= currentCellWidth)? e.getX():0 ;
//                stored.y -= (Math.abs(y_diff) >= currentCellWidth)? e.getY():0 ;
                
                int x_diff = e.getX() - stored.x;
                int y_diff = e.getY() - stored.y;
                int currentCellWidth = gameModel.getCurrentCellWidthFromZoomLevel();
                
                if(Math.abs(y_diff) >= currentCellWidth)
                    gameModel.getTopLeftCellVisible().x += (y_diff < 0)? 1:-1;
                if(Math.abs(x_diff) >= currentCellWidth)
                    gameModel.getTopLeftCellVisible().y += (x_diff < 0)? 1:-1;
                
                updateGridViewDisplay();
                
                if(Math.abs(x_diff) >= currentCellWidth)
                    stored.x += ((x_diff < 0)? -1:1) * currentCellWidth;
                if(Math.abs(y_diff) >= currentCellWidth)
                    stored.y += ((y_diff < 0)? -1:1) * currentCellWidth;
            }
        });
        
        gridView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateGridViewDisplay();
            }
        });
    }
    
    private void initializePanelViewListeners(){
        
        panel.addNextButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startOneGenerationThread();
            }
        });
        
        
        panel.addEnableItemListener(new ItemListener()//JCheckBox enabled item listener- Zaid
        {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==1)
                {
                    setcheck(true);//updates to check whether to enable mouseclicks or not.
                }
                else
                {
                    setcheck(false);
                }
                
            }
            
        });
        
        
        panel.addStartButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {        
                //Flip Automatic Mode in model
                gameModel.setIsAutomaticMode(!gameModel.getIsAutomaticMode());
                StopTime();
                
                //Change based on new Mode setting
                if (gameModel.getIsAutomaticMode()){  
                    setupTimerForAutomaticMode();
                    panel.updateViewForAutomaticMode(true);
                }
                else{
                    panel.updateViewForAutomaticMode(false);
                }
               
            }
        });
        
        panel.addShapeComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            
            gameModel.Reset();
            panel.Reset();
            StopTime();
            
            
            String selectedShape = (String) ((JComboBox) ae.getSource()).getSelectedItem();
       
            String Shape;
               Shape = selectedShape.toString();
               if(Shape == "Clear")
               {gameModel.Reset();
               panel.Reset();}
               else
                try {
                    //Have function to load this shape
                    LoadGame(Shape);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }
        });
        
        panel.addSpeedComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) ((JComboBox) e.getSource()).getSelectedItem();
                //Stop the timer before making chnages
                StopTime();
                
                if (selectedOption.equals("Slow")){
                    gameModel.setGameSpeed(GameSpeed.SLOW);
                }
                else if (selectedOption.equals("Normal")){
                    gameModel.setGameSpeed(GameSpeed.NORMAL);
                }
                else{
                    gameModel.setGameSpeed(GameSpeed.FAST);
                }
                
                //if start hasn't been pressed, we will not start timer
                if(gameModel.getIsAutomaticMode())
                    setupTimerForAutomaticMode();
            }
        });
        
        panel.addZoomComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) ((JComboBox) e.getSource()).getSelectedItem();
                if (selectedOption.equals("Small")){
                    gameModel.setZoomLevel(ZoomLevel.SMALL);
                }
                else if (selectedOption.equals("Medium")){
                    gameModel.setZoomLevel(ZoomLevel.MEDIUM);
                }
                else{
                    gameModel.setZoomLevel(ZoomLevel.BIG);
                }
                updateGridViewDisplay();
            }
        });
        
    }
    
    
    private void StopTime(){
        
                
                //Always stop existing timer before making changes
                if (gameSpeedTimer != null){
                    gameSpeedTimer.stop();
                }}
    
    private void setupTimerForAutomaticMode(){
        gameSpeedTimer = new Timer(gameModel.getNumericDelayOfGameSpeed(),
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startOneGenerationThread();
            }
        });
        gameSpeedTimer.start();
    }
    
    private void SaveGame() throws FileNotFoundException, IOException {
        String tempSpeed = gameModel.getGameSpeed().toString();
        String tempZoom = gameModel.getZoomLevel().toString();
        int tempGen = gameModel.getgeneration();
        File outfile = new File("User's Saved.txt");
        FileWriter fw = new FileWriter(outfile);
        PrintWriter pw = new PrintWriter(outfile);
        
        pw.println(tempSpeed);
        pw.println(tempZoom);
        pw.println(tempGen);
        for (int i = 0; i < gameModel.getCellGrid().length; i++) {
            for (int j = 0; j < gameModel.getCellGrid()[i].length; j++) {
                if (gameModel.getAliveStatusAt(i, j))
                    pw.print(i+","+j+" ");
                }
            }
      pw.close();    
    }
    
    private void LoadGame(String filename) throws FileNotFoundException{
        File file = new File(filename+".txt");
        Scanner sc = new Scanner(file);
 
       gameModel.setGameSpeed(GameSpeed.valueOf(sc.next()));
       gameModel.setZoomLevel(ZoomLevel.valueOf(sc.next()));
       gameModel.setgeneration(sc.nextInt());

       while (sc.hasNext()) {
            String parts[] = sc.next().split(",");
           gameModel.setAliveStatusAt(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), true);
        }
       updateGridViewDisplay();
       sc.close();
        
    }
    
    private void startOneGenerationThread(){
        new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (gameModel){
                            performOneCellGeneration();
                            updateGridViewDisplay();
                        }
                    }
                }).start();
    }
   
    
    
}
        
      
        
     





//Abdullah's code V1    
//    this is the most basic algorithm, please dont kill me xD
//    private void CellLife()
//    {
//        Cell next[][] = new Cell [gridView.getPortionOfCellsVisible().length][gridView.getPortionOfCellsVisible()[0].length];
//        for(int i = 0; i < gridView.getPortionOfCellsVisible().length; ++i)
//            for(int j = 0; j < gridView.getPortionOfCellsVisible()[0].length; ++j)
//            {
//                int ctr = 0;
//                if(i == 0 && j == 0)
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0);
//                }
//                else if(i == 0 && (j == gridView.getPortionOfCellsVisible()[0].length - 1))
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0);
//                }
//                else if((i == gridView.getPortionOfCellsVisible().length - 1) && j == 0)
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0);
//                }
//                else if((i == gridView.getPortionOfCellsVisible().length - 1)  && (j == gridView.getPortionOfCellsVisible()[0].length - 1))
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0);
//                }
//                else if(i == 0)
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0);
//                }
//                else if(i == gridView.getPortionOfCellsVisible().length - 1)
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0);
//                }
//                else if(j == 0)
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0);
//                }
//                else if(j == gridView.getPortionOfCellsVisible()[0].length - 1)
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0);
//                }
//                else
//                {
//                    ctr = ((gridView.getPortionOfCellsVisible()[i][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i-1][j-1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j+1].getIsAlive())? 1:0) +
//                        ((gridView.getPortionOfCellsVisible()[i+1][j-1].getIsAlive())? 1:0);
//                }
//                
//                if(gridView.getPortionOfCellsVisible()[i][j].getIsAlive() && (ctr <= 1 || ctr >= 4))
//                {
//                    next[i][j] = new Cell(false);
//                    next[i][j].setRect((Rectangle2D.Double)gridView.getPortionOfCellsVisible()[i][j].getRect());
//                }
//                else if(!gridView.getPortionOfCellsVisible()[i][j].getIsAlive() && ctr == 3)
//                {
//                    next[i][j] = new Cell(true);
//                    next[i][j].setRect((Rectangle2D.Double)gridView.getPortionOfCellsVisible()[i][j].getRect());
//                }
//                else
//                {
//                    next[i][j] = new Cell(gridView.getPortionOfCellsVisible()[i][j].getIsAlive());
//                    next[i][j].setRect((Rectangle2D.Double)gridView.getPortionOfCellsVisible()[i][j].getRect());
//                }
//            }
//        gridView.setPortionOfCellsVisible(next);
//        gridView.repaint();
//    }




    
//    ZAID'S CODE    
//    private void CellLife()
//    {
//        int lifestatus;//check whether alive or not (the cell)
//        int neisum=0;
//        Cell next[][] = new Cell[gridView.getPortionOfCellsVisible().length][gridView.getPortionOfCellsVisible()[0].length];
//        for(int i = 0; i < gridView.getPortionOfCellsVisible().length; ++i)
//            for(int j = 0; j < gridView.getPortionOfCellsVisible()[i].length; ++j)
//            {
//               if(gameModel.getAliveStatusAt(i, j))
//                   lifestatus=1;
//                else
//                   lifestatus=0;
//              
//               neisum=countneighbors(gridView.getPortionOfCellsVisible(),i,j);
//              
//               if(lifestatus==0 && neisum==3)
//               {
//                   next[i][j].setIsAlive(true);
//               }
//               else if ( lifestatus==1 && (neisum<2 || neisum>3))
//                   next[i][j].setIsAlive(false);
//               else
//               {
//                   if(lifestatus==1)
//                       next[i][j].setIsAlive(true);
//                   else
//                      next[i][j].setIsAlive(false);
//               }
//            }
//        
//        for(int i=0;i<next.length;++i)
//            for(int j=0;j<next[i].length;++j)
//               gridView.getPortionOfCellsVisible()[i][j]=next[i][j];
//    }
//
//    private int countneighbors(Cell array2d[][], int x , int y)
//    {
//        boolean ctr;
//        int sum=0;
//
//        for(int i=-1;i<=1;++i)
//            if(array2d[x+i][y]!=null)
//            {
//                 ctr=array2d[x+i][y].getIsAlive();
//                 if(ctr)
//                    sum+=1;
//            }   
//        
//        for(int i=-1;i<=1;++i)
//            if(array2d[x+i][y]!=null)
//            {
//                 ctr=array2d[x+i][y-1].getIsAlive();
//                 if(ctr)
//                    sum+=1;
//            }   
//        
//         for(int i=-1;i<=1;++i)
//            if(array2d[x+i][y]!=null)
//            {
//                 ctr=array2d[x+i][y+1].getIsAlive();
//                 if(ctr)
//                    sum+=1;
//            }   
//      return sum;
//    }
