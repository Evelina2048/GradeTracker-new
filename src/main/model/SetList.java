package model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetList {
    private ArrayList<String> classList;
    private JPanel textFieldPanel;
    private HashMap<JTextField, Boolean> textFieldEmptiedMap = new HashMap<>();
    private HashMap<JTextField, Boolean> textFieldLoadedMap = new HashMap<>();
    private int classListIndex = 0;
    private ArrayList<String> finalClassList;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SetList instance;

    private JLabel DEBUGMARKEDBOX;
    private JPanel classLabelPanel;

    // Private constructor to prevent instantiation
    private SetList() {}

    // Public method to provide access to the single instance
    public static SetList getInstance() {
        if (instance == null) {
            instance = new SetList();
        }
        return instance;
    }
//
    public void setClassList(ArrayList<String> newClassList) {
        classList = newClassList;
    }

    public ArrayList<String> getClassList() {
        return classList;
    }

    //public void setTypeList(ArrayList<String> newTypeList) {
    public void removeClassFromClassList() {
        int lastIndex = classList.size() - 1;
        System.out.println("CLASSLISTSIZE: "+classList.size());
        if (lastIndex >= 0) {
            classList.remove(lastIndex);
            System.out.println("CLASSLISTSIZE: "+classList.size());
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

    public void setClassLabelPanel(JPanel thisClassLabelPanel) {
        classLabelPanel = thisClassLabelPanel;
    }

    public JPanel getClassLabelPanel() {
        return classLabelPanel;
    }
}