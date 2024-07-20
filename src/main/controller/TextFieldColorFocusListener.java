package main.controller;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;

import main.controller.Creator;
import main.model.Set;

import java.awt.Container;


public class TextFieldColorFocusListener {
    private JFrame window;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Set set;
    private FileHandling fileHandler = new FileHandling();
    private int textboxCounter;
    private JTextField textField = new JTextField();
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String>textFieldPanelText = new ArrayList<>();
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Boolean focusGranted = true;
    private int attachedBoxes = 0;
    private int maxAttachedBoxes = 0;
    private boolean loaded;
    private String filePath;
    private String text = textField.getText().trim();
    private CreateButton createButton = new CreateButton();
    
    public TextFieldColorFocusListener() {
        this.set = Set.getInstance();
        this.window = set.getWindow();
    }

    public void textFieldFocusListener(JTextField focusTextField, String placeholder) { 
        focusTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focusTextField.setCaretPosition(focusTextField.getText().length());
                if (focusGranted) {
                    focusTextField.setForeground(Color.gray);
                    if (focusTextField.getText().equals(placeholder) && set.getEmptiedState(focusTextField) == false && set.getLoadedState(textField) == false) {
                        focusTextField.setText("");
                        set.setEmptiedState(textField, true);
                    }else if (!focusTextField.getText().equals(placeholder) && !focusTextField.getText().isEmpty() && set.getEmptiedState(textField) == true) {
                        set.setEmptiedState(focusTextField, true);
                    }else if (focusTextField.getText().equals(placeholder) && set.getEmptiedState(textField) == true && set.getLoadedState(textField) == false) {
                        //System.out.println("focuslistener#1.3");
                        focusTextField.setText(""); // Clear the placeholder text when the field gains focus
                        set.setEmptiedState(focusTextField, true);
                        set.setSaveable(true);
                    }
                    
                    else {
                        //textField.setHorizontalAlignment(JTextField.LEFT);
                        //System.out.println("something went wrong in Creator class, focus gained");
                        //System.out.println("focuslistener#1.4");
                    }
                    //previousTextbox = textField;
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                focusLostLeftAlign();
                focusTextField.setForeground(Color.lightGray);
                FontMetrics fontMetrics = textField.getFontMetrics(textField.getFont());
                System.out.println("focus is lost "+fontMetrics.stringWidth(textField.getText())+ text);
                if (fontMetrics.stringWidth(textField.getText()) >= textField.getWidth()) {//if is already less than max with username
                    focusTextField.setCaretPosition(0);
                    }
                
                if (focusTextField.getText().isEmpty()) {
                    focusTextField.setText(placeholder);
                    set.setEmptiedState(focusTextField, false);
                }
                
                else if (!focusTextField.getText().isEmpty() && set.getEmptiedState(textField) == true){
                }

            }
        });
        focusLost(placeholder);
    }

    private void focusLost(String placeholder){
        window.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                    textField.setForeground(Color.GRAY);
                    set.setEmptiedState(textField, false);
                    window.requestFocusInWindow();
                }
                else if (pointNotInTextbox &&  set.getEmptiedState(textField) == true && !textField.getText().isEmpty()) {
                    window.requestFocusInWindow();
                }
                else if (pointNotInTextbox) {
                    window.requestFocusInWindow();

                }
                else {
                    System.out.println("something went wrong");
                }
            }
        });
    }

    private void focusLostLeftAlign() {
        textField.grabFocus();
        textField.setCaretPosition(0);
        window.requestFocusInWindow();
    }

}