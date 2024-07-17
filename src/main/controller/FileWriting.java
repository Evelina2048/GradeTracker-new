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

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;

import main.controller.Creator;
import main.model.Set;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import main.model.Set;

public class FileWriting {
    private JFrame window;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Set set;
    private FileHandler fileHandler = new FileHandler();
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
    
    public FileWriting() {
        this.set = Set.getInstance();
        this.window = set.getWindow();
    }

public void writeFolderToFile() {
    String username = set.getUsername();
    String folderPath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+username; //+ username;
    File folder = new File(folderPath);
    if (!folder.exists()) {
        folder.mkdirs();
    }
}

public void decideIfWrite(Component component, BufferedWriter writer) {
    //filePath =
    Decorator decorator = new Decorator();
    textField = (JTextField) component;
    Boolean studentStatNonWritablePlaceholder = set.getEmptiedState(textField) == false && set.getCurrentClass() == "StudentStatCollect.java" && !textField.getText().equals("Credits (Optional)");

    System.out.println("10 10 10 10 in decide if write "+textField.getText());
    if (set.getEmptiedState(textField) == true) {
        System.out.println("11 11 11 11.1 in decide if write");
        tryToWrite(writer);
    }
    else if (studentStatNonWritablePlaceholder) {
        System.out.println("11 11 11 11.2 in decide if write");
        removeUnwritablePlaceholders();
        
        if (set.getCanContinue()) {
            //JDialog dialog = decorator.genericPopUpMessage("<html><center>Must fill in placeholder.<br>Will not save sections with placeholders",null);
            
            //JDialog dialog = 
            decorator.areYouSureMessage(null, "studentStatsEmpty", "Hello friends");
            // dialog.setLocationRelativeTo(window);
            // dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
            // dialog.setVisible(true);
            return;
        }
    }
}

public void makeUsernameFile() {

}

private void tryToWrite(BufferedWriter writer) {
    text = textField.getText().trim();
    if (!text.isEmpty()) {
        try {
            if (attachedBoxes == maxAttachedBoxes) {
                //System.out.println("writing in decide if write: "+text);
                classList.add(text);
                writer.write(text + "\n");
            }
            else {
                attachedBoxes++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }                             
        //System.out.println("should be writing");
    }
}

public void writeTextToFile(){//JPanel textFieldPanel) {//(String importedFilePath, JPanel textFieldPanel) {;
    //filePath = importedFilePath;

    System.out.println("6666 in write text to file");
    set.setCanContinue(true);
    //System.out.println("Step4: begin writeTextToFile."+ set.getCurrentPanelList());
    debugPrintPanel();
    set.getUsername();
    tryToWriteWithoutAppend();
    //System.out.println("in write text to file: "+set.getCurrentPanelList());
    set.setClassList(classList);
}

private void tryToWriteWithoutAppend() {
    //writer = new BufferedWriter(new FileWriter(filePath));
    filePath = set.getFilePath();
    System.out.println("7777 in tryToWriteWithoutAppend");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        System.out.println("8888 in trytowritewithoutappend");
        if (!classList.isEmpty()) {
           classList.clear();
        }
        for (Component component : textFieldPanel.getComponents()) {
            System.out.println("9999 in trytowritewithoutappend");
            if (component instanceof JTextField ) {
                System.out.println("10 10 10 10.1 in trytowritewithoutappend");
                tryToWriteTextFieldWithoutAppend(component, writer);
            }
            else if (component instanceof JPanel) {
                //System.out.println("JPanel");
                //filePath =
                System.out.println("10 10 10 10.2 in trytowritewithoutappend");
                writeTextToFileWithAppend((JPanel) component);
            }
            else {
                System.out.println("component is something else. Something went wrong"+ component.getClass().getName());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void tryToWriteTextFieldWithoutAppend(Component component, BufferedWriter writer) { //i think for student stat
    System.out.println("11 11 11 11 in trytowritewithoutappend");
    JTextField textField = (JTextField) component;
    if (set.getEmptiedState(textField) == true && attachedBoxes == maxAttachedBoxes) {
        System.out.println("12 12 12 12 in trytowritewithoutappend");
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            if (attachedBoxes == maxAttachedBoxes) {
                classList.add(text);
                try {
                    writer.write(text + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }                             
                //System.out.println("should be writing");
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
        JDialog dialog = decorator.genericPopUpMessage("<html><center>Must fill in placeholder.<br>Will not save sections with placeholders", null);
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

public void writeTextToFileWithAppend(JPanel panel) {
    //String filePath = importedFilePath;
    filePath = set.getFilePath();
    System.out.println("6666 in writeTextToFileWithAppend");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        System.out.println("7777 in writeTextToFileWithAppend");
        for (Component component : panel.getComponents()) {
            System.out.println("8888 in writeTextToFileWithAppend");
            if (component instanceof JTextField) {
                System.out.println("9999.1 in writeTextToFileWithAppend");
                decideIfWrite(component, writer);
            }

            if (component instanceof JPanel) {
                System.out.println("9999.2 in writeTextToFileWithAppend");
                writeTextToFileWithAppend((JPanel) component);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void removeUnwritablePlaceholders() {
    text = textField.getText();
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

public void debugPrintPanel() {
    for (Component component : textFieldPanel.getComponents()) {
        if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            System.out.println("debugPrintPanel " + textField.getText());
        }
    }
}


}