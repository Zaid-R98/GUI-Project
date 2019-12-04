/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

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





//hello
class GameFrame extends JFrame{

    private GameController gameController;
    private GridView gridView;
    private GameModel gameModel;
    
    public GameFrame() {
         

        setTitle("Game Of Life");
        setPreferredSize(new Dimension(700, 500));
        getContentPane().setBackground(Color.LIGHT_GRAY);

        gridView = new GridView();
        gameModel = new GameModel();

        add(gridView, BorderLayout.CENTER);
        setVisible(true);
        
         PanelView controllers = new PanelView();
         add(controllers, BorderLayout.SOUTH);
         
         pack();

         gameController = new GameController(gameModel, gridView,  controllers);

    }    
}

