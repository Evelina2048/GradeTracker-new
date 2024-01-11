package main.view;

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
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedReader;

import java.io.FileReader;

public class Creator {
    private JPanel backNextButtonsPanel;
    
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
                if (textField.getText().equals(placeholder) && !textFieldEmptied.get()) {
                    textField.setText(""); // Clear the placeholder text when the field gains focus
                    textField.setForeground(Color.BLACK); // Change the text color when typing
                }
            }
        });

        window.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("helloooojodso");
                System.out.println("bounds:" + e.getX() + " " + e.getY());

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

                if (!newBounds.contains(e.getPoint())) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    textFieldEmptied.set(false);
                    window.requestFocus();
                }
            }
        });
    }
}