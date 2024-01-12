package main.view;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.Caret;

import main.view.student.StudentClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.Rectangle;
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
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedReader;

import java.io.FileReader;

public class Creator {
    private JPanel backNextButtonsPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    
    public Creator() {

    }
    public JButton backButtonCreate() {
        JButton backButton;
        //buttons
        backButton = new JButton("< Back");

        return backButton;
        
    }

    public JButton nextButtonCreate() {
        JButton nextButton;

        nextButton = new JButton("Next >");

        return nextButton;
    }

    public JPanel makeBackNextButtonsPanel(JButton backButton, JButton nextButton) {
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);

        return backNextButtonsPanel;
    }

    public void textFieldFocusListener(JFrame window, JTextField textField, String placeholder, AtomicBoolean textFieldEmptied) { 

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("focusgained");
                System.out.println("textfieldemptied"+getEmptiedState(textField));
                if (textField.getText().equals(placeholder) && getEmptiedState(textField) == false) {
                    textField.setText(""); // Clear the placeholder text when the field gains focus
                    setEmptiedState(textField, true);
                    textField.setForeground(Color.BLACK);
                }

                else if (!textField.getText().equals(placeholder) && !textField.getText().isEmpty() && getEmptiedState(textField) == true) {
                    textField.setForeground(Color.BLACK);
                    setEmptiedState(textField, true);
                }

                else if (textField.getText().equals(placeholder) && getEmptiedState(textField) == true) {
                    textField.setText(""); // Clear the placeholder text when the field gains focus
                    textField.setForeground(Color.BLACK);
                    textFieldEmptied.set(true);
                }

                else if (textField.getText().isEmpty() && getEmptiedState(textField) == true) {
                    System.out.println("going wrell");
                }

                else {
                    System.out.println("textfieldemptied"+getEmptiedState(textField));
                    System.out.println("something went wrong in Creator class, focus gained");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("focuslost to other textbox");
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.PINK);
                    textField.setText(placeholder);
                    textFieldEmptied.set(false);
                }
                
                else if (!textField.getText().isEmpty() && textFieldEmptied.get() == true){
                    textField.setForeground(Color.PINK);
                    //textFieldEmptied.set(false);
                    window.requestFocusInWindow();
                }

                else {
                    System.out.println("something went wrong in focus lost");
                    System.out.println("textfieldemptied?"+textFieldEmptied);
                }
            }
        });

        focusLost(window, textField, placeholder, textFieldEmptied);
    }

    private void focusLost(JFrame window, JTextField textField, String placeholder, AtomicBoolean textFieldEmptied) {
        window.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouse clicked");
                int topLeftX = 332;
                int topLeftY = 221;
                int topRightX = 467;
                int topRightY = 222;
                int bottomLeftX = 334;
                int bottomLeftY = 270;
                int bottomRightX = 467;
                int bottomRightY = 269;

                int width = Math.abs(topRightX - topLeftX);
                int height = Math.abs(bottomLeftY - topLeftY);

                int x = Math.min(Math.min(topLeftX, topRightX), Math.min(bottomLeftX, bottomRightX));
                int y = Math.min(Math.min(topLeftY, topRightY), Math.min(bottomLeftY, bottomRightY));

                Rectangle newBounds = new Rectangle(x, y, width, height);

                boolean pointNotInTextbox = !newBounds.contains(e.getPoint());

                if (pointNotInTextbox && textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.black);
                    setEmptiedState(textField, true);
                    window.requestFocusInWindow();
                }

                else if (pointNotInTextbox && textFieldEmptied.get() == true && !textField.getText().isEmpty()) {
                    System.out.println("2nd option");
                    textField.setForeground(Color.black);
                    window.requestFocusInWindow();
                }

                else if (pointNotInTextbox) {
                    window.requestFocusInWindow();
                    textField.setForeground(Color.black);

                }

                else {
                    System.out.println("something went wrong");
                }

            }
        });

    }

    private boolean getEmptiedState(JTextField textField) {
        return textFieldEmptiedMap.getOrDefault(textField, false);
    }

    private void setEmptiedState(JTextField textField, boolean state) {
        textFieldEmptiedMap.put(textField, state);
    }
}