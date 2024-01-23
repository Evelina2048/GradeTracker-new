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

    public Set () {

    }

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public void setClassList(ArrayList<String> classList) {
        this.classList = classList;
    }

    public ArrayList<String> getClassList() {
        return classList;
    }

    public void setSaveable(Boolean canSave) {
        saveable = canSave;
    }

    public boolean getSaveable() {
        return saveable;
    }

    public void setTextFieldContainer(JPanel theTextFieldContainer) {
        textFieldContainer = theTextFieldContainer;
        //System.out.println("in set textfieldcontainer"+getTextFieldContainer());
    }

    public void setTextFieldPanel(JPanel theTextFieldPanel) {
        textFieldPanel = theTextFieldPanel;
    }

    public JPanel getTextFieldPanel() {
        return textFieldPanel;
    }

    public JPanel getTextFieldContainer() {
        //JPanel textFieldContainer = new JPanel();
        //textFieldContainer.add(textFieldPanel);
        return textFieldContainer;
    }

    public void resetContainerAndPanel() {
        textFieldContainer = null;
        textFieldPanel = null;
    }

    public boolean getEmptiedState(JTextField textField) {
        return textFieldEmptiedMap.getOrDefault(textField, false);
    }

    public void setEmptiedState(JTextField textField, boolean state) {
        textFieldEmptiedMap.put(textField, state);
    }
}