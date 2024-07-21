package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusListener;

public class SetUserInformation {
    private String username;
    private ArrayList<String> classList;
    private ArrayList<String> typeList;
    private Boolean saveable;
    private JPanel textFieldPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private HashMap<JTextField, Boolean> textFieldLoadedMap = new HashMap<>();
    private int classListIndex = 0;
    private ArrayList<String> finalClassList;
    private String existingOrNew;
    private String studentOrTeacher;
    private JFrame window;
    private String currentClass;
    private Boolean canContinue = true;
    private JButton saveButton;
    //private Boolean existingOrNewHasChanged = false;
    private Boolean studentOrTeacherHasChanged = false;
    private Boolean newOrExistingHasChanged = false;
    private String filePath;

    private Boolean dialogBeingDisplayed = false;

    private FocusAdapter textfieldFocusListener;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SetUserInformation instance;

    private JLabel DEBUGMARKEDBOX;

    private FocusListener yesFocusListener;
    private FocusListener noFocusListener;

    // Private constructor to prevent instantiation
    private SetUserInformation() {}

    // Public method to provide access to the single instance
    public static SetUserInformation getInstance() {
        if (instance == null) {
            instance = new SetUserInformation();
        }
        return instance;
    }
    //

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public void setExistingOrNew(String myExistingOrNew) {
        existingOrNew = myExistingOrNew;
    }

    public String getExistingOrNew() {
        return existingOrNew;
    }

    public void setStudentOrTeacher(String myStudentOrTeacher) {
        studentOrTeacher = myStudentOrTeacher;
    }

    public String getStudentOrTeacher() {
        return studentOrTeacher;
    }

    // public void setWindow(JFrame myWindow) {
    //     window = myWindow;
    // }

    // public JFrame getWindow() {
    //     return window;
    // }

    // public void setCurrentClass(String myClass) {
    //     currentClass = myClass;
    // }

    // public String getCurrentClass() {
    //     return currentClass;
    // }

    // public void setCanContinue(boolean myCanContinue) {
    //     canContinue = myCanContinue;
    // }

    // public Boolean getCanContinue() {
    //     return canContinue;
    // }

    // public void setNewOrExistingChanged(boolean changed) {
    //     System.out.println("setexistingornewchanged: "+ changed);
    //     newOrExistingHasChanged = changed;
    // }

    // public Boolean getNewOrExistingChanged() {
    //     //System.out.println("recievingexistingornewchanged: "+ studentOrTeacherHasChanged);
    //     return newOrExistingHasChanged;
    // }

    // public void setFilePath(String thisFilePath) {
    //     filePath = thisFilePath;
    // }

    // public String getFilePath() {
    //     return filePath;
    // }

    // public void setDEBUGBOX(JLabel MYDEBUGMARKEDBOX) {
    //     DEBUGMARKEDBOX = MYDEBUGMARKEDBOX;
    // }

    // public JLabel getDEBUGBOX() {
    //     return DEBUGMARKEDBOX;
    // }

    // public void setCurrentSaveButton(JButton thisSaveButton) {
    //     saveButton = thisSaveButton;
    // }

    // public JButton getCurrentSaveButton() {
    //     return saveButton;
    // }
}