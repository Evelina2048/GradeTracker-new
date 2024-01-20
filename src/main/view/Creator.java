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
    private JTextField textField;
    //private JPanel textFieldContainer;
    private ArrayList<String> classList = new ArrayList<>();

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
                if (previousTextbox != null) {
                    previousTextbox.setForeground(Color.LIGHT_GRAY);

                }
                if (textField.getText().equals(placeholder) && getEmptiedState(textField) == false) {
                    textField.setText("");
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
                    setEmptiedState(textField, true);
                }

                else {
                    System.out.println("something went wrong in Creator class, focus gained");

                }
                previousTextbox = textField;
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                    setEmptiedState(textField, false);
                }
                
                else if (!textField.getText().isEmpty() && getEmptiedState(textField) == true){
                    textField.setForeground(Color.gray);

                }
                else {
                    System.out.println("something went wrong in focus lost");
                }
            }
        });

        focusLost(window, textField, placeholder);
    }

    private void focusLost(JFrame window, JTextField textField, String placeholder) {
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
                    System.out.println("3");
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    setEmptiedState(textField, false);
                    window.requestFocusInWindow();
                }

                else if (pointNotInTextbox &&  getEmptiedState(textField) == true && !textField.getText().isEmpty()) {
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

    public void writeTextToFile(AtomicBoolean textFieldEmptied, String placeholder) {//, JButton saveButton) {
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
                            if (text != placeholder && getEmptiedState(textField) == true && textList.contains(text) == false) {
                                //textList.add(text+"\n");
                                writer.write(text+"\n");
                                classList.add(text);
                            }
                        }
                    }
                }
            }
            set.setClassList(classList);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setTextColor() {

    }

    

    private void textBoxIncrement() {
        textboxCounter++;
    }

    public JTextField createTextBox(JFrame window, String placeholder, int width, int height) {
        textboxCounter++; //textBoxIncrement();
        if (textboxCounter <= 30) {
            JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            textField = decorate.decorateTextBox(placeholder);//Enter Class Name
            setEmptiedState(textField, false);
             
            
            textFieldPanel.add(textField); 
            
            textField.setPreferredSize(new Dimension(width, height)); //50,50
            textFieldContainer.add(textFieldPanel); //
            textFieldContainer.setVisible(true);
            window.add(textFieldContainer); //problem
            textFieldFocusListener(window, textField, placeholder);
            windowFix(window);
        }
        return textField;


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
            windowFix(window);
            textboxCounter--;
        }
    }

    public void windowFix(JFrame window) {
        window.revalidate(); 
        window.repaint();
    }
}