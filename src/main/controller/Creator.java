package main.controller;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;

import main.controller.Creator;
import main.model.Set;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Container;


public class Creator {
    private JFrame window;
    private JPanel backNextButtonsPanel;
    private JPanel textFieldPanel= new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JTextField previousTextbox;
    private Set set;
    private FileHandler fileHandler = new FileHandler();
    //private Decorator decorate = new Decorator();
    private int textboxCounter;
    private JTextField textField = new JTextField();
    private String placeholder;
    private ArrayList<String> classList = new ArrayList<>();
    private JButton saveButton;
    private ArrayList<String>textFieldPanelText = new ArrayList<>();
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Boolean focusGranted = true;
    private int attachedBoxes = 0;
    private int maxAttachedBoxes = 0;
    private boolean loaded;
    private String filePath;
    //private BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
    private String text = textField.getText().trim();
    
    public Creator() {
        this.set = Set.getInstance();
        this.window = set.getWindow();
    }

    public JButton backButtonCreate() {
        JButton backButton;
        backButton = new JButton("< Back");
        backButton.setPreferredSize(new Dimension(87, 29));

        return backButton;
        
    }

    public JButton saveButtonCreate() {
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

    public void textFieldFocusListener(JTextField importedTextField, String placeholder) { 
        textField = importedTextField;

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (focusGranted) {
                    System.out.println("focusGranted");
                    if (previousTextbox != null) {
                        previousTextbox.setForeground(Color.LIGHT_GRAY);
                    }if (textField.getText().equals(placeholder) && set.getEmptiedState(textField) == false && set.getLoadedState(textField) == false) {
                        textField.setText("");
                        set.setEmptiedState(textField, true);
                        textField.setForeground(Color.gray);
                    }else if (!textField.getText().equals(placeholder) && !textField.getText().isEmpty() && set.getEmptiedState(textField) == true) {
                        textField.setForeground(Color.gray);
                        set.setEmptiedState(textField, true);
                    }else if (textField.getText().equals(placeholder) && set.getEmptiedState(textField) == true && set.getLoadedState(textField) == false) {
                        textField.setText(""); // Clear the placeholder text when the field gains focus
                        textField.setForeground(Color.gray);
                        set.setEmptiedState(textField, true);
                        set.setSaveable(true);
                    }else {
                        System.out.println("something went wrong in Creator class, focus gained");
                    }
                    previousTextbox = textField;
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText(placeholder);
                    set.setEmptiedState(textField, false);
                }
                
                else if (!textField.getText().isEmpty() && set.getEmptiedState(textField) == true){
                    textField.setForeground(Color.LIGHT_GRAY);
                }
                else {
                    System.out.println("something went wrong in focus lost");
                }
            }
        });
        focusLost(textField, placeholder);
    }

    private void focusLost(JTextField textField, String placeholder) {
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

    public void writeFolderToFile(AtomicBoolean textFieldEmptied) {
        String username = set.getUsername();
        String folderPath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+username; //+ username;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void writeTextToFile(String importedFilePath, JPanel textFieldPanel) {
        filePath = importedFilePath;
        set.setCanContinue(true);
        System.out.println("Step4: begin writeTextToFile."+ set.getCurrentPanelList());
        debugPrintPanel();
        set.getUsername();
        tryToWriteWithoutAppend();
        System.out.println("in write text to file: "+set.getCurrentPanelList());
        set.setClassList(classList);
    }

    private void tryToWriteWithoutAppend() {
        //writer = new BufferedWriter(new FileWriter(filePath));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            if (!classList.isEmpty()) {
               classList.clear();
            }
            for (Component component : textFieldPanel.getComponents()) {
                if (component instanceof JTextField ) {
                    tryToWriteTextFieldWithoutAppend(component, writer);
                }
                else if (component instanceof JPanel) {
                    System.out.println("JPanel");
                    writeTextToFileWithAppend(filePath, (JPanel) component);
                }
                else {
                    System.out.println("component is something else. Something went wrong"+ component.getClass().getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryToWriteTextFieldWithoutAppend(Component component, BufferedWriter writer) {
        JTextField textField = (JTextField) component;
        if (set.getEmptiedState(textField) == true && attachedBoxes == maxAttachedBoxes) {
            text = textField.getText().trim();
            if (!text.isEmpty()) {
                if (attachedBoxes == maxAttachedBoxes) {
                    classList.add(text);
                    try {
                        writer.write(text + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                             
                    System.out.println("should be writing");
                }
                else {
                    attachedBoxes++;
                }
        }
        else if (set.getEmptiedState(textField) == false) {
            seeHowManyPlaceholdersToSkip();
        }}
    }

    private void seeHowManyPlaceholdersToSkip() {
        Decorator decorator = new Decorator();
        if (set.getCanContinue()) {
            JDialog dialog = decorator.genericPopUpMessage("<html><center>Must fill in placeholder.<br>Will not save sections with placeholders");
            dialog.setLocationRelativeTo(window);
            dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
            dialog.setVisible(true);
            return;
        }
        if (text.contains("Grade Type") && attachedBoxes == maxAttachedBoxes) {
            attachedBoxes = 0;
            maxAttachedBoxes = 2;
        }
        else if (text.equals("Percentage of Grade") && attachedBoxes == maxAttachedBoxes) {
            deleteLines(filePath, text);
            attachedBoxes = 0;
            maxAttachedBoxes = 1;
        }
        else if (text.equals("Grades(format:# # #)") && attachedBoxes == maxAttachedBoxes) {
            deleteLines(filePath, text);
            deleteLines(filePath, text);
        }
        else {
            attachedBoxes++;
        }
    }

    private void writeTextToFileWithAppend(String importedFilePath, JPanel textFieldPanel) {
        String filePath = importedFilePath;
        System.out.println("in writeTextToFileWithAppend");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (Component component : textFieldPanel.getComponents()) {
                if (component instanceof JTextField) {
                    decideIfWrite(component, writer, filePath);
                }

                if (component instanceof JPanel) {
                    writeTextToFileWithAppend(filePath, (JPanel) component);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decideIfWrite(Component component, BufferedWriter writer, String filePath) {
        Decorator decorator = new Decorator();
        System.out.println("is a textfield in write text to file with append");
        JTextField textField = (JTextField) component;
        Boolean studentStatNonWritablePlaceholder = set.getEmptiedState(textField) == false && set.getCurrentClass() == "StudentStatCollect.java" && !textField.getText().equals("Credits (Optional)");

        if (set.getEmptiedState(textField) == true) {
            tryToWrite(writer);
        }
        else if (studentStatNonWritablePlaceholder) {
            removeUnwritablePlaceholders();
            
            if (set.getCanContinue()) {
                JDialog dialog = decorator.genericPopUpMessage("<html><center>Must fill in placeholder.<br>Will not save sections with placeholders");
                dialog.setLocationRelativeTo(window);
                dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
                dialog.setVisible(true);
                return;
            }
        }
    }
    public void tryToWrite(BufferedWriter writer) {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            try {
                if (attachedBoxes == maxAttachedBoxes) {
                    System.out.println("writing in decide if write: "+text);
                    classList.add(text);
                    writer.write(text + "\n");
                }
                else {
                    attachedBoxes++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }                             
            System.out.println("should be writing");
        }
    }

    private void removeUnwritablePlaceholders() {
        System.out.println("textfield is a placeholder 1");
        String text = textField.getText();
        if (text.contains("Grade Type") && attachedBoxes == maxAttachedBoxes) {
            //dont write next two
            attachedBoxes = 0;
            maxAttachedBoxes = 2;
        }

        else if (text.equals("Percentage of Grade") && attachedBoxes == maxAttachedBoxes) {
            attachedBoxes = 0;
            maxAttachedBoxes = 1;
            deleteLines(filePath, text);
        }

        else if (text.equals("Grades(format:# # #)") && attachedBoxes == maxAttachedBoxes) {
            deleteLines(filePath, text);
            deleteLines(filePath, text);
        }

        else {
            attachedBoxes++;
        }
    }

    public void clearFile(String filePath) {
        // Clear the file content or reset relevant fields/variables
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(""); // Write empty string to clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLines(String importedFilePath, String text) {
        filePath = importedFilePath;
        //read to array list
        ArrayList<String> lines = fileHandler.readFileToList(filePath);
        if (!lines.isEmpty()) {            
            lines.remove(lines.size() -1);
            classList.remove(classList.size() -1);
    }
        fileHandler.writeArrayListToFile(filePath, lines);
    }

    public JTextField createTextBox(String placeholder, String my_type, Boolean boxLoaded) {
        loaded = boxLoaded;
        int width = 144;
        int height = 50;
        Decorator decorate = new Decorator();
        textboxCounter++;
        if (textboxCounter <= 30 && my_type.equals("JTextField")) {
            textField = decorate.decorateTextBox(placeholder);
            set.setEmptiedState(textField, false);
            addDocumentListener(textField);
            textFieldPanel.add(textField); 
            textFieldPanelText.add(textField.getText());
            textField.setPreferredSize(new Dimension(width, height));
            window.add(textFieldPanel);
            textFieldFocusListener(textField, placeholder);
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
        }
        else if (loaded == false) {
            set.setLoadedState(textField, false);
        }
    }

    private void addDocumentListener(JTextField textField2) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
                saveButtonEnable(); 
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            System.out.println("removeUpdate/// "+ textField.getWidth());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("changedUpdate");
        }
        });
    }

    public JPanel getTextFieldContainer() {
        return textFieldPanel;
    }

    public void resetTextFieldContainer() {

    }

    public void deleteTextBox(JPanel container) {
        int componentsCount = container.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = container.getComponent(componentsCount - 1);
            container.remove(lastComponent); 

            set.removeClassFromClassList();

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

    public void debugPrintPanel() {
        for (Component component : textFieldPanel.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                System.out.println("debugPrintPanel " + textField.getText());
            }
        }
    }

    public void setTextFieldPanel(JPanel myPanel) {
        textFieldPanel = myPanel;
    }

    public void debugPanelComponentCount() {
        System.out.println(textFieldPanel.getComponentCount());
    }

    public void traversePanelAndWrite(String importedFilePath, Container container) {
        filePath = importedFilePath;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            System.out.println("final filepath: "+ filePath);
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
    
                if (!text.isBlank() && !text.equals(placeholder) && set.getEmptiedState(textField) && !classList.contains(text)) {
                    classList.add(text);   
                    writer.write(text + "\n");
                }
            } else if (component instanceof Container) {
                traverseComponents(writer, (Container) component);
            }
        }
    }

    public void setClassList() {
        set.setClassList(classList);

    }

    public void saveButtonEnable() {
        saveButton.setEnabled(true);
    }
    
    public JPanel typeBox(String placeholder, String my_type, Boolean loaded) {
        hideContainer();
        JPanel northTypePanel = new JPanel(new BorderLayout());
        JPanel gradeTypePanel = new JPanel(new BorderLayout()); //second border layout so things not cut out


        if (my_type.equals("JTextField")) {
            JTextField toAddType = createTextBox(placeholder, "JTextField", loaded);
            gradeTypePanel.add(toAddType);
        }

        else if(my_type.equals("JLabel")) {
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
}