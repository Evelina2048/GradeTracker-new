package main.controller;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
///
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import java.awt.Dimension;

import main.model.Set;

///

//key listening

public class Decorator {
    JFrame window;
    Set set;
    
    public Decorator() {
        set = Set.getInstance();
        window = set.getWindow();
    }
    

    public JPanel InstructionsPanelDecorate(JPanel instructionsPanel, JLabel instructionsWords) {
        JFrame window = set.getWindow();
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

    public void areYouSureMessageSetUp(JButton button, JTextField textField) {
        Creator creator = new Creator();
        JDialog dialog = new JDialog(window, true);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel("<html><center>Deleting this class will delete <br>its loaded information.<br>Do you wish to continue?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);

        JButton yesButton = new JButton("Yes");
        yesButton.setVisible(true);
        dialog.add(yesButton);

        JButton noButton = new JButton("Cancel");
        noButton.setVisible(true);
        dialog.add(noButton);

        dialog.setSize(250,120);
        JPanel panel = new JPanel();
        System.out.println("does textfield be null?: "+textField==null+ "textfield text"+ textField.getText());
        panel.add(textField);

        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {     
                textField.setVisible(false);
                
                creator.deleteTextBox(panel);
                dialog.setVisible(false);
                dialog.dispose(); 
                
            }
        });
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose(); 
            }
        });
        
        dialog.setLocationRelativeTo(window);
        //dialog.setLocation(null); 
        dialog.setVisible(true);
    }

    public void errorMessageSetUp(JRadioButton button) {
        JDialog dialog = new JDialog(window, true);
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
       JTextField textField = new JTextField(10);
       textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 50)); // Set the height to 50 pixels
       textField.setFont(new Font("Roboto", Font.PLAIN, 14));
       textField.setForeground(Color.gray);
       textField.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

       Color defaultTextColor = Color.decode("#B0B0B0");
       textField.setSelectedTextColor(defaultTextColor);

       textField.setHorizontalAlignment(JTextField.CENTER);
       textField.setText(placeholderText);
       return textField;
    }
}