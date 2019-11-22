/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
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
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
                frame.setTitle("Game Of Life");
                frame.setSize(500, 500);
                frame.getContentPane().setBackground(Color.LIGHT_GRAY);
            
                
                
            }
        });
    }
    
}


class GameFrame extends JFrame{

    public GameFrame() {
        setBackground(Color.LIGHT_GRAY);
         JPanel boxes = new JPanel();
         boxes.setLayout(new GridLayout(25,25));
         boxes.setBackground(Color.DARK_GRAY);
      
         
         for (int i = 0; i < 500; i++) {
             JLabel grids = new JLabel(" ");
             grids.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
             boxes.add(grids);
        }
         
         
         
         add(boxes, BorderLayout.CENTER);
         GamePanels controllers = new GamePanels();
         add(controllers, BorderLayout.SOUTH);
    }
     
}

class GamePanels extends JPanel{
    public GamePanels(){
        
        setBackground(Color.LIGHT_GRAY);
        String[] Shapes = {"Glider", "R-Pentomino","Small Exploder", "Small spaceship", "Tumbler"};
        add(new JComboBox(Shapes));
        
        add(new JButton("Next"));
        add(new JButton("Start"));
        
        String[] Speed = {"Slow", "Normal","Fast"};
        add(new JComboBox(Speed));
        
        JLabel generation = new JLabel();
        generation.setText("Generation: ");
        add(generation);

        }


}