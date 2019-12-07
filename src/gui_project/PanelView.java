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
    
      private final JButton next;
      private final JButton start;
      private final JComboBox shape;
      private final JComboBox speed;
      private final JComboBox zoom;
      private final JLabel generation;
      private final JPanel checkboxPanel;//Added Panel to store the CheckBox- Zaid
      private final JCheckBox enable;
    
      
      String[] shapes = {"Clear", "Glider", "R-Pentomino","Small Exploder", "Small spaceship", "Tumbler"};
      String[] speeds = {"Slow", "Normal", "Fast"};
      String[] zoomLevels = {"Small", "Medium", "Large"};
    public PanelView(){
        
        setBackground(Color.LIGHT_GRAY);
        
        
        shape = new JComboBox(shapes);
        add(shape);
        
        
        next = new JButton("Next");
        add(next);
        
        start = new JButton("Start");
        add(start);
        
       
        speed = new JComboBox(speeds);
        speed.setEditable(false);
        speed.setSelectedItem("Normal");
        add(speed);
        
        
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
        enable= new JCheckBox("Enable");
        enable.setSelected(true);
        checkboxPanel.add(enable);
        add(checkboxPanel);
    }
    
    public void updateViewForAutomaticMode(boolean isAutomaticMode){
        if (isAutomaticMode){
            next.setEnabled(false);
            start.setText("Stop");
            enable.setSelected(false);//CheckBox disables in automatic mode- Zaid
            enable.setEnabled(false);
        }
        else{
            next.setEnabled(true);//Revert changes once automatic mode is done - Zaid
            enable.setEnabled(true);
            start.setText("Start");
        }
    }
    
    public void setGenLabel(int gen)
    { 
        generation.setText("Generation: "+ gen);
    }
    
     public void setSpeedCombo(String inpSpeed)
    { 
        for (int i = 0; i < speeds.length; i++) {
            if (inpSpeed.toLowerCase().equals(speeds[i].toLowerCase())) {
                speed.setSelectedIndex(i);
            }
        }
    }
     
      public void setZoomCombo(String inpZoom)
    { 
         for (int i = 0; i < zoomLevels.length; i++) {
            if (inpZoom.toLowerCase().equals(zoomLevels[i].toLowerCase())) {
                zoom.setSelectedIndex(i);
            }
        }
    }
    public void reset()
    { 
        setGenLabel(0);
        setSpeedCombo("Normal");
        setZoomCombo("Medium");
        updateViewForAutomaticMode(false);
    }
    
    public void addShapeComboBoxListener(ActionListener ShapeAction){
        shape.addActionListener(ShapeAction);
    }
    
    public void addNextButtonListener(ActionListener NextAction){
        next.addActionListener(NextAction);
    }
    
    public void addStartButtonListener(ActionListener StartAction){
        start.addActionListener(StartAction);
    }
    
    public void addSpeedComboBoxListener(ActionListener SpeedAction){
        speed.addActionListener(SpeedAction);
    }
    
    public void addZoomComboBoxListener(ActionListener listener){
        zoom.addActionListener(listener);
    }
    
    public void addEnableItemListener(ItemListener listener)//adding the item listener to the JCheckBox- Zaid
    {
        enable.addItemListener(listener);
    }
}
