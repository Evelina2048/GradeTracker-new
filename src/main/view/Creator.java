package main.view;

import javax.swing.BorderFactory;
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
import main.view.student.StudentClasses;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Container;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Creator {
    private JPanel backNextButtonsPanel;
    private JPanel textFieldPanel= new JPanel(new FlowLayout(FlowLayout.LEFT));
    // private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private JTextField previousTextbox;
    private Set set;
    private Decorator decorate = new Decorator();
    private int textboxCounter;
    //private Set set = new Set();
    private JTextField textField;
    private String placeholder;
    //private JPanel textFieldContainer;
    private ArrayList<String> classList = new ArrayList<>();
    private JButton saveButton;
    private boolean userClickedEmpty;
    private boolean placeholderFill;
    private ArrayList<String>textFieldPanelText = new ArrayList<>();

    //private JPanel textFieldContainer = new JPanel(new GridLayout(0, 5)); // Panel to hold text fields
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
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
        //saveButton;

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
        backNextButtonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        return backNextButtonsPanel;
    }

    
    public void textFieldFocusListener(JFrame window, JTextField textField, String placeholder) { 

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (previousTextbox != null) {
                    previousTextbox.setForeground(Color.LIGHT_GRAY);

                }
                if (textField.getText().equals(placeholder) && set.getEmptiedState(textField) == false) {
                    textField.setText("");
                    userClickedEmpty = true;
                    
                    //document.putProperty("name", "clicked");
                    set.setEmptiedState(textField, true);
                    textField.setForeground(Color.BLACK);
                }

                else if (!textField.getText().equals(placeholder) && !textField.getText().isEmpty() && set.getEmptiedState(textField) == true) {
                    textField.setForeground(Color.BLACK);
                    set.setEmptiedState(textField, true);
                    //setSaveEnabled();
                }

                else if (textField.getText().equals(placeholder) && set.getEmptiedState(textField) == true) {
                    textField.setText(""); // Clear the placeholder text when the field gains focus
                    userClickedEmpty = true;
                    textField.setForeground(Color.BLACK);
                    set.setEmptiedState(textField, true);
                    set.setSaveable(true);
                    //setSaveEnabled();
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
                    //userClickedEmpty = true;
                    set.setEmptiedState(textField, false);
                }
                
                else if (!textField.getText().isEmpty() && set.getEmptiedState(textField) == true){
                    textField.setForeground(Color.gray);
                    //setSaveEnabled();

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

    public void writeFolderToFile(AtomicBoolean textFieldEmptied) {//, JButton saveButton) {
        //StudentClasses classes = new StudentClasses(null, null, null, null, set)
        String username = set.getUsername();
        System.out.println("username " + username);
        String folderPath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+username; //+ username;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void writeTextToFile(String filePath) {//, JButton saveButton) {
        System.out.println("writetexttofile1 made it into write text to file");
        debugPrintPanel();
        String username = set.getUsername();
        System.out.println("username " + username);
        System.out.println("Class List: " + classList);
        // set.setClassList(classList);
        traversePanelAndWrite(filePath, getTextFieldContainer());
        // try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        //     System.out.println("writetexttofile2 in the try statement"+ textFieldPanel.getComponentCount());
        //     debugPrintPanel();
        //     for (Component component : textFieldPanel.getComponents()) {
        //         System.out.println("p");
        //         if (component instanceof JTextField) {
        //             JTextField textField = (JTextField) component;
        //             String text = textField.getText();
        //             System.out.println("q");

        //             if (!text.isBlank() && !text.equals(placeholder) && set.getEmptiedState(textField) && !classList.contains(text)) {
        //                 System.out.println("r");
        //                 writer.write(text + "\n");
        //                 classList.add(text);
        //             }
        //         }
        //     }
        //     set.setClassList(classList);
        //     System.out.println("writetexttopanel3 classlist,");
        //     debugPrintPanel();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        //set.resetContainerAndPanel();
    }

    public JTextField createTextBox(JFrame window, String placeholder, int width, int height) { //something here is causing the issue
        debugPanelComponentCount();
        textboxCounter++;
        if (textboxCounter <= 30) {
            textField = decorate.decorateTextBox(placeholder);
            set.setEmptiedState(textField, false);
            placeholderFill = true;
            addDocumentListener(textField);
            debugPanelComponentCount();
            System.out.println("^before adding "+ textField.getText());
            textFieldPanel.add(textField); 
            debugPanelComponentCount();
            System.out.println("^after adding");
            textFieldPanelText.add(textField.getText());
            textField.setPreferredSize(new Dimension(width, height));
            window.add(textFieldPanel);
            textFieldFocusListener(window, textField, placeholder);
            windowFix(window);
        }
        return textField;
    }

    private void addDocumentListener(JTextField textField2) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            System.out.println("placeholderfill"+placeholderFill);
                saveButton.setEnabled(true);
                userClickedEmpty = false; 
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            System.out.println("removeUpdate");
            if (userClickedEmpty = false) {
                saveButton.setEnabled(true);
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            //saveButton.setEnabled(true);
        }
        });
    }

    public JPanel getTextFieldContainer() {
        return textFieldContainer;
    }

    public void resetTextFieldContainer() {

    }

    public void deleteTextBox(JFrame window, JPanel container) {
        int componentsCount = container.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = container.getComponent(componentsCount - 1);
            container.remove(lastComponent);
            windowFix(window);
            textboxCounter--;
        }
    }
    public void windowFix(JFrame window) {
        window.revalidate(); 
        window.repaint();
    }

    public void hideContainer() {
        textFieldContainer.setVisible(false);
        textFieldPanel.setVisible(false);
    }

    public void updateTextBoxContainerPanel() {
        //set.setTextFieldPanel(textFieldPanel);
        //set.setTextFieldContainer(textFieldContainer);
    }

    public void debugPrintPanel() {
        //System.out.println("textfieldpaneltext"+textFieldPanelText);
        for (Component component : textFieldPanel.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                System.out.println("debugPrintPanel " + textField.getText());
            }
        }
    }

    public void setTextFieldPanel(JPanel myPanel) {
        System.out.println("in set text field panel ");
        System.out.println("components: "+textFieldPanel.getComponentCount());
        textFieldPanel = myPanel;
    }

    public void debugPanelComponentCount() {
        System.out.println(textFieldPanel.getComponentCount());
    }

    ////
    public void traversePanelAndWrite(String filePath, Container container) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            traverseComponents(writer, container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void traverseComponents(BufferedWriter writer, Container container) throws IOException {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                System.out.println("q");
    
                if (!text.isBlank() && !text.equals(placeholder) && set.getEmptiedState(textField) && !classList.contains(text)) {
                    System.out.println("r");
                    classList.add(text);
                    System.out.println("clas list:" + classList);
                    writer.write(text + "\n");
                    //classList.add(text);
                }
            } else if (component instanceof Container) {
                traverseComponents(writer, (Container) component);
            }
        }
    }

    public void setClassList() {
        System.out.println("setting class list");
        set.setClassList(classList);
    }
    
}