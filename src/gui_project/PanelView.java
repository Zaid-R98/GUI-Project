/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class PanelView extends JPanel{
    
      private final JButton Next;
      private final JButton Start;
      private final JComboBox shape;
      private final JComboBox speed;
      private final JLabel generation;
      private int gen;

      public  void setGen(int currentgen) 
      {
          this.gen = currentgen;
      }
      
    public PanelView(){
        
        setBackground(Color.LIGHT_GRAY);
        
        String[] Shapes = {"Glider", "R-Pentomino","Small Exploder", "Small spaceship", "Tumbler"};
        shape = new JComboBox(Shapes);
        add(shape);
        
        Next = new JButton("Next");
        add(Next);
        
        Start = new JButton("Start");
        add(Start);
        
        String[] Speeds = {"Normal", "Slow","Fast"};
        speed = new JComboBox(Speeds);
        add(speed);
        
        generation = new JLabel();
        setGenLabel(0);
        add(generation);    
    }
    
    public void updateViewForAutomaticMode(boolean isAutomaticMode){
        if (isAutomaticMode){
            Next.setEnabled(false);
            Start.setText("Stop");
        }
        else{
            Next.setEnabled(true);
            Start.setText("Start");
        }
    }
    
    public void setGenLabel(int gen)
    { 
        generation.setText("Generation: "+ gen);
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
