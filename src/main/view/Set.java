package main.view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Set {
    private String username;
    private ArrayList<String> classList;
    private Boolean saveable;
    private JPanel textFieldContainer;
    private JPanel textFieldPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private int classIndex;

    public Set () {

    }

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public void setClassList(ArrayList<String> classList) {
        System.out.println("AStep3 in setClassList. Size: "+classList);
        this.classList = classList;
        System.out.println("AStep4 in setClassList. Size: "+classList.size());
    }

    public void resetClassList() {
        this.classList.clear();
    }

    public ArrayList<String> getClassList() {
        return this.classList;
    }
    
    public void classListIndexIncrement() {
        this.classIndex++;
    }

    public int getClassListIndex() {
        return this.classIndex;
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
}