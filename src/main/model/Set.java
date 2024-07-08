package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;

public class Set {
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
    //private Boolean existingOrNewHasChanged = false;
    private Boolean studentOrTeacherHasChanged = false;
    private Boolean newOrExistingHasChanged = false;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static Set instance;

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

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public void setClassList(ArrayList<String> newClassList) {
        classList = newClassList;
    }

    public void setTypeList(ArrayList<String> newTypeList) {
        typeList = newTypeList;
    }

    public void removeClassFromClassList() {
        int lastIndex = classList.size() - 1;
        if (lastIndex >= 0) {
            classList.remove(lastIndex);
        } else {
            System.out.println("ArrayList is empty. Nothing to remove.");
        }
        
    }

    public ArrayList<String> getCurrentPanelList() {
        return classList;
    }

    public void setFinalClassList(ArrayList<String> userFinalClassList) {
        System.out.println("insetfinalclasslist: "+finalClassList);
        finalClassList = userFinalClassList;
    }

    public ArrayList<String> getFinalClassList() {
        System.out.println("ingetfinalclasslist: "+finalClassList);
        return finalClassList;
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

    public void resetClassList() {
    }

    public void setExistingOrNew(String myExistingOrNew) {
        existingOrNew = myExistingOrNew;
        System.out.println(2222+existingOrNew);
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

    public void setWindow(JFrame myWindow) {
        window = myWindow;
    }

    public JFrame getWindow() {
        return window;
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

    public void setNewOrExistingChanged(boolean changed) {
        System.out.println("setexistingornewchanged: "+ changed);
        newOrExistingHasChanged = changed;
    }

    public Boolean getNewOrExistingChanged() {
        //System.out.println("recievingexistingornewchanged: "+ studentOrTeacherHasChanged);
        return newOrExistingHasChanged;
    }

}