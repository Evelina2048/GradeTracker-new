package controller;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.awt.FontMetrics;

import model.Set;
import model.SetState;

public class TextFieldColorFocusListener {
    private JFrame window;
    private Set set;
    private SetState setState;
    private JTextField textField = new JTextField();
    private Boolean focusGranted = true;

    public TextFieldColorFocusListener() {
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.window = set.getWindow();
    }

    public void textFieldFocusListener(JTextField focusTextField, String placeholder) { 
        focusTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focusTextField.setCaretPosition(focusTextField.getText().length());
                if (focusGranted) {
                    focusTextField.setForeground(Color.gray);
                    if (focusTextField.getText().equals(placeholder) && setState.getEmptiedState(focusTextField) == false && setState.getLoadedState(textField) == false) {
                        focusTextField.setText("");
                        setState.setEmptiedState(textField, true);
                    }else if (!focusTextField.getText().equals(placeholder) && !focusTextField.getText().isEmpty() && setState.getEmptiedState(textField) == true) {
                        setState.setEmptiedState(focusTextField, true);
                    }else if (focusTextField.getText().equals(placeholder) && setState.getEmptiedState(textField) == true && setState.getLoadedState(textField) == false) {
                        focusTextField.setText(""); // Clear the placeholder text when the field gains focus
                        setState.setEmptiedState(focusTextField, true);
                        setState.setSaveable(true);
                    }
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                focusTextField.setForeground(Color.lightGray);
                FontMetrics fontMetrics = textField.getFontMetrics(textField.getFont());
                if (fontMetrics.stringWidth(textField.getText()) >= textField.getWidth()) {//if is already less than max with username
                    focusTextField.setCaretPosition(0);
                    }

                if (focusTextField.getText().isEmpty()) {
                    focusTextField.setText(placeholder);
                    setState.setEmptiedState(focusTextField, false);
                }

                else if (!focusTextField.getText().isEmpty() && setState.getEmptiedState(textField) == true){
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
                    setState.setEmptiedState(textField, false);
                    window.requestFocusInWindow();

                    textField.grabFocus();
                    textField.setCaretPosition(0);
                    window.requestFocusInWindow();
                }
                else if (pointNotInTextbox &&  setState.getEmptiedState(textField) == true && !textField.getText().isEmpty()) {
                    window.requestFocusInWindow();

                    textField.grabFocus();
                    textField.setCaretPosition(0);
                    window.requestFocusInWindow();
                }
                else if (pointNotInTextbox) {
                    window.requestFocusInWindow();

                    textField.grabFocus();
                    textField.setCaretPosition(0);
                    window.requestFocusInWindow();
                }
                else {
                    System.out.println("something went wrong");
                }
            }
        });
    }

}