package main.controller;

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

import java.util.ArrayList;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;

import main.controller.Creator;
import main.model.Set;

import java.awt.Container;


public class Creator {
    private JFrame window;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Set set;
    private int textboxCounter;
    private JTextField textField = new JTextField();
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String>textFieldPanelText = new ArrayList<>();
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Boolean focusGranted = true;
    private boolean loaded;
    private String text = textField.getText().trim();
    private CreateButton createButton = new CreateButton();
    private TextFieldColorFocusListener colorFocusListener;
    
    public Creator() {
        this.set = Set.getInstance();
        this.window = set.getWindow();
        colorFocusListener = new TextFieldColorFocusListener();
    }

    // public void textFieldFocusListener(JTextField focusTextField, String placeholder) { 
    //     focusTextField.addFocusListener(new FocusAdapter() {
    //         @Override
    //         public void focusGained(FocusEvent e) {
    //             focusTextField.setCaretPosition(focusTextField.getText().length());
    //             if (focusGranted) {
    //                 focusTextField.setForeground(Color.gray);
    //                 if (focusTextField.getText().equals(placeholder) && set.getEmptiedState(focusTextField) == false && set.getLoadedState(textField) == false) {
    //                     focusTextField.setText("");
    //                     set.setEmptiedState(textField, true);
    //                 }else if (!focusTextField.getText().equals(placeholder) && !focusTextField.getText().isEmpty() && set.getEmptiedState(textField) == true) {
    //                     set.setEmptiedState(focusTextField, true);
    //                 }else if (focusTextField.getText().equals(placeholder) && set.getEmptiedState(textField) == true && set.getLoadedState(textField) == false) {
    //                     //System.out.println("focuslistener#1.3");
    //                     focusTextField.setText(""); // Clear the placeholder text when the field gains focus
    //                     set.setEmptiedState(focusTextField, true);
    //                     set.setSaveable(true);
    //                 }
                    
    //                 else {
    //                     //textField.setHorizontalAlignment(JTextField.LEFT);
    //                     //System.out.println("something went wrong in Creator class, focus gained");
    //                     //System.out.println("focuslistener#1.4");
    //                 }
    //                 //previousTextbox = textField;
    //             }
    //         }
    //         @Override
    //         public void focusLost(FocusEvent e) {
    //             focusLostLeftAlign();
    //             focusTextField.setForeground(Color.lightGray);
    //             FontMetrics fontMetrics = textField.getFontMetrics(textField.getFont());
    //             System.out.println("focus is lost "+fontMetrics.stringWidth(textField.getText())+ text);
    //             if (fontMetrics.stringWidth(textField.getText()) >= textField.getWidth()) {//if is already less than max with username
    //                 focusTextField.setCaretPosition(0);
    //                 }
                
    //             if (focusTextField.getText().isEmpty()) {
    //                 focusTextField.setText(placeholder);
    //                 set.setEmptiedState(focusTextField, false);
    //             }
                
    //             else if (!focusTextField.getText().isEmpty() && set.getEmptiedState(textField) == true){
    //             }

    //         }
    //     });
    //     focusLost(placeholder);
    // }

    // private void focusLost(String placeholder){
    //     window.getContentPane().addMouseListener(new MouseAdapter() {
    //         @Override
    //         public void mouseClicked(MouseEvent e) {
    //             int topLeftX = 332;
    //             int topLeftY = 221;
    //             int topRightX = 467;
    //             int topRightY = 222;
    //             int bottomLeftX = 334;
    //             int bottomLeftY = 270;
    //             int bottomRightX = 467;
    //             int bottomRightY = 269;

    //             int width = Math.abs(topRightX - topLeftX);
    //             int height = Math.abs(bottomLeftY - topLeftY);

    //             int x = Math.min(Math.min(topLeftX, topRightX), Math.min(bottomLeftX, bottomRightX));
    //             int y = Math.min(Math.min(topLeftY, topRightY), Math.min(bottomLeftY, bottomRightY));

    //             Rectangle newBounds = new Rectangle(x, y, width, height);

    //             boolean pointNotInTextbox = !newBounds.contains(e.getPoint());

    //             if (pointNotInTextbox && textField.getText().isEmpty()) {
    //                 textField.setText(placeholder);
    //                 textField.setForeground(Color.GRAY);
    //                 set.setEmptiedState(textField, false);
    //                 window.requestFocusInWindow();
    //             }
    //             else if (pointNotInTextbox &&  set.getEmptiedState(textField) == true && !textField.getText().isEmpty()) {
    //                 window.requestFocusInWindow();
    //             }
    //             else if (pointNotInTextbox) {
    //                 window.requestFocusInWindow();

    //             }
    //             else {
    //                 System.out.println("something went wrong");
    //             }
    //         }
    //     });
    // }

    // private void focusLostLeftAlign() {
    //     textField.grabFocus();
    //     textField.setCaretPosition(0);
    //     window.requestFocusInWindow();
    // }

