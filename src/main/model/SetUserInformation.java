package main.model;

import javax.swing.JTextField;

public class SetUserInformation {
    private String username;
    private String existingOrNew;
    private String studentOrTeacher;
    private Set set;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SetUserInformation instance;

    // Private constructor to prevent instantiation
    private SetUserInformation() {}

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
        this.set = Set.getInstance();
        return  "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+getUsername()+"/ClassInformation/"+set.getFinalClassList().get(set.getClassListIndex())+".txt";
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
}