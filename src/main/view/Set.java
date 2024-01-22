package main.view;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Set {
    private String username;
    private ArrayList<String> classList;
    private Boolean saveable;
    private JPanel textFieldContainer;
    private JPanel textFieldPanel;

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

    public void setTextfieldContainer(JPanel theTextFieldContainer) {
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
        return textFieldContainer;
    }
}