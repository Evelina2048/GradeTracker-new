package main.view;

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
    private JPanel textFieldContainer;
    private JPanel textFieldPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private int classListIndex = 0;
    private ArrayList<String> finalClassList;
    private String existingOrNew;
    private String studentOrTeacher;
    private JFrame window;

    public Set () {

    }

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public void setClassList(ArrayList<String> newClassList) {
        System.out.println("in set class list the newlist is: "+newClassList);
        classList = newClassList;
        System.out.println("in set class list: "+classList);
    }

    public void setTypeList(ArrayList<String> newTypeList) {
        System.out.println("in set class list the newlist is: "+newTypeList);
        typeList = newTypeList;
        System.out.println("in set class list: "+ typeList);
    }

    public void removeClassFromClassList() {
        int lastIndex = classList.size() - 1;
        if (lastIndex >= 0) {
            classList.remove(lastIndex);
            System.out.println("Removed the most recent element.");
        } else {
            System.out.println("ArrayList is empty. Nothing to remove.");
        }
        
    }

    public ArrayList<String> getCurrentPanelList() {
        //System.out.println("current panel list is kkkk:   "+ classList+ typeof(classList));
        return classList;
    }

    public void setFinalClassList(ArrayList<String> userFinalClassList) {
        finalClassList = userFinalClassList;
        System.out.println("insetfinalclasslist: "+finalClassList);
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

    // public void setTextFieldContainer(JPanel theTextFieldContainer) {
    //     textFieldContainer = theTextFieldContainer;
    //     //System.out.println("in set textfieldcontainer"+getTextFieldContainer());
    // }

    public void setTextFieldPanel(JPanel theTextFieldPanel) {
        textFieldPanel = theTextFieldPanel;
    }

    public JPanel getTextFieldPanel() {
        return textFieldPanel;
    }

    public void addTextFieldPanel(Component add) {
        textFieldPanel.add(add);
    }

    // public JPanel getTextFieldContainer() {
    //     //JPanel textFieldContainer = new JPanel();
    //     //textFieldContainer.add(textFieldPanel);
    //     return textFieldContainer;
    // }

    // public void resetContainerAndPanel() {
    //     textFieldContainer = null;
    //     textFieldPanel = null;
    // }

    public boolean getEmptiedState(JTextField textField) {
        return textFieldEmptiedMap.getOrDefault(textField, false);
    }

    public void setEmptiedState(JTextField textField, boolean state) {
        textFieldEmptiedMap.put(textField, state);
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
}