    public JTextField createTextBox(String placeholder, String my_type, Boolean boxLoaded) {
        loaded = boxLoaded;
        int width = 144;
        int height = 50;
        Decorator decorate = new Decorator();
        textboxCounter++;
        if (textboxCounter <= 30 && my_type.equals("JTextField")) {
            textField = decorate.decorateTextBox(placeholder);
            createButton.addDocumentListenerForSaving(textField);
            textFieldPanel.add(textField); 
            textFieldPanelText.add(textField.getText());
            textField.setPreferredSize(new Dimension(width, height));
            window.add(textFieldPanel);
            colorFocusListener.textFieldFocusListener(textField, placeholder);
            windowFix();
        }
        else if (my_type.equals("JLabel")) {
            JLabel toAddType = new JLabel(placeholder);
            textFieldPanel.add(toAddType);
        }
        checkIfLoadedAndAction();
        set.setTextFieldPanel(textFieldPanel);
        return textField;
    }

    private void checkIfLoadedAndAction() {
        if (loaded) {
            set.setEmptiedState(textField, true);
            set.setLoadedState(textField, true);

            textField.setForeground(Color.lightGray);
        }
        else if (loaded == false) {
            set.setLoadedState(textField, false);
        }
    }

    public void setTextFieldContainer(JPanel thisTextFieldPanel) {
        textFieldPanel = thisTextFieldPanel;
    }

    public JPanel getTextFieldContainer() {
        return textFieldPanel;
    }

    public void deleteTextBox(JPanel container) {
        System.out.println("this is accessed, right?");
        int componentsCount = container.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = container.getComponent(componentsCount - 1);
            container.remove(lastComponent); 

            //TODO remove from classlist
            //set.removeClassFromClassList();

            windowFix();
            textboxCounter--;
        }
    }

    public void windowFix() {
        window.revalidate(); 
        window.repaint();
    }

    public void hideContainer() {
        textFieldContainer.setVisible(false);
        textFieldPanel.setVisible(false);
    }

    public void setClassList() {
        System.out.println("2222 "+classList);
        set.setClassList(classList);
    }
    
    public JPanel typeBox(String placeholder, String my_type, Boolean boxLoaded) {
        hideContainer(); //needed unless jlabels will be missing
        loaded = boxLoaded;
        JPanel northTypePanel = new JPanel(new BorderLayout());
        JPanel gradeTypePanel = new JPanel(new BorderLayout()); //second border layout so things not cut out


        if (my_type.equals("JTextField")) {
            JTextField toAddType = createTextBox(placeholder, "JTextField", loaded);
            gradeTypePanel.add(toAddType);
        }

        else if(my_type.equals("JLabel")) {
            System.out.println("hi creating jlabel for "+placeholder+" which is loaded? "+boxLoaded);
            JLabel toAddType = new JLabel(placeholder);
            gradeTypePanel.add(toAddType);
        }
        gradeTypePanel.setPreferredSize(new Dimension( 155,50));
        northTypePanel.add(gradeTypePanel, BorderLayout.NORTH);
        windowFix();

        return northTypePanel;
    }

    public String goIntoPanel(JPanel panel, int index) {
        Container container = panel;
        if (index >= container.getComponentCount()) { //check component is not null
            return "does not exist";
        }
        Component component = container.getComponent(index);
        if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                return text;
            } 
        else if (component instanceof JPanel) {
                JPanel jpanel = (JPanel) component;
                String text = goIntoPanel(jpanel, 0);
                if (text != null) {
                    return text;
                }
            }
            System.out.println("none of these" +component.getClass().getName());
            return "something went wrong goIntoPanel";
        }

        public JTextField goIntoPanelReturnTextbox(JPanel panel, int index) {
            Container container = panel;
            Component component = container.getComponent(index);
            if (component instanceof JTextField) {
                    JTextField textField = (JTextField) component;
                    return textField;
                } 
            else if (component instanceof JPanel) {
                    JPanel jpanel = (JPanel) component;
                    goIntoPanel(jpanel, 0);
                }
                System.out.println("none of these" +component.getClass().getName());
                return textField;
            }
        
            public Boolean checkIfHasPlaceholder(JPanel textFieldPanel2) {
                JTextField textField = new JTextField();
                for (int i = 0; i < textFieldPanel2.getComponentCount()-1; i++) {
                    Component component = textFieldPanel2.getComponent(i);
                    if (component instanceof JTextField) {
                        textField = (JTextField) component;
                    } 
                    else if (component instanceof JPanel) {
                        textField = (JTextField) goIntoPanelReturnTextbox(textFieldPanel2, i);
                    }
                    if (set.getEmptiedState(textField) == false) {
                        return false;
                    }}
                    return true;
            }

    public void deleteMouseListeners(JTextField textField, int numberToDelete) {
        MouseListener[] listeners = textField.getMouseListeners();
        for (int i = 5; i >= 0; i--) {
            textField.removeMouseListener(listeners[i]);
        }
    }
}