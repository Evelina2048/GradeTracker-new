package main.controller;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.Component;

import java.awt.FlowLayout;

import main.model.Set;
import main.model.SetUserInformation;

import java.io.FileWriter;

public class FileWriting {
    private JFrame window;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Set set;
    private SetUserInformation setUserInformation;
    private FileHandling fileHandler = new FileHandling();
    private JTextField textField = new JTextField();
    private ArrayList<String> classList = new ArrayList<>();
    private int attachedBoxes = 0;
    private int maxAttachedBoxes = 0;
    private String filePath;
    private String text = textField.getText().trim();

    private int placeholderCount = 0;
    private int allCount = 0;
    
    public FileWriting() {
        this.set = Set.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.window = set.getWindow();
    }

public void writeFolderToFile() {
    String username = setUserInformation.getUsername();
    //String folderPath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+username+"/ClassInformation/"; //+ username;
    String folderPath = setUserInformation.getPathToClassInformationFolder();
    File folder = new File(folderPath);
    if (!folder.exists()) {
        folder.mkdirs();
    }
}

public void decideIfWrite(Component component, BufferedWriter writer) {
    System.out.println("6666"+"decide if write");
    Decorator decorate = new Decorator();
    textField = (JTextField) component;
    Boolean studentStatNonWritablePlaceholder = set.getEmptiedState(textField) == false && set.getCurrentClass() == "StudentStatCollect.java" && !textField.getText().equals("Credits (Optional)");

    if (set.getEmptiedState(textField) == true) {
        tryToWrite(writer);
    }
    else if (studentStatNonWritablePlaceholder) {
        System.out.println("7777"+"decide if write");
        removeUnwritablePlaceholders();
        
        if (set.getCanContinue()) {
            placeholderCount++;
            allCount++;
            System.out.println("8888 placeholder count"+placeholderCount);
            decorate.areYouSureMessage(null, "studentStatsEmpty", "Remove placeholders to continue"); //hello friends
            //return;
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
                writer.write(text + "\n");
                System.out.println("classlisr "+classList);
            }
            else {
                attachedBoxes++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }                             
    }
}

public void writeTextToFile(){
    set.setCanContinue(true);
    debugPrintPanel();
    setUserInformation.getUsername();
    System.out.println(1111);
    tryToWriteWithoutAppend();

}

private void tryToWriteWithoutAppend() {
    System.out.println(2222);
    filePath = set.getFilePath();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        if (!classList.isEmpty()) {
           classList.clear();
        }

        for (Component component : set.getTextFieldPanel().getComponents()) {
            if (component instanceof JTextField ) {
                tryToWriteTextFieldWithoutAppend(component, writer);
                System.out.println(3333.1);
                set.setCanContinue(true);
            }
            else if (component instanceof JPanel) {
                writeTextToFileWithAppend((JPanel) component);
                System.out.println(3333.2);
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
    JTextField textField = (JTextField) component;
    if (set.getEmptiedState(textField) == true && attachedBoxes == maxAttachedBoxes) {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            if (attachedBoxes == maxAttachedBoxes) {
                classList.add(text);
                System.out.println("in trytowritetextfieldwithoutappend classlist "+classList);
                try {
                    writer.write(text + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }                             
            }
            else {
                attachedBoxes++;
            }
    }
    else if (set.getEmptiedState(textField) == false) {
        seeHowManyPlaceholdersToSkip();
        System.out.println("placeholder count: "+placeholderCount);
        
    }}
}

private void seeHowManyPlaceholdersToSkip() {
    Decorator decorate = new Decorator();
    if (set.getCanContinue()) {
        JDialog dialog = decorate.genericPopUpMessage("<html><center>Must fill in placeholder.<br>Will not save sections with placeholders", null, 200, 90);
        dialog.setLocationRelativeTo(window);
        dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
        dialog.setVisible(true);
        //set.setCanContinue(false);
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

public void writeTextToFileWithAppend(JPanel panel) { //i think student classes
    System.out.println("4444"+"write text to file with append");
    filePath = set.getFilePath();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JTextField) {
                System.out.println("5555"+"write text to file with append");
                decideIfWrite(component, writer);
                
            }

            if (component instanceof JPanel) {
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
    placeholderCount++;
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

public  ArrayList<String> getClassList() {
    return classList;
}


}