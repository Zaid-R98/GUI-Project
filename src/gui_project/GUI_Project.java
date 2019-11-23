/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Zaid
 */
public class GUI_Project {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {  
                GameFrame frame = new GameFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
            }
        });
    }
    
}


class GameFrame extends JFrame{

    private GameController gameController;
    private GridView gridView;
    private GameModel gameModel;
    
    public GameFrame() {
         

        setTitle("Game Of Life");
        setPreferredSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.LIGHT_GRAY);

        gridView = new GridView();
        gameModel = new GameModel();

        add(gridView, BorderLayout.CENTER);
        setVisible(true);
        
         GamePanels controllers = new GamePanels();
         add(controllers, BorderLayout.SOUTH);
         
         pack();

         gameController = new GameController(gameModel, gridView,  controllers);

    }
     
}

class GamePanels extends JPanel{
    
      private final JButton Next;
      private final JButton Start;
      private final JComboBox shape;
      private final JComboBox speed;
      private final JLabel generation;
      private int gen = 0;

      public  void setGen(int currentGen) {
        this.gen = currentGen;}
      
    public GamePanels(){
        
        setBackground(Color.LIGHT_GRAY);
        
        String[] Shapes = {"Glider", "R-Pentomino","Small Exploder", "Small spaceship", "Tumbler"};
        shape = new JComboBox(Shapes);
        add(shape);
        
        Next = new JButton("Next");
        add(Next);
        
        Start = new JButton("Start");
        add(Start);
        
        String[] Speeds = {"Slow", "Normal","Fast"};
        speed = new JComboBox(Speeds);
        add(speed);
        
        generation = new JLabel();
        generation.setText("Generation: "+gen);
        add(generation);

        
  }
    
    public void addShapeComboBoxListener(ActionListener ShapeAction){
        shape.addActionListener(ShapeAction);
    }
    
    public void addNextButtonListener(ActionListener NextAction){
        Next.addActionListener(NextAction);
    }
    
    public void addStartButtonListener(ActionListener StartAction){
        Start.addActionListener(StartAction);
    }
    
    public void addSpeedComboBoxListener(ActionListener SpeedAction){
        speed.addActionListener(SpeedAction);
    }


}