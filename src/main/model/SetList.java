package model;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SetList {
    private ArrayList<String> classList;
    private ArrayList<String> finalClassList;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SetList instance;

    private JPanel classLabelPanel;
    private JPanel readClassLabelPanel;

    private ArrayList<Integer> gradeNumber = new ArrayList<>();

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

    public void setReadClassLabelPanel(JPanel thisReadClassLabelPanel) {
        readClassLabelPanel = thisReadClassLabelPanel;
    }

    public JPanel getReadClassLabelPanel() {
       return readClassLabelPanel;
    }

    public JPanel getClassLabelPanel() {
        return classLabelPanel;
    }

    public void addGradeTypeList(int thisGradeNumber) {
        gradeNumber.add(thisGradeNumber);
    }

    public ArrayList<Integer> getGradeTypeList() {
        return gradeNumber;
    }

    public void removeStudentStatCollectSettings() {
        JFrame window = Set.getInstance().getWindow();
        if (classLabelPanel != null) {
            window.remove(classLabelPanel);
        }
        
    }

    public void removeStudentStatCollectReadClassLabelPanel() {
        JFrame window = Set.getInstance().getWindow();
        window.remove(readClassLabelPanel);
        
    }



}