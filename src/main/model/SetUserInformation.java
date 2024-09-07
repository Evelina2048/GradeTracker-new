package model;

import java.util.ArrayList;

import javax.swing.JTextField;

public class SetUserInformation {
    private String username;
    private String existingOrNew;
    private String studentOrTeacher;
    private Set set;
    private SetState setState;
    private SetList setList;
    
    private ArrayList<String> deleteQueue = new ArrayList<>();

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SetUserInformation instance;

    // Private constructor to prevent instantiation
    private SetUserInformation() {
        setState = SetState.getInstance();
        setList = SetList.getInstance();
    }

    // Public method to provide access to the single instance
    public static SetUserInformation getInstance() {
        if (instance == null) {
            instance = new SetUserInformation();
        }
        return instance;
    }

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public String getPathToUsernameFolder() {
        return "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername();
    }

    public String getPathToClassInformationFolder() {
        //return;
        return "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/";
    }

    public String getPathToClassTextFile() {
        //return;
        return "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/class.txt";
    }

    public String getPathToClassInformationFileWithTextField(JTextField textField) {
        return "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+textField.getText()+".txt";
    }

    public String getPathToClassInformationFileWithIndex() {
        //System.out.println("final list in path"+ setList.getFinalClassList()+" "+"/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+setList.getFinalClassList().get(setState.getClassListIndex())+".txt");
        System.out.println("final list in path"+ setList.getFinalClassList());
        System.out.println("number 2 "+setList.getFinalClassList().get(setState.getClassListIndex()));

        return  "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+setList.getFinalClassList().get(setState.getClassListIndex())+".txt";
    }

    public String getPathToClassInformationFileWithChosenIndex(int chosenIndex) {
        System.out.println("final list in path"+ setList.getFinalClassList()+" "+"/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+setList.getFinalClassList().get(chosenIndex)+".txt");
        return  "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+setList.getFinalClassList().get(chosenIndex)+".txt";
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

    public void addDeleteToQueue(String text) {
        deleteQueue.add("/Users/evy/Documents/GradeTracker-new/target/classes/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+text+".txt");

    }

    public ArrayList<String> getDeleteQueue() {
        return deleteQueue;
    }

    public void TESTRESETSETUSERINFORMATION() {
        instance = null;
    }
}