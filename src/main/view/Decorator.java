package main.view;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

//key listening
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

public class Decorator {
    private int windowX;
    private int windowY;
    
    public Decorator() {

    }

    public void setWindowX(int newWindowX) {
        windowX = newWindowX;
    }

    public void setWindowY(int newWindowY) {
        windowY = newWindowY;
    }

    // public void showWindow(JFrame window, int windowX, int windowY) {
    //     if (windowX != 0 && windowY != 0) {
    //         window.setLocation(windowX, windowY);
    //         setWindowX(windowX);
    //         setWindowY(windowY);
     
    //     }
     
    //     else {
    //      window.setLocationRelativeTo(null);
    //     }
     
    //      window.setVisible(true);
    //  }

    public void errorMessageSetUp(JFrame window, JRadioButton button) {
        JDialog dialog = new JDialog(window, null, true);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel("<html><center>Please choose an option");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        JButton okButton = new JButton("OK");
        okButton.setVisible(true);
        dialog.add(okButton);
        dialog.setSize(200,90);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose(); 
            }
        });
        
        dialog.setLocationRelativeTo(button);
        dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
        dialog.setVisible(true);
    }

    public void hideWindow(JPanel instructionsPanel, JPanel choicesPanel, JPanel backNextButtonsPanel) {
        instructionsPanel.setVisible(false);
        choicesPanel.setVisible(false);
        backNextButtonsPanel.setVisible(false);
    
    }

}