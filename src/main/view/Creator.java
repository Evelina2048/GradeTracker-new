package main.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import main.view.Creator;

public class Creator {
    private JPanel backNextButtonsPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private JTextField previousTextbox;
    private Set set;
    private Decorator decorate = new Decorator();
    private int textboxCounter;
    //private Set set = new Set();
    //private JPanel textFieldContainer;
    private JPanel textFieldContainer = new JPanel(new GridLayout(0, 5)); // Panel to hold text fields
    
    public Creator(Set set) {
        this.set = set;
    }

    public JButton backButtonCreate() {
        JButton backButton;
        //buttons
        backButton = new JButton("< Back");
        backButton.setPreferredSize(new Dimension(87, 29));

        return backButton;
        
    }

    public JButton saveButtonCreate() {
        JButton saveButton;

        saveButton = new JButton("Save");

        return saveButton;
    }

    public JButton nextButtonCreate() {
        JButton nextButton;

        nextButton = new JButton("Next >");
        nextButton.setPreferredSize(new Dimension(87, 29));

        return nextButton;
    }

    public JPanel makeBackNextButtonsPanel(JPanel backButton, JPanel saveButtonPanel, JPanel nextButton) {
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);
        backNextButtonsPanel.add(saveButtonPanel, BorderLayout.CENTER);
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);

        return backNextButtonsPanel;
    }

    
    public void textFieldFocusListener(JFrame window, JTextField textField, String placeholder) { 

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("focusgained");
                System.out.println("textfieldemptied"+getEmptiedState(textField));
                if (previousTextbox != null) {
                    previousTextbox.setForeground(Color.LIGHT_GRAY);

                }
                if (textField.getText().equals(placeholder) && getEmptiedState(textField) == false) {
                    System.out.println("Inside condition block 8");
                    System.out.println("Before setting emptied state: textFieldEmptied..." + getEmptiedState(textField));
                    textField.setText("");
                    setEmptiedState(textField, true);
                    System.out.println("After setting emptied state: textFieldEmptied..." + getEmptiedState(textField));
                    textField.setForeground(Color.BLACK);
                    System.out.println("After setting color: textFieldEmptied..." + getEmptiedState(textField));
                }
                //System.out.println("After condition check: textFieldEmptied..." + getEmptiedState(textField));

                else if (!textField.getText().equals(placeholder) && !textField.getText().isEmpty() && getEmptiedState(textField) == true) {
                    System.out.println("7");
                    textField.setForeground(Color.BLACK);
                    setEmptiedState(textField, true);
                }

                else if (textField.getText().equals(placeholder) && getEmptiedState(textField) == true) {
                    System.out.println("6");
                    textField.setText(""); // Clear the placeholder text when the field gains focus
                    textField.setForeground(Color.BLACK);
                    setEmptiedState(textField, true);
                }

                else {
                    System.out.println("textfieldemptied"+getEmptiedState(textField));
                    System.out.println("something went wrong in Creator class, focus gained");

                }
                previousTextbox = textField;
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("focuslost");
                //JTextField previousTextbox = textField;
                if (textField.getText().isEmpty()) {
                    System.out.println("5textfieldemptied..."+getEmptiedState(textField));
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                    setEmptiedState(textField, false);
                }
                
                else if (!textField.getText().isEmpty() && getEmptiedState(textField) == true){
                    System.out.println("4");
                    textField.setForeground(Color.gray);

                }
                else {
                    System.out.println("something went wrong in focus lost");
                    System.out.println("textfieldemptied?"+getEmptiedState(textField));
                }
            }
        });

        focusLost(window, textField, placeholder);
    }

    private void focusLost(JFrame window, JTextField textField, String placeholder) {
        window.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouse clicked");
                //JTextField previousTextbox = textField;
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
                    System.out.println("3");
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    setEmptiedState(textField, false);
                    window.requestFocusInWindow();
                }

                else if (pointNotInTextbox &&  getEmptiedState(textField) == true && !textField.getText().isEmpty()) {
                    System.out.println("2nd option");
                    System.out.println("2");
                    //textField.setForeground(Color.BLACK);
                    window.requestFocusInWindow();
                }

                else if (pointNotInTextbox) {
                    //textField.setForeground(Color.black);
                    window.requestFocusInWindow();
                    System.out.println("1textfieldemptied..."+getEmptiedState(textField));

                }

                else {
                    System.out.println("something went wrong");
                }

            }
        });

    }

    public void writeFolderToFile(AtomicBoolean textFieldEmptied) {//, JButton saveButton) {
        //StudentClasses classes = new StudentClasses(null, null, null, null, set)
        String username = set.getUsername();
        System.out.println("usernameeeeeeeeeeee" + username);
        String folderPath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+username; //+ username;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void writeTextToFile(AtomicBoolean textFieldEmptied) {//, JButton saveButton) {
        //StudentClasses classes = new StudentClasses(null, null, null, null, set)
        String username = set.getUsername();
        System.out.println("usernameeeeeeeeeeee" + username);
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + username + "/class.txt";
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            List<String> textList = new ArrayList<>();
    
            for (Component component : textFieldContainer.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel textFieldPanel = (JPanel) component;
                    for (Component innerComponent : textFieldPanel.getComponents()) {
                        if (innerComponent instanceof JTextField) {
                            JTextField textField = (JTextField) innerComponent;
                            String text = textField.getText();
                            System.out.println("test"+ (text != "Enter Class Name")+" textfieldEmptied" + getEmptiedState(textField));
                            if (text != "Enter Class Name" && getEmptiedState(textField) == true && textList.contains(text) == false) {
                                //textList.add(text+"\n");
                                writer.write(text+"\n");
                            }
                        }
                    }
                }
            }
    
            // Write the list as an array to the file
            //writer.write(textList.toString());
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setTextColor() {

    }

    

    private void textBoxIncrement() {
        textboxCounter++;
    }

    public void createTextBox(JFrame window) {
        textboxCounter++; //textBoxIncrement();
        if (textboxCounter <= 30) {
            System.out.println("In create textbox new");
            JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JTextField textField = decorate.decorateTextBox("Enter Class Name");
            setEmptiedState(textField, false);
             
            
            textFieldPanel.add(textField); 
            
            textField.setPreferredSize(new Dimension(50, 50));
            textFieldContainer.add(textFieldPanel); //
            textFieldContainer.setVisible(true);
            window.add(textFieldContainer, BorderLayout.NORTH);

            textFieldFocusListener(window, textField, "Enter Class Name");
            
            
            windowFix(window);

        }


    }

    public JPanel getTextFieldContainer() {
        return textFieldContainer;
    }
 
    private boolean getEmptiedState(JTextField textField) {
        return textFieldEmptiedMap.getOrDefault(textField, false);
    }

    private void setEmptiedState(JTextField textField, boolean state) {
        textFieldEmptiedMap.put(textField, state);
    }

    public void deleteTextBox(JFrame window) {
        int componentsCount = textFieldContainer.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = textFieldContainer.getComponent(componentsCount - 1);
            textFieldContainer.remove(lastComponent);
            //// window.revalidate(); 
            //// window.repaint();
            windowFix(window);
            textboxCounter--;
        }
    }

    public void windowFix(JFrame window) {
        window.revalidate(); 
        window.repaint();
    }
}