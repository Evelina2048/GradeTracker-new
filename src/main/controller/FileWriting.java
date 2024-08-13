package controller;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.Component;

import java.awt.FlowLayout;

import model.GoIntoPanel;
import model.Set;
import model.SetState;
import model.SetUserInformation;

import java.io.FileWriter;

public class FileWriting {
    private JFrame window;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    private Set set;
    private SetState setState;
    private SetUserInformation setUserInformation;
    private CompositeActionListenerWithPriorities actionPriorities;

    private FileHandling fileHandler = new FileHandling();
    private JTextField textField = new JTextField();
    private ArrayList<String> classList = new ArrayList<>();
    private int attachedBoxes = 0;
    private int maxAttachedBoxes = 0;
    private String filePath;
    private String text = textField.getText().trim();

    private int placeholderCount = 0;
    public FileWriting() {
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        this.window = set.getWindow();
    }

public void writeFolderToFile() {
    setUserInformation.getUsername();
    String folderPath = setUserInformation.getPathToClassInformationFolder();
    File folder = new File(folderPath);
    if (!folder.exists()) {
        folder.mkdirs();
    }
}

private void decideIfWrite(Component component, BufferedWriter writer) {
    textField = (JTextField) component;
    Boolean studentStatNonWritablePlaceholder = setState.getEmptiedState(textField) == false && setState.getCurrentClass() == "StudentStatCollect.java" && !textField.getText().equals("Credits (Optional)");

    if (setState.getEmptiedState(textField) == true) {
        tryToWrite(writer);
    }
    else if (studentStatNonWritablePlaceholder) {
        removeUnwritablePlaceholders();
    }
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

public void writeTextToFile(){ //Student Classes and Stat
    setState.setCanContinue(true);
    debugPrintPanel();
    setUserInformation.getUsername();
    debugPrintPanel();
    tryToWriteWithoutAppend();

}

private void tryToWriteWithoutAppend() {
    filePath = set.getFilePath();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        if (!classList.isEmpty()) {
           classList.clear();
        }

        for (Component component : setState.getTextFieldPanel().getComponents()) {
            if (component instanceof JTextField ) {
                tryToWriteTextFieldWithoutAppend(component, writer); //student stat
                setState.setCanContinue(true);
            }
            else if (component instanceof JPanel) {
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

private void tryToWriteTextFieldWithoutAppend(Component component, BufferedWriter writer) { //student stat
    JTextField textField = (JTextField) component;
    if (setState.getEmptiedState(textField) == true && attachedBoxes == maxAttachedBoxes) {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            if (attachedBoxes == maxAttachedBoxes) {
                //TODO classlist
                if (actionPriorities.getCurrentClass() == "StudentClasses" && !classList.contains(text)) {
                    classList.add(text);
                }

                else {
                classList.add(text);
                }
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
    
    else if (setState.getEmptiedState(textField) == false) {
        seeHowManyPlaceholdersToSkip();
        System.out.println("placeholder count: "+placeholderCount);
        
    }}
}

private void seeHowManyPlaceholdersToSkip() {
    Decorator decorate = new Decorator();
    if (setState.getCanContinue()) {
        System.out.println("if youre seeing this, please take note because i have no idea when this placeholder launches");
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

private void writeTextToFileWithAppend(JPanel panel) { //student classes
    filePath = set.getFilePath();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JTextField) {
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

public int howManyPlaceholders() {
    System.out.println("skelator 1111 ");
    GoIntoPanel goIntoPanel = new GoIntoPanel();
    int placeholderAmount = 00000;
    JPanel tempTextFieldPanel = setState.getTextFieldPanel();
    // if (tempTextFieldPanel.getComponentCount() >=5) {
        JLabel l = new JLabel();
        JPanel p = new JPanel();
        System.out.println("jlabel. jpanel "+l.getClass().getName()+ " "+ p.getClass().getName()+ "<><><> "+goIntoPanel.goIntoPanelReturnType(tempTextFieldPanel));
    if (goIntoPanel.goIntoPanelReturnType(tempTextFieldPanel) == "javax.swing.JLabel") {
        placeholderAmount = iteratePanel(1);
    }

    else if ((goIntoPanel.goIntoPanelReturnType(tempTextFieldPanel) == "javax.swing.JPanel")||(goIntoPanel.goIntoPanelReturnType(tempTextFieldPanel) == "javax.swing.JTextField")) {
        placeholderAmount = iteratePanel(0);
    }

    else {
        System.out.println("issue in howManyPlaceholders");
    }


    // }
    
    // else if ((tempTextFieldPanel.getComponentCount() >=1) && (tempTextFieldPanel.getComponentCount() < 5)) {
    //     while (tempTextFieldPanel.getComponentCount() < 5) {
    //         if (textFieldPanel.getComponentCount() >= 1) {
    //             textFieldPanel = (JPanel) textFieldPanel.getComponent(0);
    //         }

    //         else  {
    //             System.out.println("textfieldpanel.getComponent(0) does not exist");
    //             break;
    //         }
    //     }
    //     if (tempTextFieldPanel.getComponentCount() >= 5) {
    //         placeholderAmount = iteratePanel();
    //     }
    //}

    // else {
    //     System.out.println("temptextfieldpanel is empty");
    // }
    return placeholderAmount;
}

private int iteratePanel(int labelOrNotIndex) {
    //System.out.println("skelator 2222 "+ placeholderAmount+ "fieldpanelcount "+setState.getTextFieldPanel().getComponentCount());
    GoIntoPanel goIntoPanel = new GoIntoPanel();
    int placeholderAmount = 0;
    JTextField returnedBox = new JTextField("howmanyplaceholdersnotchanged");

    for (int i = 0; i < setState.getTextFieldPanel().getComponentCount(); i++) {
        //for (int i = 1; i < setState.getTextBoxPanel().getComponentCount(); i++) {
            //getTextBoxPanel()
            System.out.println("skelator 3333 "+ placeholderAmount);
            System.out.println("instance of "+setState.getTextFieldPanel().getComponent(i).getClass().getName());
            Component component = setState.getTextFieldPanel().getComponent(i);
            if (component instanceof JTextField && setState.getEmptiedState(textField) == false) {
                System.out.println("skelator 3333.1 "+ placeholderAmount);
                placeholderAmount++;
            }
            else if (component instanceof JPanel) {
                System.out.println("skelator 3333.2 "+ placeholderAmount);
                returnedBox = goIntoPanel.goIntoPanelReturnTextbox(setState.getTextFieldPanel(), i);
                //System.out.println("returnedboxshouldbeseventimes " + returnedBox.getText());
                if (setState.getEmptiedState(returnedBox) == false) {
                    System.out.println("in jpanel placeholder look "+ returnedBox.getText());
                    placeholderAmount++;
                }

                else {
                    System.out.println("not a placeholder "+returnedBox.getText());
                } 
            }
            else {
                System.out.println("skelator 3333.3 "+ placeholderAmount);
                System.out.println("component is something else. Something went wrong"+ component.getClass().getName());
            }
    
            //placeholderAmount--; //for jlabel
            System.out.println("skelator 4444 "+placeholderAmount);
    }
    return placeholderAmount;
}

// public void getClassList() {
//     return classList;
// }
}