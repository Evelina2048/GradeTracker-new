package main.view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.ObjectUtils.Null;

public class Set {
    private String username;
    private ArrayList<String> classList;
    private Boolean saveable;
    private JPanel textFieldContainer;
    private JPanel textFieldPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private int classListIndex = 0;

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
        //if (classList != null) {
        //    classList.clear();
        //}
        //System.out.println("2nd step: in set class list the newlist is: "+newClassList);
        classList = newClassList;
        System.out.println("in set class list: "+classList);
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

    public ArrayList<String> getClassList() {
        return classList;
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
}