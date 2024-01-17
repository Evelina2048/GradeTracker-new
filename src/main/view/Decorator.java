package main.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
///
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.view.student.StudentClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.io.FileWriter;
import java.io.IOException;

import java.io.BufferedReader;

import java.io.FileReader;

///

//key listening

public class Decorator {
    private int windowX;
    private int windowY;
    
    public Decorator() {

    }

    public JPanel InstructionsPanelDecorate(JFrame window, JPanel instructionsPanel, JLabel instructionsWords) {
        instructionsPanel= new JPanel();
        Color instructionsColor = Color.decode("#7A6D6D");
        instructionsPanel.setBackground(instructionsColor);
        
        instructionsPanel.add(instructionsWords);
        Color instructionsWordsColor = Color.decode("#edebeb");
        instructionsWords.setForeground(instructionsWordsColor); //color
        window.add(instructionsPanel, BorderLayout.NORTH);
    
        //set the font for instructions
        Font instructionsFont = new Font("Roboto", Font.PLAIN, 30); // Change the font and size here
        instructionsWords.setFont(instructionsFont);

        return instructionsPanel;
    }

    // public void radioButtonSetUp() {
    //     buttonGroup = new ButtonGroup();
    //     Color choicesPanelColor = Color.decode("#AFA2A2");
    
    //     choicesPanel= new JPanel(new GridBagLayout());
    //     choicesPanel.setBackground(choicesPanelColor);
    
    //     //initialize buttons with color
    //     teacherButton = new JRadioButton("Teacher");
    //     choicesButtonDecorate(teacherButton);
    //     teacherButton.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //            studentOrTeacher = teacherButton.getText();
    //            moveOnPossible = true;
    //         }
            
    //     });
    
    //     studentButton = new JRadioButton("Student");
    //     choicesButtonDecorate(studentButton);
    //     studentButton.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //             studentOrTeacher = studentButton.getText();
    //             moveOnPossible = true;
    //         }
    //     });
    
    //     addToChoicesPanel(buttonGroup, teacherButton, studentButton, choicesPanel);
    
    //     this.add(choicesPanel);
    // }

    public GridBagConstraints choiceGbc() {
        //radio buttons distance
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 0, 0); // Increase the horizontal spacing between components
        return gbc;
    }

    public void setWindowX(int newWindowX) {
        windowX = newWindowX;
    }

    public void setWindowY(int newWindowY) {
        windowY = newWindowY;
    }

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

    public JTextField decorateTextBox(String placeholderText) {
       System.out.println("test2 in decorateTextBix"); 
       JTextField textField = new JTextField(10);
       System.out.println("test3 (should be false) "+ (textField == null));
       textField.setPreferredSize(new Dimension(10, 50)); // Set the height to 50 pixels
       textField.setFont(new Font("Roboto", Font.PLAIN, 14));
       textField.setForeground(Color.gray);
       textField.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

       Color defaultTextColor = Color.decode("#B0B0B0");
       textField.setSelectedTextColor(defaultTextColor);

       textField.setHorizontalAlignment(JTextField.CENTER);
       textField.setText(placeholderText);
        
       System.out.println("test4 (should be true) "+ (textField.getText() == placeholderText));
       return textField;
    }

    
    // public JButton createJButton() {
    //     JButton newClassButton = new JButton("New Class");

    //     return newClassButton;
    // }

    // public void createRadioButtons() {

    // }

}