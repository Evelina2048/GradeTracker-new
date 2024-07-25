package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;

public class Set {
    private ArrayList<String> classList;
    private ArrayList<String> typeList;
    private Boolean saveable;
    private JPanel textFieldPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private HashMap<JTextField, Boolean> textFieldLoadedMap = new HashMap<>();
    private int classListIndex = 0;
    private ArrayList<String> finalClassList;
    private JFrame window;
    private String currentClass;
    private Boolean canContinue = true;
    private JButton saveButton;
    private Boolean newOrExistingHasChanged = false;
    private String filePath;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static Set instance;

    private JLabel DEBUGMARKEDBOX;

    // Private constructor to prevent instantiation
    private Set() {}

    // Public method to provide access to the single instance
    public static Set getInstance() {
        if (instance == null) {
            instance = new Set();
        }
        return instance;
    }
//
    // public void setClassList(ArrayList<String> newClassList) {
    //     classList = newClassList;
    // }

    // public ArrayList<String> getClassList() {
    //     return classList;
    // }

    // public void setTypeList(ArrayList<String> newTypeList) {
    //     typeList = newTypeList;
    // }

    // public void removeClassFromClassList() {
    //     int lastIndex = classList.size() - 1;
    //     System.out.println("CLASSLISTSIZE: "+classList.size());
    //     if (lastIndex >= 0) {
    //         classList.remove(lastIndex);
    //         System.out.println("CLASSLISTSIZE: "+classList.size());
    //     } else {
    //         System.out.println("ArrayList is empty. Nothing to remove.");
    //     }
        
    // }

    // public ArrayList<String> getCurrentPanelList() {
    //     return classList;
    // }

    // public void setFinalClassList(ArrayList<String> userFinalClassList) {
    //     System.out.println("insetfinalclasslist: "+finalClassList);
    //     finalClassList = userFinalClassList;
    // }

    // public ArrayList<String> getFinalClassList() {
    //     System.out.println("ingetfinalclasslist: "+finalClassList);
    //     return finalClassList;
    // }

//
    public void setWindow(JFrame myWindow) {
        window = myWindow;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setNewOrExistingChanged(boolean changed) {
        System.out.println("setexistingornewchanged: "+ changed);
        newOrExistingHasChanged = changed;
    }

    public Boolean getNewOrExistingChanged() {
        //System.out.println("recievingexistingornewchanged: "+ studentOrTeacherHasChanged);
        return newOrExistingHasChanged;
    }

    public void setFilePath(String thisFilePath) {
        filePath = thisFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setDEBUGBOX(JLabel MYDEBUGMARKEDBOX) {
        DEBUGMARKEDBOX = MYDEBUGMARKEDBOX;
    }

    public JLabel getDEBUGBOX() {
        return DEBUGMARKEDBOX;
    }

    public void setCurrentSaveButton(JButton thisSaveButton) {
        saveButton = thisSaveButton;
    }

    public JButton getCurrentSaveButton() {
        return saveButton;
    }

    // public void getPathToClassInformationFolder() {
    //     //return;
    //     return "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+setUserInformation.getUsername()+"/ClassInformation/";
    // }

    // public String getPathToClassInformationFile(JTextField textField) {
    //     return "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+setUserInformation.getUsername()+"/"+"ClassInformation"+"/"+textField.getText()+".txt";
    // }
}