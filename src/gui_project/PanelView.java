/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author User
 */
public class PanelView extends JPanel{
    
      private final JButton Next;
      private final JButton Start;
      private final JComboBox shape;
      private final JComboBox speed;
      private final JComboBox zoom;
      private final JLabel generation;
      private int gen;
      private final JPanel checkboxPanel;//Added Panel to store the CheckBox- Zaid
      private final JCheckBox Enable;
    
      public  void setGen(int currentgen) 
      {
          this.gen = currentgen;
      }
      
    public PanelView(){
        
        setBackground(Color.LIGHT_GRAY);
        
        String[] Shapes = {"Clear", "Glider", "R-Pentomino","Small Exploder", "Small spaceship", "Tumbler"};
        shape = new JComboBox(Shapes);
        add(shape);
        
        
        Next = new JButton("Next");
        add(Next);
        
        Start = new JButton("Start");
        add(Start);
        
        String[] Speeds = {"Slow", "Normal", "Fast"};
        speed = new JComboBox(Speeds);
        speed.setEditable(false);
        speed.setSelectedItem("Normal");
        add(speed);
        
        String[] zoomLevels = {"Small", "Medium", "Large"};
        zoom = new JComboBox(zoomLevels);
        zoom.setEditable(false);
        zoom.setSelectedItem("Medium");
        add(zoom);     
        
        generation = new JLabel();
        setGenLabel(0);
        add(generation);    

        add(generation);  
        //creating the edit option checkbox menu.
        checkboxPanel=new JPanel();
        checkboxPanel.setBackground(Color.LIGHT_GRAY);
        checkboxPanel.setBorder(BorderFactory.createTitledBorder("Edit Mode"));
        Enable= new JCheckBox("Enable");
        checkboxPanel.add(Enable);
        add(checkboxPanel);
    }
    
    public void updateViewForAutomaticMode(boolean isAutomaticMode){
        if (isAutomaticMode){
            Next.setEnabled(false);
            Start.setText("Stop");
            Enable.setSelected(false);//CheckBox disables in automatic mode- Zaid
            Enable.setEnabled(false);
        }
        else{
            Next.setEnabled(true);//Revert changes once automatic mode is done - Zaid
            Enable.setEnabled(true);
            Start.setText("Start");
        }
    }
    
    public void setGenLabel(int gen)
    { 
        generation.setText("Generation: "+ gen);
    }
    public void Reset()
    { 
        setGenLabel(0);
       speed.setSelectedItem("Normal");
       zoom.setSelectedItem("Medium");
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
    
    public void addZoomComboBoxListener(ActionListener listener){
        zoom.addActionListener(listener);
    }
    
    public void addEnableItemListener(ItemListener listener)//adding the item listener to the JCheckBox- Zaid
    {
        Enable.addItemListener(listener);
    }
}
