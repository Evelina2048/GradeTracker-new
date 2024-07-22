package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;

public class SetState {
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
    private static SetState instance;

    private JLabel DEBUGMARKEDBOX;

    // Private constructor to prevent instantiation
    private SetState() {}

    // Public method to provide access to the single instance
    public static SetState getInstance() {
        if (instance == null) {
            instance = new SetState();
        }
        return instance;
    }

    public ArrayList<String> getTypeList() {
        return typeList;
    }


    public void setSaveable(Boolean canSave) {
        saveable = canSave;
    }

    public boolean getSaveable() {
        return saveable;
    }

    public void setTextFieldPanel(JPanel theTextFieldPanel) {
        textFieldPanel = theTextFieldPanel;
    }

    public JPanel getTextFieldPanel() {
        return textFieldPanel;
    }

    public void addTextFieldPanel(Component add) {
        textFieldPanel.add(add);
    }

    public boolean getEmptiedState(JTextField textField) {
        return textFieldEmptiedMap.getOrDefault(textField, false);
    }

    public void setEmptiedState(JTextField textField, boolean state) {
        textFieldEmptiedMap.put(textField, state);
    }

    public boolean getLoadedState(JTextField textField) {
        return textFieldLoadedMap.getOrDefault(textField, false);
    }

    public void setLoadedState(JTextField textField, boolean state) {
        textFieldLoadedMap.put(textField, state);
    }

    public void incrementClassListIndex() {
        classListIndex++;
    }

    public void decrementClassListIndex() {
        classListIndex--;
    }

    public int getClassListIndex() {
        return classListIndex;
    }

    public void setCurrentClass(String myClass) {
        currentClass = myClass;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCanContinue(boolean myCanContinue) {
        canContinue = myCanContinue;
    }

    public Boolean getCanContinue() {
        return canContinue;
    }
}