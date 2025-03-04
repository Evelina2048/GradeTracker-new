package model;

import java.util.ArrayList;

import javax.swing.JTextField;

public class SetUserInformation {
    private String username;
    private String existingOrNew;
    private String studentOrTeacher;
    private SetState setState;
    private SetList setList;

    private ArrayList<String> deleteQueue = new ArrayList<>();

    // Below is for singleton design pattern
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
        return "src/main/model/UserInfo/StudentInfo/"+getUsername();
    }

    public String getPathToClassInformationFolder() {
        String usernameFolder = getPathToUsernameFolder();
        return usernameFolder+"/ClassInformation/";
    }

    public String getPathToClassTextFile() {
        String classInfoFolder = getPathToClassInformationFolder();
        return classInfoFolder+"/class.txt";
    }

    public String getPathToClassInformationFileWithTextField(JTextField textField) {
        String classInfoFolder = getPathToClassInformationFolder();
        return classInfoFolder+textField.getText()+".txt";
    }

    public String getPathToClassInformationFileWithIndex() {
        String classInfoFolder = getPathToClassInformationFolder();
        return  classInfoFolder+setList.getFinalClassList().get(setState.getClassListIndex())+".txt";
    }

    public String getPathToClassInformationFileWithChosenIndex(int chosenIndex) {
        String classInfoFolder = getPathToClassInformationFolder();
        return classInfoFolder+setList.getFinalClassList().get(chosenIndex)+".txt";
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
        deleteQueue.add("~/Documents/GradeTracker-new/target/classes/main/model/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+text+".txt");
    }

    public ArrayList<String> getDeleteQueue() {
        return deleteQueue;
    }

    public void TESTRESETSETUSERINFORMATION() {
        instance = null;
    }
}